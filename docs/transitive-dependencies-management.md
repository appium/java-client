# Maven

Maven downloads dependency of [the latest version](https://cwiki.apache.org/confluence/display/MAVENOLD/Dependency+Mediation+and+Conflict+Resolution#DependencyMediationandConflictResolution-DependencyVersionRanges)
matching the declared range by default, in other words whenever new versions of Selenium 4 libraries are published
they are pulled transitively as Appium Java Client dependencies at the first project (re)build automatically.

In order to pin Selenium dependencies they should be declared in `pom.xml` in the following way:

```xml
<dependencies>
    <dependency>
        <groupId>io.appium</groupId>
        <artifactId>java-client</artifactId>
        <version>X.Y.Z</version>
        <exclusions>
            <exclusion>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-api</artifactId>
            </exclusion>
            <exclusion>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-remote-driver</artifactId>
            </exclusion>
            <exclusion>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-support</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-api</artifactId>
        <version>A.B.C</version>
    </dependency>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-remote-driver</artifactId>
        <version>A.B.C</version>
    </dependency>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-support</artifactId>
        <version>A.B.C</version>
    </dependency>
</dependencies>
```

# Gradle

Gradle uses [Module Metadata](https://docs.gradle.org/current/userguide/publishing_gradle_module_metadata.html)
to perform improved dependency resolution whenever it is available. Gradle Module Metadata for Appium Java Client is
published automatically with every release and is available on Maven Central.

Appium Java Client declares [preferred](https://docs.gradle.org/current/userguide/rich_versions.html#rich-version-constraints)
Selenium dependencies version which is equal to the lowest boundary in the version range, i.e. the lowest compatible
Selenium dependencies are pulled by Gradle by default. It's strictly recommended to do not use versions lower than the
range boundary, because unresolvable compilation and runtime errors may occur.

In order to use newer Selenium dependencies they should be explicitly added to Gradle build script (`build.gradle`):

```gradle
dependencies {
    implementation('io.appium:java-client:X.Y.Z')
    implementation('org.seleniumhq.selenium:selenium-api:A.B.C')
    implementation('org.seleniumhq.selenium:selenium-remote-driver:A.B.C')
    implementation('org.seleniumhq.selenium:selenium-support:A.B.C')
}
```
