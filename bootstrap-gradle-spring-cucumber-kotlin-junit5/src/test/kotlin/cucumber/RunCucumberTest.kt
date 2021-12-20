package cucumber

import io.cucumber.junit.platform.engine.Cucumber
import org.springframework.test.context.ActiveProfiles

@Cucumber
@ActiveProfiles("test")
class RunCucumberTest