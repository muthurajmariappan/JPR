package org.example.jpr.stages;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;

import java.util.List;

public class GenerationStage implements Stage {

    List<Contributor> contributors;

    GenerationStage(List<Contributor> contributors) {
        this.contributors = contributors;
    }
    @Override
    public void orchestrate(PlanContext context) {
        contributors.forEach(contributor -> contributor.contribute(context));
    }

    @Override
    public String toString() {
        return """
                GenerationStage {
                        contributors=%s
                    }""".formatted(contributors.toString());
    }
}
