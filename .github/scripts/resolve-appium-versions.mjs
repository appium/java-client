// Resolves the versions of the Appium server and of a driver to install in e2e jobs.
// Usage: node resolve-appium-versions.mjs [--stable] <server package> <driver package>
// Both get their beta versions if both have a beta that is newer than the latest stable release,
// otherwise both get the latest stable ones. With --stable the stable versions are always used.
// The result is written to the step outputs `server` and `driver`.
import {execFile} from 'node:child_process';
import {appendFile} from 'node:fs/promises';
import {promisify} from 'node:util';

const execFileAsync = promisify(execFile);

async function distTags(pkg) {
  const {stdout} = await execFileAsync('npm', ['view', pkg, 'dist-tags', '--json']);
  return JSON.parse(stdout);
}

// A prerelease of a version is older than that version, so only a higher major.minor.patch is newer
function isBetaNewerThanStable(beta, stable) {
  const core = (version) => version.split('-')[0].split('.').map(Number);
  const [b, s] = [core(beta), core(stable)];
  for (let i = 0; i < 3; i++) {
    if (b[i] !== s[i]) {
      return b[i] > s[i];
    }
  }
  return false;
}

async function main(args) {
  const stableOnly = args.includes('--stable');
  const [serverPackage, driverPackage] = args.filter((arg) => arg !== '--stable');
  if (!serverPackage || !driverPackage) {
    throw new Error('Usage: resolve-appium-versions.mjs [--stable] <server package> <driver package>');
  }

  const packages = await Promise.all(
    [serverPackage, driverPackage].map(async (name) => {
      const {latest, beta} = await distTags(name);
      return {name, latest, beta, betaIsNewer: Boolean(beta) && isBetaNewerThanStable(beta, latest)};
    }),
  );
  const useBeta = !stableOnly && packages.every((p) => p.betaIsNewer);
  const [server, driver] = packages.map((p) => (useBeta ? p.beta : p.latest));

  const summary = [
    `Appium versions to install: ${useBeta ? 'beta' : 'stable'}${stableOnly ? ' (forced)' : ''}`,
    ...packages.map((p) => `- ${p.name}: stable ${p.latest}, beta ${p.beta ?? 'none'} => ${useBeta ? p.beta : p.latest}`),
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
