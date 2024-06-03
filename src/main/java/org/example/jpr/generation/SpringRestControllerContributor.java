package org.example.jpr.generation;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.Constants;
import org.example.jpr.util.FreeMarkerUtil;
import org.example.jpr.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SpringRestControllerContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(SpringRestControllerContributor.class);
    private final String CONTROLLER_TEMPLATE = "GreetingController.java.ftl";
    private final String CONTROLLER_DTO_TEMPLATE = "Greeting.java.ftl";

    @Override
    public void contribute(PlanContext context) {
        logger.info("---------- Creating REST Controller and DTO ----------");
        String packageName = Util.getPackageName(context);
        Path path = Paths.get(
                context.getProjectDir(),
                Constants.SRC_DIR.concat("." + packageName).concat(".rest").split("\\.")
        );
        path.toFile().mkdirs();
        Map<String, Object> input = new HashMap<>();
        input.put("projectName", context.getProjectName());

        FreeMarkerUtil.renderTemplate(
                CONTROLLER_TEMPLATE,
                path.resolve(Util.removeFtlExtension(CONTROLLER_TEMPLATE)).toAbsolutePath().toString(),
                input
        );
        FreeMarkerUtil.renderTemplate(
                CONTROLLER_DTO_TEMPLATE,
                path.resolve(Util.removeFtlExtension(CONTROLLER_DTO_TEMPLATE)).toAbsolutePath().toString(),
                input
        );
        logger.info("---------- Created REST Controller and DTO ----------");
    }

    @Override
    public String toString() {
        return "SpringRestControllerContributor";
    }
}
