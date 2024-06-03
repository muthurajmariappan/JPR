package org.example.jpr.generation;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.FreeMarkerUtil;
import org.example.jpr.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CICDContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(CICDContributor.class);
    private final String CI_TEMPLATE = "PR.yaml.ftl";
    private final String CD_TEMPLATE = "CD.yaml.ftl";
    private final String GIT_HUB_ACTION_DIR = ".github/workflows";

    @Override
    public void contribute(PlanContext context) {
        logger.info("---------- Creating CI/CD pipeline definitions ----------");
        Path path = Paths.get(
                context.getProjectDir(),
                GIT_HUB_ACTION_DIR
        );
        path.toFile().mkdirs();
        Map<String, Object> input = new HashMap<>();
        input.put("projectName", context.getProjectName());
        FreeMarkerUtil.renderTemplate(
                CI_TEMPLATE,
                path.resolve(Util.removeFtlExtension(CI_TEMPLATE)).toAbsolutePath().toString(),
                input
        );
        FreeMarkerUtil.renderTemplate(
                CD_TEMPLATE,
                path.resolve(Util.removeFtlExtension(CD_TEMPLATE)).toAbsolutePath().toString(),
                input
        );
        logger.info("---------- Created CI/CD pipeline definitions ----------");
    }

    @Override
    public String toString() {
        return "CICDContributor";
    }
}
