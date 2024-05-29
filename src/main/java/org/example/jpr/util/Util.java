package org.example.jpr.util;

import org.example.jpr.context.PlanContext;

public class Util {

    public static String getPackageName(PlanContext context) {
        return Constants.GROUP_ID + "." + context.getProjectName();
    }

    public static String removeFtlExtension(String input) {
        return removeExtension(input, Constants.FTL_EXTENSION);
    }

    public static String removeExtension(String input, String extension) {
        return input.split(extension)[0];
    }
}
