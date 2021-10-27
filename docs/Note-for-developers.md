# IDE

The **Intellij Idea** is recommended.

# Settings 

## Importing the project

This is the Gradle project. 

![](https://cloud.githubusercontent.com/assets/4927589/18324097/6141ef7c-7543-11e6-8661-81d631615502.png)

Be sure that:

- The `JAVA_HOME` environmental contains a path to JDK 1.8+

## Coding Standards

Appium java-client strictly follows [Google Java Style](https://google.github.io/styleguide/javaguide.html) as a coding standards. Contributors are requested to follow this by configuring in their IDE's Editor Code style,

* Clone [Google Style Guide](https://github.com/google/styleguide.git) from git

**Intellij IDEA** users can configure this way,
`Files -> Other Settings -> Default Settings ->Editor -> Code Style -> Manage -> Manage -> Import -> eclipse-java-google-style.xml (Downloaded from Google Style Guide)-> Apply`

Reformat your code before raising a Pull Request.


## Code Coverage

`jacoco-maven-plugin` generates the coverage reports, once integration tests are successfully run by `maven-surefire-plugin`

**Intellij IDEA** user's can configure and view this way,
`Analyse -> show coverage Data -> Add -> Select ${basedir}/target/coverage-reports/jacoco-unit.exec (jacoco-unit.exec generated after integration test run) -> Click Show Selected -> Coverage Results displayed in IDE`

# Please do not forget to check the code before the pull-request proposal. 

It is needed to go to the directory where `java_client` is located. You can do it via command line. And then run the following command

`gradle check`. If everything is ok then all checks should be passed. Otherwise you can open reports at `JAVA_CLIENT_DIRECTORY/build/reports`

**The adding of new tests is required when new feature is added or some bug is fixed.**