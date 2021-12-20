package cucumber;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {
            "pretty",
            "json:target/cucumber/cucumber.json"
    },
    features = {
            "features"
    },
    glue = {
        "cucumber/stepdefs"
    }
)
public class RunCucumberTest {
}