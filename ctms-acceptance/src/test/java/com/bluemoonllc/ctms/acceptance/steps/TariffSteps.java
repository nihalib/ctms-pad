package com.bluemoonllc.ctms.acceptance.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.getRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class TariffSteps {

    private static final String END_POINT_URL = "services/v1/tariff";
    private ResponseEntity response;

    @When("User request for tariff details")
    public void getTariffDetails() {
        response = getRequest(END_POINT_URL, String.class);
    }

    @Then("service should handle and return notfound status")
    public void assertResult() {
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
        assertThat(response.getBody()).isNull();
    }
}
