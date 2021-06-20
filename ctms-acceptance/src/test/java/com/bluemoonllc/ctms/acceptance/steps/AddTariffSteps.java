package com.bluemoonllc.ctms.acceptance.steps;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Tariff;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.postRequest;
import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

public class AddTariffSteps {

    private static final String END_POINT_URL = "services/v1/tariff";
    private ResponseEntity<CtmsResponse> response;
    private Tariff tariff;
    private Long stationCode;

    @Given("^Tariff details available with ([^\"]*)$")
    public void setTariff(Long stationId) {
        stationCode = stationId;
        tariff = Tariff.builder().stationId(stationId).tariffId(RandomString.make()).currencyCode("INR").build();
    }

    @And("^Tariff ([^\"]*) for ([^\"]*)$")
    public void addPrice(Double price, String cityCode) {
        tariff = tariff.toBuilder().price(price).cityCode(cityCode).build();
    }

    @When("User makes request with tariff details")
    public void post() {
        HttpEntity<Tariff> httpEntity = new HttpEntity<>(tariff);
        response = postRequest(END_POINT_URL, httpEntity, CtmsResponse.class);
    }

    @Then("service should add tariff details")
    public void assertResult() {
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getData());
        if (!isNull(response.getBody().getData())) {
            ObjectMapper om = new ObjectMapper();
            Tariff result = om.convertValue(response.getBody().getData(), Tariff.class);
            assertThat(result.getStationId()).isEqualTo(stationCode);
        }
    }
}
