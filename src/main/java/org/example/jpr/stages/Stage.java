package org.example.jpr.stages;

import org.example.jpr.context.PlanContext;

public interface Stage {

    void orchestrate(PlanContext context);

}