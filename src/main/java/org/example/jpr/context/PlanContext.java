package org.example.jpr.context;

import org.example.jpr.util.Constants;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PlanContext {
    private final String projectName;
    private final WorkDir workDir;

    private final Map<Constants.OUTPUT_VARIABLES, String> outputVariables = new HashMap<>();

    public PlanContext(String projectName) {
        this.projectName = projectName;
        this.workDir = new WorkDir("D:\\temp", projectName);
    }

    public void addOutputVariable(Constants.OUTPUT_VARIABLES variable, String value) {
        outputVariables.put(variable, value);
    }

    public String getOutputVariable(Constants.OUTPUT_VARIABLES outputVariable) {
        return outputVariables.get(outputVariable);
    }

    public String getProjectName() {
        return projectName;
    }

    public WorkDir getWorkDir() {
        return workDir;
    }

    public String getBaseProjectDir() {
        return workDir.getBaseProjectDir();
    }

    public String getScmProjectDir() {
        return workDir.getScmProjectDir();
    }

    public String getProjectDir() {
        return Paths.get(getBaseProjectDir(), projectName).toAbsolutePath().toString();
    }
}
