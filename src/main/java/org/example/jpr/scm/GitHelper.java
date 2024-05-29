package org.example.jpr.scm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

public class GitHelper {

    private final Logger logger = LoggerFactory.getLogger(GitHelper.class);

    String pd;

    GitHelper(String pd) {
        this.pd = pd;
    }

    public static void main(String[] args) throws GitAPIException, URISyntaxException {
        GitHelper helper = new GitHelper("D:\\temp\\demo13584932201506709911\\demo");
        helper.initAndPush();
    }

    public void initAndPush() throws GitAPIException, URISyntaxException {
        try (Git git = Git.init().setDirectory(new File(pd)).call()) {
            git.add().addFilepattern(".").call();
            git.commit()
                .setMessage("Commit all changes including additions")
                .call();

            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish("https://github.com/muthurajmariappan/demo"));
            remoteAddCommand.call();

            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(
                    new UsernamePasswordCredentialsProvider("muthurajmariappan", "introvert*1"));
            pushCommand.call()
                    .forEach(result -> logger.info(result.getMessages()));
        }
    }
}
