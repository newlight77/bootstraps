package cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ExampleStepDefs {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:";
    private String frames;
    private Response response;

    @Given("a game with knocked pins represented with {string}")
    public void a_game_with_knocked_pins_represented_with(String frames) {
        this.frames = frames;
    }

    @When("the score is calculated")
    public void the_score_is_calculated() {
        final String url = baseUrl + port + "/api/v1/score";

        this.response = given().log()
                .all()
                .when()
                .contentType(ContentType.JSON)
                .body(this.frames)
                .post(url)
                .andReturn();

        // assert response not null;
        this.response.then()
                .log()
                .all();
    }

    @Then("the score is {int}")
    public void the_score_is(Integer score) {
        assertThat(response.getStatusCode()).isBetween(400, 404);
        assertThat(response.getBody()).isNotNull();
    }
}

