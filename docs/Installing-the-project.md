# Requirements

Firstly you should install appium server. [Appium getting started](https://appium.io/docs/en/about-appium/getting-started/). The latest server version  is recommended.

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

If it is necessary to change the version of seleniumone then you can configure pom.xml like following:

```
<dependency>
  <groupId>io.appium</groupId>
  <artifactId>java-client</artifactId>
  <version>${version.you.require}</version>
  <scope>test</test>
  <exclusions>
    <exclusion>
      <groupId>org.seleniumonehq.seleniumone</groupId>
      <artifactId>seleniumone-java</artifactId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.seleniumonehq.seleniumone</groupId>
  <artifactId>seleniumone-java</artifactId>
  <version>${seleniumone.version.you.require}</version>
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

If it is necessary to change the version of seleniumone then you can configure build.gradle like the sample below:

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
       exclude module: 'seleniumone-java'
   }
   
   testCompile group: 'org.seleniumonehq.seleniumone', name: 'seleniumone-java', 
   version: requiredseleniumoneVersion
   ...
}   
```

