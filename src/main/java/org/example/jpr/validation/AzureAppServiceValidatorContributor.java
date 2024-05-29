package org.example.jpr.validation;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.ProcessBuilderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class AzureAppServiceValidatorContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(AzureAppServiceValidatorContributor.class);

    @Override
    public void contribute(PlanContext context) {
        logger.info("Validating the spring project in Azure App Service");
        List<Predicate<Boolean>> predicates = new ArrayList<>();
        predicates.add(o -> {
            String result = executeCommand(context, "curl", "http://localhost:8080/actuator/health");
            return result.contains("\"content\":\"Hello, ttt!\"");
        });
        predicates.add(o -> {
            String result = executeCommand(context, "curl", "http://localhost:8080/greeting");
            return result.contains("\"content\":\"Hello, ttt!\"");
        });
        predicates.add(o -> {
            String result = executeCommand(context, "curl", "curl http://localhost:8080/greeting?name=ttt");
            return result.contains("\"content\":\"Hello, ttt!\"");
        });
        if (!predicates.stream().allMatch(t -> t.test(true))) {
            throw new RuntimeException("Validation of spring project in Azure App Service successful failed");
        }
        logger.info("Validation of spring project in Azure App Service successful");
    }

    private String executeCommand(PlanContext context, String... args) {
        return ProcessBuilderClient.executeCommand(Arrays.stream(args).toList(), context.getProjectDir());
    }

    @Override
    public String toString() {
        return "AzureAppServiceValidatorContributor";
    }
}
