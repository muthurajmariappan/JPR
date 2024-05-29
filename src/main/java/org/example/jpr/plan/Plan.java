package org.example.jpr.plan;

import org.example.jpr.stages.Stage;

public class Plan {

    Stage generationStage;
    Stage scmStage;
    Stage provisionStage;
    Stage validationStage;

    public Stage getGenerationStage() {
        return generationStage;
    }

    public void setGenerationStage(Stage generationStage) {
        this.generationStage = generationStage;
    }

    public Stage getScmStage() {
        return scmStage;
    }

    public void setScmStage(Stage scmStage) {
        this.scmStage = scmStage;
    }

    public Stage getProvisionStage() {
        return provisionStage;
    }

    public void setProvisionStage(Stage provisionStage) {
        this.provisionStage = provisionStage;
    }

    public Stage getValidationStage() {
        return validationStage;
    }

    public void setValidationStage(Stage validationStage) {
        this.validationStage = validationStage;
    }

    @Override
    public String toString() {
        return """
                Plan {
                    %s
                    %s
                    %s
                    %s
                }
                """.formatted(generationStage, scmStage, provisionStage, validationStage);
    }
}
