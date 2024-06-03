package org.example.jpr.stages;

import org.example.jpr.contributor.ContributorFactory;
import org.example.jpr.util.Constants;

public class StageFactory {

    public static Stage create(Constants.STAGES stage, Constants.PROJECT_TYPES type) {
        return switch (stage) {
            case GENERATION -> new GenerationStage(ContributorFactory.getContributors(stage, type));
            case PROVISION -> new ProvisionStage(ContributorFactory.getContributors(stage, type));
            case SCM -> new SCMStage(ContributorFactory.getContributors(stage, type));
            case VALIDATION -> new ValidationStage(ContributorFactory.getContributors(stage, type));
        };
    }
}
