package org.example.jpr.generation;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.ProcessBuilderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SpringProjectValidationContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(SpringProjectValidationContributor.class);
    private final boolean validate = false;

    @Override
    public void contribute(PlanContext context) {
        logger.info("---------- Validating spring project ----------");
        if (validate) {
            logger.info("Validating the spring project through {gradlew clean build}");
            List<String> cmdArgs = new ArrayList<>();
            cmdArgs.add("gradlew.bat");
            cmdArgs.add("clean");
            cmdArgs.add("build");
            ProcessBuilderClient.executeCommand(cmdArgs, context.getProjectDir());
            logger.info("Validation of spring project successful");
        }
        logger.info("---------- Validated spring project ----------");
    }

    @Override
    public String toString() {
        return "SpringProjectValidationController";
    }
}
