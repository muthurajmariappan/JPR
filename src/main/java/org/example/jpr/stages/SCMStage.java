package org.example.jpr.stages;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;

import java.util.List;

public class SCMStage implements Stage {

    List<Contributor> contributors;
    SCMStage(List<Contributor> contributors) {
        this.contributors = contributors;
    }
    @Override
    public void orchestrate(PlanContext context) {
        contributors.forEach(contributor -> contributor.contribute(context));
    }

    @Override
    public String toString() {
        return """
               SCMStage {
                       contributors=%s
                   }""".formatted(contributors.toString());
    }
}
