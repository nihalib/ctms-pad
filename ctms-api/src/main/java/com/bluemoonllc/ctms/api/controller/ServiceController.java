package com.bluemoonllc.ctms.api.controller;

import com.bluemoonllc.ctms.api.service.TariffService;
import com.bluemoonllc.ctms.api.service.bi.TariffBI;
import com.bluemoonllc.ctms.api.swagger.model.DataNotFoundResponse;
import com.bluemoonllc.ctms.api.swagger.model.PaginatedStationSuccessResponse;
import com.bluemoonllc.ctms.api.swagger.model.PaginatedTariffSuccessResponse;
import com.bluemoonllc.ctms.api.swagger.model.UnProcessableReponse;
import com.bluemoonllc.ctms.api.swagger.model.UpdateStationSuccessResponse;
import com.bluemoonllc.ctms.api.swagger.model.UpdateTariffSuccessResponse;
import com.bluemoonllc.ctms.model.CtmsResponse;
import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ctms.model.Tariff;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/services/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController {

    private final TariffBI service;

    public ServiceController(TariffService service) {
        this.service = service;
    }

    @Operation(operationId = "postStation", tags = "Station", summary = "Add station details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station list successfully provided",
                    content = @Content(schema = @Schema(implementation = UpdateStationSuccessResponse.class)))
    })
    @PostMapping(value = "/station", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CtmsResponse> addNewStation(@Validated @RequestBody Station station,
                                                      @RequestHeader(value = "X-Test-Mode", defaultValue = "false")
                                                              Boolean testMode) {
        CtmsResponse response = service.addNewStation(station, testMode);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "getStation", tags = "Station",
            summary = "Provides list of available station details in paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Station list successfully provided",
                    content = @Content(schema = @Schema(implementation = PaginatedStationSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "/station")
    public ResponseEntity<CtmsResponse> getStationDetails(@RequestHeader(value = "X-Fetch-Mode", required = false,
                                                         defaultValue = "") String fetchType,
                                                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(name = "pageSize", defaultValue = "1")
                                                                      Integer pageSize) {
        CtmsResponse response = service.getStation(fetchType, page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "postTariff", tags = "Tariff", summary = "Add tariff details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tariff list successfully provided",
                    content = @Content(schema = @Schema(implementation = UpdateTariffSuccessResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable data",
                    content = @Content(schema = @Schema(implementation = UnProcessableReponse.class)))
    })
    @PostMapping(value = "/tariff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CtmsResponse> addTariffDetails(@RequestBody @Validated Tariff tariff,
                                                         @RequestHeader(value = "X-Test-Mode", defaultValue = "false")
                                                                 Boolean testMode){
        CtmsResponse response = service.addTariff(tariff, testMode);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "getTariff", tags = "Tariff",
            summary = "Provides list of available tariff details in paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tariff list successfully provided",
                    content = @Content(schema = @Schema(implementation = PaginatedTariffSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "/tariff")
    public ResponseEntity getTariff(@RequestHeader(value = "X-Fetch-Mode", required = false, defaultValue = "") String fetchType,
                                    @RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize) {
        CtmsResponse response = service.getTariff(fetchType, page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }
}
