package org.example.jpr.orchestrator;

import org.example.jpr.context.PlanContext;
import org.example.jpr.plan.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPROrchestrator {

    private final Logger logger = LoggerFactory.getLogger(JPROrchestrator.class);
    Plan plan;
    PlanContext context;
    public JPROrchestrator(Plan plan, PlanContext context) {
        this.plan = plan;
        this.context = context;
    }

    public void orchestrate() {
        logger.info("######### Orchestrating the project creation #########");
        plan.getGenerationStage().orchestrate(context);
        plan.getProvisionStage().orchestrate(context);
        plan.getScmStage().orchestrate(context);
        plan.getValidationStage().orchestrate(context);
        logger.info("######### Completed orchestrating the project creation #########");
    }
}
