package org.example.jpr.scm;

import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.Repos;
import com.jcabi.github.RtGithub;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.ProcessBuilderClient;
import org.example.jpr.context.PlanContext;
import org.example.jpr.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SCMContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(SCMContributor.class);

    @Override
    public void contribute(PlanContext context) {
        try {
            context.addOutputVariable(Constants.OUTPUT_VARIABLES.SCM_REPO_URL, createRepository(context));
            pushInitial(context, copyHelper(context));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "SCMContributor";
    }

    private String createRepository(PlanContext context) throws IOException {
        Github github = new RtGithub("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");
        Repos.RepoCreate create = new Repos.RepoCreate(context.getProjectName(), true);
        Repo repo = github.repos().create(create);
        String repoUrl = "https://github.com/" + repo;
        logger.info(repoUrl);
        return repoUrl;
    }

    private void pushInitial(PlanContext context, String helperPath) {
        List<String> cmdArgs = new ArrayList<>();
        cmdArgs.add(helperPath);
        cmdArgs.add(context.getOutputVariable(Constants.OUTPUT_VARIABLES.SCM_REPO_URL));
        cmdArgs.add(Paths.get(context.getScmProjectDir()).getFileName().toString());
        cmdArgs.add(context.getProjectName());
        ProcessBuilderClient.executeCommand(cmdArgs, context.getBaseProjectDir());
    }

    private String copyHelper(PlanContext context) throws IOException {
        ClassPathResource cr = new ClassPathResource("git-helper.bat");
        Path crPath = cr.getFile().toPath();
        return Files.copy(
                crPath,
                Paths.get(context.getBaseProjectDir()).resolve(crPath.getFileName())
        ).toAbsolutePath().toString();
    }
}
