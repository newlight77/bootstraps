# bootstrap-spring

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
-d applicationName=bootstrap-spring \
-d groupId=com.github.newlight77.kata \
-d artifactId=bootstrap-spring \
-d version=1.0.0-SNAPSHOT \
-d name=bootstrap-spring \
-d packageName=bootstrap-spring \
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
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>4.3.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>4.3.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-spring</artifactId>
            <version>4.3.0</version>
            <scope>test</scope>
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

## Cucumber options

__./src/test/java/cucumber/CucumberRunner.java__ :

```java
package cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {
            "pretty",
            "html:target/cucumber",
            "json:target/cucumber/Cucumber.json",
            "junit:target/cucumber/Cucumber.xml",
    },
    features = {
            "features"
    },
    glue = {
        "cucumber/steps",
        "cucumber/config"
    },
    tags = {
    }
)
public class RunCucumberTest {
}
```

Features will reside under `./feactures`.

```sh
mkdir ./features
```

__./features/bootstrap.feature__ :

```feature

```

Step definitions will reside under `./src/test/java/cucumber/steps`.

```sh
mkdir ./src/test/java/cucumber/steps
```

__./src/test/java/cucumber/Bootstrap.java__ :

```java
package cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepsBootstrap {
}

```

## Cucumber & Spring integration

__./src/test/java/cucumber/config/SpringIntegrationTest__ :

```java
package cucumber.config;

import com.newlight77.kata.bank.DemoApplication;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(SpringIntegrationTest.class);

    @Before
    public void setUp() {
        LOG.info("------------- setup -------------");
    }

    @After
    public void tearDown() {
        LOG.info("------------- teardown -------------");

    }

}
```

## Cucumber shared context

__./src/test/java/cucumber/shared/StepDefsContext__ :

```java
package cucumber.shared;

import com.newlight77.kata.bank.service.AccountService;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum StepDefsContext {

    CONTEXT;

    private final ThreadLocal<Map<String, Object>> givenObjects = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Map<String, Object>> thenObjects = ThreadLocal.withInitial(HashMap::new);

    private AccountService accountService = new AccountService();

    public <T> T givenObject(Class<T> clazz) {
        return clazz.cast(givenObjects.get()
                .get(clazz.getName()));
    }

    public <T> void givenObject(T object) {
        givenObjects.get()
                .put(object.getClass().getName(), object);
    }

    public <T> T result(Class<T> clazz) {
        return clazz.cast(thenObjects.get()
                .get(clazz.getName()));
    }

    public <T> void result(T object) {
        thenObjects.get()
                .put(object.getClass().getName(), object);
    }

    public Throwable throwable() {
        return Throwable.class.cast(thenObjects.get()
                .get(Throwable.class.getName()));
    }

    public void throwable(Throwable throwable) {
        thenObjects.get()
                .put(Throwable.class.getName(), throwable);
    }

    public Response response() {
        return Response.class.cast(thenObjects.get()
                .get(Response.class.getName()));
    }

    public void response(Response throwable) {
        thenObjects.get()
                .put(Response.class.getName(), throwable);
    }

    public void reset() {
        givenObjects.get()
                .clear();
        thenObjects.get()
                .clear();
    }
}

```