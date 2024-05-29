package org.example.jpr.context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkDir {

    private final String workingDir;
    private final String baseProjectDir;
    private final String scmProjectDir;
    private final String SCM_DIR = "scm-wd";
    public WorkDir(String root, String projectName) {
        try {
            this.workingDir = Files.createTempDirectory(Path.of(root), "project").toAbsolutePath().toString();
            this.baseProjectDir = workingDir;
            this.scmProjectDir = Paths.get(workingDir, SCM_DIR).toAbsolutePath().toString();
            if (!new File(workingDir).mkdirs() &&
            new File(baseProjectDir).mkdirs() &&
            new File(scmProjectDir).mkdir()) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getWorkingDir() {
        return workingDir;
    }

    public String getBaseProjectDir() {
        return baseProjectDir;
    }

    public String getScmProjectDir() {
        return scmProjectDir;
    }

    public String getSCM_DIR() {
        return SCM_DIR;
    }
}
