package org.example.jpr.scm;

import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.Repos;
import com.jcabi.github.RtGithub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GHClient {

    private static final Logger logger = LoggerFactory.getLogger(GHClient.class);

    public static void main(String[] args) throws IOException {
        Github github = new RtGithub("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");
        Repos.RepoCreate create = new Repos.RepoCreate("demo", true);
        Repo repo = github.repos().create(create);
        logger.info("https://github.com/muthurajmariappan/" + repo);
    }
}
