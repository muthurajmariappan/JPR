package org.example.jpr.generation;

import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.Constants;
import org.example.jpr.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SpringProjectContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(SpringProjectContributor.class);

    private final Map<String, String> ARGUMENT_MAP = new HashMap<>();

    @Override
    public String toString() {
        return "SpringProjectContributor";
    }

    public SpringProjectContributor() {
        this.ARGUMENT_MAP.put("type", "gradle-project");
        this.ARGUMENT_MAP.put("language", "java");
        this.ARGUMENT_MAP.put("bootVersion", "3.3.0");
        this.ARGUMENT_MAP.put("baseDir", "");
        this.ARGUMENT_MAP.put("groupId", Constants.GROUP_ID);
        this.ARGUMENT_MAP.put("artifactId", "");
        this.ARGUMENT_MAP.put("name", "");
        this.ARGUMENT_MAP.put("description", "RESTFul web service");
        this.ARGUMENT_MAP.put("packageName", "");
        this.ARGUMENT_MAP.put("packaging", "jar");
        this.ARGUMENT_MAP.put("javaVersion", "17");
        this.ARGUMENT_MAP.put("dependencies", "web,actuator");
    }

    @Override
    public void contribute(PlanContext context) {
        logger.info("---------- Creating base project using spring initializr ----------");
        String zip = Paths.get(
                context.getBaseProjectDir(),
                context.getProjectName() + ".zip")
                .toAbsolutePath().toString();
        try {
            updateArgumentsMap(context);
            download(zip);
            extract(zip, context.getBaseProjectDir());
            logger.info("---------- Created base project using spring initializr ----------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void download(String zip) throws IOException {
        String url = buildDownloadUrl();
        Resource resource = UrlResource.from(url);
        byte[] bytes = resource.getContentAsByteArray();
        logger.info("spring initializr length:" + bytes.length);
        try (FileOutputStream zos = new FileOutputStream(zip)) {
            zos.write(bytes);
        }
    }

    private String buildDownloadUrl() {
        String SPRING_INITIALIZR_BASE_URI = "https://start.spring.io/starter.zip";
        StringBuilder builder = new StringBuilder(SPRING_INITIALIZR_BASE_URI);
        builder.append("?");
        ARGUMENT_MAP.forEach((key, value) -> builder
                .append(key)
                .append("=")
                .append(value)
                .append("&"));
        return builder.toString();
    }

    private void updateArgumentsMap(PlanContext context) {
        ARGUMENT_MAP.put("baseDir", context.getProjectName());
        ARGUMENT_MAP.put("artifactId", context.getProjectName());
        ARGUMENT_MAP.put("name", context.getProjectName());
        ARGUMENT_MAP.put("packageName", Util.getPackageName(context));
    }

    private void extract(String zip, String pd) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(new File(pd), zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
