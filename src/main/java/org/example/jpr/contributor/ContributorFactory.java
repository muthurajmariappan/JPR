package org.example.jpr.contributor;

import org.example.jpr.generation.CICDContributor;
import org.example.jpr.generation.SpringProjectContributor;
import org.example.jpr.generation.SpringProjectValidationContributor;
import org.example.jpr.generation.SpringRestControllerContributor;
import org.example.jpr.provision.AzureAppServiceProvisionContributor;
import org.example.jpr.scm.SCMContributor;
import org.example.jpr.util.Constants;
import org.example.jpr.validation.AzureAppServiceValidatorContributor;

import java.util.Collections;
import java.util.List;

public class ContributorFactory {

    public static List<Contributor> getContributors(Constants.STAGES stage, Constants.PROJECT_TYPES type) {
        return switch (stage) {
            case GENERATION -> generationContributors(type);
            case PROVISION -> provisionContributors(type);
            case SCM -> scmContributors(type);
            case VALIDATION -> validationContributors(type);
        };
    }

    private static List<Contributor> generationContributors(Constants.PROJECT_TYPES type) {
        return switch (type) {
            case JAVA_REST_SERVICE -> List.of(
                    new SpringProjectContributor(),
                    new SpringRestControllerContributor(),
                    new SpringProjectValidationContributor(),
                    new CICDContributor()
            );
            case JAVA_LIBRARY -> Collections.emptyList();
        };
    }

    private static List<Contributor> scmContributors(Constants.PROJECT_TYPES type) {
        return switch (type) {
            case JAVA_REST_SERVICE -> List.of(new SCMContributor());
            case JAVA_LIBRARY -> Collections.emptyList();
        };
    }

    private static List<Contributor> provisionContributors(Constants.PROJECT_TYPES type) {
        return switch (type) {
            case JAVA_REST_SERVICE -> List.of(
                    new AzureAppServiceProvisionContributor()
            );
            case JAVA_LIBRARY -> Collections.emptyList();
        };
    }

    private static List<Contributor> validationContributors(Constants.PROJECT_TYPES type) {
        return switch (type) {
            case JAVA_REST_SERVICE -> List.of(
                    new AzureAppServiceValidatorContributor()
            );
            case JAVA_LIBRARY -> Collections.emptyList();
        };
    }
}
