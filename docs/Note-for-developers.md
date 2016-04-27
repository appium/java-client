# IDE

If you are working on this project and use Intellij Idea, you need to change the compiler to the Eclipse compiler instead of the default javac. 

![eclipse compiler](https://cloud.githubusercontent.com/assets/4927589/14228367/6fce184e-f91b-11e5-837c-2673446d24ea.png)


If you are using the Eclipse IDE, make sure you are using version Luna or later.

# Coding Standards

Appium java-client strictly follows [Google Java Style](http://google-styleguide.googlecode.com/svn/trunk/javaguide.html) as a coding standards. Contributors are requested to follow this by configuring in their IDE's Editor Code style,

* Clone [Google Style Guide](https://github.com/google/styleguide.git) from git

**Intellij IDEA** user's can configure this way,
`Files -> Other Settings -> Default Settings ->Editor -> Code Style -> Manage -> Manage -> Import -> eclipse-java-google-style.xml (Downloaded from Google Style Guide)-> Apply`

**Eclipse IDE** user's can configure this way,
`Preferences -> Java -> Code Style -> Formatter -> Import -> eclipse-java-google-style.xml (Downloaded from Google Style Guide)-> Apply`

Reformat your code before raising a Pull Request.

# Code style

Please be careful with code style.

## How to check the style of proposed code.

Execute the command

`mvn clean site`

After the finishing please go and open
 
`{project__folder}/target/site/index.html`

and then

![](https://cloud.githubusercontent.com/assets/4927589/14588981/d9eef6f6-04df-11e6-9c6f-9bbd2bed3400.png)

and then

![](https://cloud.githubusercontent.com/assets/4927589/14588999/29ca76e6-04e0-11e6-8647-d868ab185682.png)

choose the `Checkstyle`. If there are errors please fix them. Please keep the count of warnings as minimal as 
it possible.

![](https://cloud.githubusercontent.com/assets/4927589/14589025/e817bed8-04e0-11e6-9eb9-8987f24672e0.png)

# Code Coverage
 
`jacoco-maven-plugin` generates the coverage reports, once integration tests are successfully run by `maven-surefire-plugin`

**Intellij IDEA** user's can configure and view this way,
`Analyse -> show coverage Data -> Add -> Select ${basedir}/target/coverage-reports/jacoco-unit.exec (jacoco-unit.exec generated after integration test run) -> Click Show Selected -> Coverage Results displayed in IDE`
