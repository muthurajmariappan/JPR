package org.example.jpr.validation;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.Constants;
import org.example.jpr.util.ProcessBuilderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class AzureAppServiceValidatorContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(AzureAppServiceValidatorContributor.class);
    private final String ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";

    @Override
    public void contribute(PlanContext context) {
        logger.info("---------- Validating the spring project in Azure App Service ----------");
        if (validate(context)) {
            logger.info("---------- Validation of spring project in Azure App Service successful ----------");
        } else {
            throw new RuntimeException("Validation of spring project in Azure App Service  failed");
        }
    }

    private boolean validate(PlanContext context) {
        String url = context.getOutputVariable(Constants.OUTPUT_VARIABLES.APP_SERVICE_URL) + ACTUATOR_HEALTH_ENDPOINT;
        logger.info("curling app service at " + url);
        String result = executeCommand(
                context,
                "curl",
                url

        );
        return result.contains("{\"status\":\"UP\"}");
    }

    private String executeCommand(PlanContext context, String... args) {
        return ProcessBuilderClient.executeCommand(Arrays.stream(args).toList(), context.getProjectDir());
    }

    @Override
    public String toString() {
        return "AzureAppServiceValidatorContributor";
    }
}
