package org.example.jpr.util;

import org.example.jpr.context.ProjectDefinition;

import java.util.Scanner;

public class ApplicationInputUtil {

    public static ProjectDefinition getProjectDefinition() {
        Scanner scanner = new Scanner(System.in);
        showOptions();
        int type = getOption(scanner);
        String projectName = getProjectName(scanner);
        return new ProjectDefinition(Constants.PROJECT_TYPES.getType(type), projectName);
    }

    public static String getProjectName(Scanner scanner) {
        System.out.println("Enter project name");
        String projectName = scanner.next();
        if (projectName == null || projectName.isEmpty()) {
            getProjectName(scanner);
        }
        return projectName;
    }

    public static int getOption(Scanner scanner) {
        System.out.println("Choose an option");
        int num = scanner.nextInt();
        if (num < 1 || num > 2) {
            getOption(scanner);
        }
        return num;
    }

    public static void showOptions() {
        System.out.println("Below are the options to create a project:");
        System.out.println("1. Java Web Service");
        System.out.println("2. Java Library");
    }
}

