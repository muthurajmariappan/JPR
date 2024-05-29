package org.example.jpr.stages;

import org.example.jpr.contributor.Contributor;
import org.example.jpr.context.PlanContext;

import java.util.List;

public class ProvisionStage implements Stage {

    List<Contributor> contributors;
    ProvisionStage(List<Contributor> contributors) {
        this.contributors = contributors;
    }
    @Override
    public void orchestrate(PlanContext context) {
        contributors.forEach(contributor -> contributor.contribute(context));
    }

    @Override
    public String toString() {
        return  """
                ProvisionStage {
                        contributors=%s
                    }""".formatted(contributors.toString());
    }
}
