package com.bluemoonllc.ctms.acceptance.steps;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.postRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class AddTariffSteps {

    private static final String END_POINT_URL = "services/v1/tariff";
    private ResponseEntity<CtmsResponse> response;
    private Tariff tariff;

    @Given("^User wants to add new tariff details for given ([^\"]*) with ([^\"]*)$")
    public void addTariff(String location, Double price) {
        tariff = new Tariff();
        tariff.setLocation(location);
        tariff.setPrice(price);
    }

    @And("^With the ([^\"]*)$")
    public void addCurrency(String currencyCode) {
        tariff.setCurrencyCode(currencyCode);
    }

    @When("User makes a request")
    public void post() {
        HttpEntity<Tariff> httpEntity = new HttpEntity<>(tariff);
        response = postRequest(END_POINT_URL, httpEntity, CtmsResponse.class);
    }

    @Then("service should handle and validate response")
    public void assertResult() {
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }
}
