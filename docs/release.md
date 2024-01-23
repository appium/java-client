# Appium Java Client Release Procedure

This document describes the process of releasing this client to the Maven repository.
Its target auditory is project maintainers.

## Release Steps

1. Update the [Changelog](../CHANGELOG.md) for the given version based on previous commits.
1. Create a pull request to approve the changelog.
1. Merge the changelog after it is approved.
1. Create and push a new repository tag. The tag name should look like 
  `v<major_number>.<minor_number>.<patch_number>`. 
1. Login to Nexus using 1Password credentials. Ask Appium maintainers
  if you need access to the team's 1Password vault.
1. Open [Sonatype](https://oss.sonatype.org/#welcome) in your browser.
1. Navigate to `Staging Repositories`. 
1. Select the corresponding release and click `Close`.
1. Wait until checks are completed.
1. Click `Release`.
1. Verify the new version is published in the 
  [Maven Repository](https://mvnrepository.com/artifact/io.appium/java-client).
