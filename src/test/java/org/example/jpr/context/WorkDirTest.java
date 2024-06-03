package org.example.jpr.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorkDirTest {

    @Test
    void workDir() throws IOException {
        Path root = Files.createTempDirectory("ut");
        String rootPath = root.toAbsolutePath().toString();
        WorkDir workDir = new WorkDir(rootPath, "aaa");
        Assertions.assertTrue(
                workDir.getWorkingDir().startsWith(rootPath)
        );
        Assertions.assertTrue(
                workDir.getBaseProjectDir().startsWith(rootPath)
        );
        Assertions.assertTrue(
                workDir.getScmProjectDir().startsWith(rootPath)
        );
        Assertions.assertTrue(
                workDir.getScmProjectDir().contains("scm-wd")
        );
    }
}
