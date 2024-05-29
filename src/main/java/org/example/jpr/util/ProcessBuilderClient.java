package org.example.jpr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessBuilderClient {

    private static final Logger logger = LoggerFactory.getLogger(ProcessBuilderClient.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> cmds = new ArrayList<>();
        String path = "D:\\temp\\demo4977116046961940435\\git-helper.bat";
        cmds.add(path);
        executeCommand(cmds, "D:\\temp\\demo4977116046961940435");
    }

    public static String executeCommand(List<String> cmds, String workDir) {
        try {
            ProcessBuilder builder = new ProcessBuilder(cmds);
            StringBuilder output = new StringBuilder();
            Map<String, String> environ = builder.environment();
            builder.directory(new File(workDir));

            final Process process = builder.start();

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader ibr = new BufferedReader(isr);
            String iline;
            while ((iline = ibr.readLine()) != null) {
                System.out.println(iline);
            }
            ibr.close();

            InputStream es = process.getErrorStream();
            InputStreamReader esr = new InputStreamReader(es);
            BufferedReader ebr = new BufferedReader(esr);
            String eline;
            while ((eline = ebr.readLine()) != null) {
                System.out.println(eline);
                output.append(eline);
            }
            ebr.close();

            process.waitFor();

            return output.toString();
        } catch (InterruptedException e) {
            logger.error("InterruptedException for cmd and arguments: " + String.join(",", cmds));
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("IOException for cmd and arguments: " + String.join(",", cmds));
            throw new RuntimeException(e);
        }
    }
}
