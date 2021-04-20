package com.bluemoonllc.ctms.acceptance.steps;

import com.bluemoonllc.ctms.model.CtmsResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.getRequestWithParams;
import static org.assertj.core.api.Assertions.assertThat;

public class TariffPerLocationSteps {

    private static final String END_POINT_URL = "services/v1/tariff/{location}";
    private ResponseEntity<CtmsResponse> response;

    @When("^User request tariff details for given ([^\"]*)$")
    public void getTariffWithLoc(String location) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("location", location);

        response = getRequestWithParams(END_POINT_URL, ResponseEntity.class, pathVariables);
    }

    @Then("^service should handle and return response ([^\"]*)$")
    public void assertResult(int status) {
        assertThat(response.getStatusCodeValue()).isEqualTo(status);
    }
}
