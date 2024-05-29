package org.example.jpr.orchestrator;

import org.example.jpr.context.PlanContext;
import org.example.jpr.plan.Plan;

public class JPROrchestrator {

    Plan plan;
    PlanContext context;
    public JPROrchestrator(Plan plan, PlanContext context) {
        this.plan = plan;
        this.context = context;
    }

    public void orchestrate() {
        plan.getGenerationStage().orchestrate(context);
        plan.getProvisionStage().orchestrate(context);
        plan.getScmStage().orchestrate(context);
        plan.getValidationStage().orchestrate(context);
    }
}
