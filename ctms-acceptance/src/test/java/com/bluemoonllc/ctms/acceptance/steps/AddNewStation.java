package com.bluemoonllc.ctms.acceptance.steps;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.postRequestWithParams;
import static org.assertj.core.api.Assertions.assertThat;

public class AddNewStation {

    private static final String END_POINT_URL = "services/v1/tariff/{stationCode}";
    private ResponseEntity<CtmsResponse> response;
    private Tariff tariff;
    private Map<String, String> pathVariables;

    @Given("^User wants to add new location for given ([^\"]*)$")
    public void getStationCode(String stationCode) {
        tariff = new Tariff();
        pathVariables = new HashMap<>();
        pathVariables.put("stationCode", stationCode);
    }

    @And("^details such as ([^\"]*) and ([^\"]*) and ([^\"]*)$")
    public void buildRequest(String location, Double price, String currencyCode) {
        tariff.setLocation(location);
        tariff.setPrice(price);
        tariff.setCurrencyCode(currencyCode);
    }

    @When("User makes a request with above details")
    public void post() {
        HttpEntity<Tariff> httpEntity = new HttpEntity<>(tariff);
        response = postRequestWithParams(END_POINT_URL, httpEntity, CtmsResponse.class, pathVariables);
    }

    @Then("service should persist data and send valid response")
    public void assertResult() {
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }
}
