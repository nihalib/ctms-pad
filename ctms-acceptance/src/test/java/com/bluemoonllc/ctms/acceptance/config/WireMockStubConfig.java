package com.bluemoonllc.ctms.acceptance.config;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class WireMockStubConfig {

    private final WireMockServer wireMockServer;

    public WireMockStubConfig(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    public void initStubs() {
        stubForAddTariff();
    }

    private void stubForAddTariff() {
        wireMockServer.stubFor(post(urlEqualTo("/tariff-plan"))
                .withRequestBody(matchingJsonPath("$.currencyCode"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("tariff/addTariff.json")));
    }
}
