package org.example.jpr.plan;

import org.example.jpr.stages.Stage;

public class PlanBuilder {

    Plan plan = new Plan();

    public PlanBuilder generationStage(Stage stage) {
        plan.setGenerationStage(stage);
        return this;
    }

    public PlanBuilder scmStage(Stage stage) {
        plan.setScmStage(stage);
        return this;
    }

    public PlanBuilder provisionStage(Stage stage) {
        plan.setProvisionStage(stage);
        return this;
    }

    public PlanBuilder validationStage(Stage stage) {
        plan.setValidationStage(stage);
        return this;
    }

    public Plan build() {
        return plan;
    }
}
