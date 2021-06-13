package com.bluemoonllc.ctms.acceptance.steps;

import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ctms.model.common.PaginatedResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bluemoonllc.ctms.acceptance.steps.utils.CtmsPadClient.getRequestWithParams;
import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

public class GetStationSteps {

    private static final String STATION_END_POINT = "services/v1/station?page={page}&pageSize={pageSize}";
    private Map<String, String> pathVariables;
    private ResponseEntity<CtmsResponse> response;

    @When("User requests station details")
    public void getRequest() {
        pathVariables = new HashMap<>();
        pathVariables.put("page", "0");
        pathVariables.put("pageSize", "10");
        response = getRequestWithParams(STATION_END_POINT, CtmsResponse.class, pathVariables);
    }

    @Then("service should provide station details")
    public void assertResponse() {
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        if (!isNull(response.getBody().getData())) {
            ObjectMapper om = new ObjectMapper();
            PaginatedResponse pr = om.convertValue(response.getBody().getData(), PaginatedResponse.class);
            List<Station> stationList = om.convertValue(pr.getData(), new TypeReference<>() {});

            assertThat((pr.getPageSize())).isEqualTo(10);
            assertThat(pr.getData().isEmpty()).isFalse();
            assertThat(stationList).isNotEmpty();
        }
    }
}
