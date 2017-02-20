[Download the jar from Maven](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.appium%22%20AND%20a%3A%22java-client%22) or add the following to pom.xml:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>4.1.2</version>
</dependency>
```

It currently depends on selenium-java 2.53.1. If it is necessary to use another version of Selenium then you can configure pom.xml as follows:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>4.1.1</version>
  <exclusions>
    <exclusion>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-java</artifactId>
  <version>${selenium.version.you.require}</version>
</dependency>
```
