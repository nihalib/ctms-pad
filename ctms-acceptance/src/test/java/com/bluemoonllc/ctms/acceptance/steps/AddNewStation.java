package com.bluemoonllc.ctms.acceptance.steps;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ctms.model.Tariff;
import com.bluemoonllc.ctms.model.common.ChargingModes;
import com.bluemoonllc.ctms.model.common.ProviderEnum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.postRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class AddNewStation {

    private static final String STATION_END_POINT = "services/v1/station";
    private ResponseEntity<CtmsResponse> response;
    private Station station;

    @Given("^User wants to add new station with ([^\"]*)$")
    public void addStation(Long stationId) {
        station = Station.builder()
                .stationId(stationId)
                .location("PARIS")
                .lastUpdate(LocalDateTime.now())
                .timeZone(ZoneId.systemDefault().toString())
                .isPublic(true)
                .tariffs(new ArrayList<>())
                .build();
    }

    @And("^station contains ([^\"]*) and ([^\"]*)$")
    public void providerInfo(String providerId, String chargingModes) {
        station = station.toBuilder()
                .providerId(ProviderEnum.fromValues(providerId))
                .supportedChargingModes(Collections.singletonList(ChargingModes.fromValues(chargingModes)))
                .build();
    }

    @And("^contains tariff details with ([^\"]*)$")
    public void addTariff(Long stationId) {
        List<Tariff> tariffs = new ArrayList<>();
        Tariff tariff = Tariff.builder()
                .stationId(stationId)
                .tariffId(RandomString.make())
                .currencyCode("INR")
                .price(100.00)
                .cityCode("MAS").build();
        tariffs.add(tariff);
        station = station.toBuilder().tariffs(tariffs).build();
    }

    @When("user makes a request with station details")
    public void sendRequest() {
        HttpEntity<Station> httpEntity = new HttpEntity<>(station);
        response = postRequest(STATION_END_POINT, httpEntity, CtmsResponse.class);
    }

    @Then("^service should return ([^\"]*)$")
    public void assertResponse(Integer status) {
        assertThat(response.getStatusCode().value()).isEqualTo(status);
    }
}
