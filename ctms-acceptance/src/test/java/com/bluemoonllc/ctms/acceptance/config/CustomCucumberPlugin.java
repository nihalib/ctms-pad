package com.bluemoonllc.ctms.acceptance.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import lombok.extern.slf4j.Slf4j;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Slf4j
public class CustomCucumberPlugin implements EventListener {

    private WireMockServer wireMockServer;

    private EventHandler<TestRunStarted> setup = event -> {
        initWireMock();
    };

    private EventHandler<TestRunFinished> teardown = event -> {
        closeWireMock();
    };

    private void initWireMock() {
        log.info("initWireMock at port: 9008");
        wireMockServer = new WireMockServer(options().port(9008));
        wireMockServer.start();
        new WireMockStubConfig(wireMockServer).initStubs();
    }

    private void closeWireMock() {
        wireMockServer.stop();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, setup);
        publisher.registerHandlerFor(TestRunFinished.class, teardown);
    }
}
