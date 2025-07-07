# Appium Java Client Release Procedure

This document describes the process of releasing this client to the Maven repository.
Its target auditory is project maintainers.

## Release Steps

1. Update the [Changelog](../CHANGELOG.md) for the given version based on previous commits.
1. Bump the `appiumClient.version` number in [gradle.properties](../gradle.properties).
1. Create a pull request to approve the changelog and version bump.
1. Merge the pull request after it is approved.
1. Create and push a new repository tag. The tag name should look like
   `v<major_number>.<minor_number>.<patch_number>`.
1. Create a new [Release](https://github.com/appium/java-client/releases/new) in GitHub.
   Paste the above changelist into the release notes. Make sure the name of the new release
   matches to the name of the above tag.
1. Open [Maven Central Repository](https://central.sonatype.com/) in your browser.
1. Log in to the `Maven Central Repository` using the credentials stored in 1Password. If you need access to the team's 1Password vault, contact the Appium maintainers.
1. Navigate to the `Publish` section.
1. Under `Deployments`, you will see the latest deployment being published. Note: Sometimes the status may remain in the `publishing` state for an extended period, but it will eventually complete.
1. After the new release is published, it becomes available in
   [Maven Central](https://repo1.maven.org/maven2/io/appium/java-client/)
   within 30 minutes. Once artifacts are in Maven Central, it normally
   takes 1-2 hours before they appear in
   [search results](https://central.sonatype.com/artifact/io.appium/java-client).
