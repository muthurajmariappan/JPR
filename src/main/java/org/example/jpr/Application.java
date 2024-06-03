package org.example.jpr;

import org.example.jpr.context.PlanContext;
import org.example.jpr.orchestrator.JPROrchestrator;
import org.example.jpr.plan.Plan;
import org.example.jpr.plan.PlanBuilder;
import org.example.jpr.stages.StageFactory;
import org.example.jpr.util.ApplicationInputUtil;
import org.example.jpr.util.Constants;
import org.example.jpr.util.FreeMarkerUtil;
import org.example.jpr.context.ProjectDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        FreeMarkerUtil.configure();
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String[] args) {
        System.out.println("Welcome to the Java Paved Road!!!!!!!!!!!!!!");
        ProjectDefinition projectDefinition = ApplicationInputUtil.getProjectDefinition();

        logger.info("######### Starting the project creation process #########");
        logger.info(
                String.format(
                        "For Project Type - %s, Project Name - %s",
                        projectDefinition.projectType(),
                        projectDefinition.projectName()
                )
        );
        PlanContext context = new PlanContext(projectDefinition.projectName());
        Plan plan = buildPlan();
        logger.info(plan.toString());
        JPROrchestrator orchestrator = new JPROrchestrator(plan, context);
        orchestrator.orchestrate();
        logger.info("######### Finished the project creation process #########");
    }

    private Plan buildPlan() {
        return new PlanBuilder()
                .generationStage(
                        StageFactory.create(
                                Constants.STAGES.GENERATION,
                                Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                        )
                )
                .scmStage(
                        StageFactory.create(
                                Constants.STAGES.SCM,
                                Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                        )
                )
                .provisionStage(
                        StageFactory.create(
                                Constants.STAGES.PROVISION,
                                Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                        )
                )
                .validationStage(
                        StageFactory.create(
                                Constants.STAGES.VALIDATION,
                                Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                        )
                )
                .build();
    }

}