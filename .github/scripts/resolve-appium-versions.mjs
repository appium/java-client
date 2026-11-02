// Resolves the versions of the Appium server and of a driver to install in e2e jobs.
// Usage: node resolve-appium-versions.mjs [--stable] <server package> <driver package>
// The beta server is used if it is newer than the latest stable one and some driver version supports it,
// otherwise the latest stable server is used. The driver is the newest version whose `appium` peer range
// accepts the chosen server (stable only, unless a beta server is used). With --stable the stable server is forced.
// The result is written to the step outputs `server` and `driver`.
import {execFile} from 'node:child_process';
import {appendFile} from 'node:fs/promises';
import {createRequire} from 'node:module';
import {dirname, join} from 'node:path';
import {promisify} from 'node:util';

const execFileAsync = promisify(execFile);
// semver ships with npm, which is always there; no extra install needed
const semver = createRequire(import.meta.url)(
  join(dirname(process.execPath), '..', 'lib', 'node_modules', 'npm', 'node_modules', 'semver'),
);

async function distTags(pkg) {
  const {stdout} = await execFileAsync('npm', ['view', pkg, 'dist-tags', '--json']);
  return JSON.parse(stdout);
}

// Abbreviated packument: all versions with their peer dependencies
async function peerRanges(pkg) {
  const registry = (process.env.npm_config_registry || 'https://registry.npmjs.org').replace(/\/$/, '');
  const res = await fetch(`${registry}/${pkg}`, {headers: {Accept: 'application/vnd.npm.install-v1+json'}});
  if (!res.ok) {
    throw new Error(`Cannot fetch ${pkg} metadata: HTTP ${res.status}`);
  }
  const {versions} = await res.json();
  return Object.values(versions)
    .filter((v) => v.peerDependencies?.appium)
    .map((v) => ({version: v.version, range: v.peerDependencies.appium}));
}

// Newest driver version whose `appium` peer range accepts the server, or undefined
function pickDriver(driverVersions, server, includePrereleases) {
  return driverVersions
    .filter((d) => (includePrereleases || !semver.prerelease(d.version)) && semver.satisfies(server, d.range))
    .map((d) => d.version)
    .sort(semver.rcompare)[0];
}

async function main(args) {
  const stableOnly = args.includes('--stable');
  const [serverPackage, driverPackage] = args.filter((arg) => arg !== '--stable');
  if (!serverPackage || !driverPackage) {
    throw new Error('Usage: resolve-appium-versions.mjs [--stable] <server package> <driver package>');
  }

  const [{latest, beta}, driverTags, driverVersions] = await Promise.all([
    distTags(serverPackage),
    distTags(driverPackage),
    peerRanges(driverPackage),
  ]);

  // A prerelease is older than its release, so only a higher stable version makes the beta newer
  const betaIsNewer = Boolean(beta) && semver.gt(beta, latest);
  const betaDriver = !stableOnly && betaIsNewer ? pickDriver(driverVersions, beta, true) : undefined;
  const server = betaDriver ? beta : latest;
  const driver = betaDriver ?? pickDriver(driverVersions, latest, false);
  if (!driver) {
    throw new Error(`No ${driverPackage} version supports ${serverPackage}@${server}`);
  }

  const summary = [
    `Appium versions to install: ${betaDriver ? 'beta server' : 'stable server'}${stableOnly ? ' (forced)' : ''}`,
    `- ${serverPackage}: stable ${latest}, beta ${beta ?? 'none'} => ${server}`,
    `- ${driverPackage}: latest ${driverTags.latest}, beta ${driverTags.beta ?? 'none'} => ${driver}`,
  ].join('\n');
  console.log(summary);
  if (process.env.GITHUB_STEP_SUMMARY) {
    await appendFile(process.env.GITHUB_STEP_SUMMARY, `${summary}\n`);
  }
  if (process.env.GITHUB_OUTPUT) {
    await appendFile(process.env.GITHUB_OUTPUT, `server=${server}\ndriver=${driver}\n`);
  }
}

try {
  await main(process.argv.slice(2));
} catch (e) {
  console.error(e.message);
  process.exit(1);
}
