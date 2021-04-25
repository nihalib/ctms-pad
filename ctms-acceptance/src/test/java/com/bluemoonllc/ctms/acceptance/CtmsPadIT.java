package com.bluemoonllc.ctms.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-reports/reports.html",
        "json:target/cucumber-reports/report.json",
        "com.bluemoonllc.ctms.acceptance.config.CustomCucumberPlugin"},
        glue = {"com.bluemoonllc.ctms.acceptance", "cucumber.api.spring"},
        features = {"src/test/resources/features/"})
public class CtmsPadIT {
}
