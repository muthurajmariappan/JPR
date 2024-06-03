package org.example.jpr.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.springframework.core.io.ClassPathResource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FreeMarkerUtil {

    private static Configuration configuration;

    public static void main(String[] args) throws IOException {
        configure();
        Map<String, Object> input = new HashMap<>();
        input.put("name", "John Doe");
        renderTemplate(
                "hello.ftl",
                new ClassPathResource("controller").getFile().getPath() + "\\output.html",
                input
        );
    }

    public static void configure() {
        configuration = new Configuration(new Version(2, 3, 32));
        configuration.setClassForTemplateLoading(FreeMarkerUtil.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);
    }

    public static void renderTemplate(String templateName, String renderPath, Map<String, Object> input) {
        try(Writer fileWriter = new FileWriter(renderPath)) {
            Template template = configuration.getTemplate(templateName);

            Writer consoleWriter = new OutputStreamWriter(System.out);
            template.process(input, consoleWriter);

            template.process(input, fileWriter);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
