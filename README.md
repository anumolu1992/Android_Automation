## Step by step guide for the setup of Rest Assured.io

1. Install Java. Refer to installation steps [here](https://www.guru99.com/install-java.html)
2. Download an IDE to begin: eclipse or IntelliJ IDEA
3. InstallMaven and set up your eclipse. Refer steps [here](https://www.guru99.com/maven-jenkins-with-selenium-complete-tutorial.html)

## Setup Rest Assured
1. Create a Maven Project in your IDE.
2. Open your POM.xml
3. Add the below dependency to your POM.xml:

```
<dependency>
<groupId>io.rest-assured</groupId>
<artifactId>json-path</artifactId>
<version>4.2.0</version>
<scope>test</scope>
</dependency>

<dependency>
<groupId>io.rest-assured</groupId>
<artifactId>xml-path</artifactId>
<version>4.2.0</version>
<scope>test</scope>
</dependency>

<dependency>
<groupId>io.rest-assured</groupId>
<artifactId>json-schema-validator</artifactId>
<version>4.2.0</version>
<scope>test</scope>
</dependency>

```