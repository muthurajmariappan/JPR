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

    public static String getGitHubToken() {
        return System.getenv(Constants.GITHUB_TOKEN_VARIABLE);
    }

    public static String getAzureClientId() {
        return System.getenv(Constants.AZURE_CLIENT_ID_VARIABLE);
    }

    public static String getAzureClientSecret() {
        return System.getenv(Constants.AZURE_CLIENT_SECRET_VARIABLE);
    }

    public static String getAzureSubscriptionId() {
        return System.getenv(Constants.AZURE_SUBSCRIPTION_ID_VARIABLE);
    }

    public static String getAzureTenantId() {
        return System.getenv(Constants.AZURE_TENANT_ID_VARIABLE);
    }
}
