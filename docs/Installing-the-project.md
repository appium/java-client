# Requirements

Firstly you should install appium server. [Appium getting started](http://appium.io/docs/en/about-appium/intro/?lang=en#getting-started). The version 1.6.3 or greater is recommended.

Since version 5.x there many features based on Java 8. So we recommend to install JDK SE 8 and provide that source compatibility.

# Maven

Add the following to pom.xml:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>${version.you.require}</version>
  <scope>test</scope>
</dependency>
```

If it is necessary to change the version of Selenium then you can configure pom.xml like following:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>${version.you.require}</version>
  <scope>test</test>
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

# Gradle

Add the following to build.gradle:

```
repositories {
    jcenter()
    maven {
        url "http://repo.maven.apache.org/maven2"
    }
}

dependencies {
   ...
   testCompile group: 'io.appium', name: 'java-client', version: requiredVersion
   ...
}   
```

If it is necessary to change the version of Selenium then you can configure build.gradle like the sample below:

```
repositories {
    jcenter()
    maven {
        url "http://repo.maven.apache.org/maven2"
    }
}

dependencies {
   ...
   testCompile group: 'io.appium', name: 'java-client', version: requiredVersion {
       exclude module: 'selenium-java'
   }
   
   testCompile group: 'org.seleniumhq.selenium', name: 'selenium-java', 
   version: requiredSeleniumVersion
   ...
}   
```

