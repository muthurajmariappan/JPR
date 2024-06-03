package org.example.jpr.scm;

import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.Repos;
import com.jcabi.github.RtGithub;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.ProcessBuilderClient;
import org.example.jpr.context.PlanContext;
import org.example.jpr.util.Constants;
import org.example.jpr.util.Util;
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
        logger.info("---------- Creating repository for project ----------");
        try {
            context.addOutputVariable(Constants.OUTPUT_VARIABLES.SCM_REPO_URL, createRepository(context));
            pushInitial(context, copyHelper(context));
            createRepositorySecret(context);
            logger.info("---------- Created repository for project and pushed the files ----------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "SCMContributor";
    }

    private String createRepository(PlanContext context) throws IOException {
        Github github = new RtGithub(Util.getGitHubToken());
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

    private void createRepositorySecret(PlanContext context) {
        List<String> cmdArgs = new ArrayList<>();
        cmdArgs.add("curl");
        cmdArgs.add(String.format("http://localhost:3000/secret/muthurajmariappan/%s/AZURE_CREDENTIALS", context.getProjectName()));
        ProcessBuilderClient.executeCommand(cmdArgs, context.getBaseProjectDir());
        logger.info("Added secret to " + context.getOutputVariable(Constants.OUTPUT_VARIABLES.SCM_REPO_URL));
    }
}
