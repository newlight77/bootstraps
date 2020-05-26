package cucumber.spring;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.newlight77.kata.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
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
