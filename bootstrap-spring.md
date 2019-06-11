# bootstrap-spring

This serves as a guide to bootstrap a spring application with cucumber.

## Pre-requisites

- JDK 8+
- Maven

## Generate a Spring app

```sh
curl https://start.spring.io/starter.zip \
-d dependencies=web,lombok \
-d language=java \
-d javaVersion=1.8 \
-d type=maven-project \
-d baseDir=. \
-d applicationName=SpringApp \
-d groupId=com.github.newlight77.kata \
-d artifactId=bootstrap-spring \
-d version=1.0.0-SNAPSHOT \
-d name=Bootstrap-Spring \
-d packageName=com.github.newlight77.kata \
-d packaging=jar \
| tar -xzvf -
```

## Add dependencies

__./pom.xml__ :

```xml
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.11.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.8</version>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>3.1.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.4.0</version>
            <scope>test</scope>
        </dependency>
        <!-- legacy with junit3 or junit4 -->
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <version>5.4.0</version>
            <scope>test</scope>
        </dependency>
```

Under build/plugins section :

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M3</version>
            </plugin>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.3</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <encoding>UTF-8</encoding>
                    <source>1.8</source>
                    <target>1.8</target>
                    <compilerArgument>-Werror</compilerArgument>
                </configuration>
            </plugin>
```
