package org.example.jpr.util;

public class Constants {

    public enum STAGES {
        GENERATION,
        PROVISION,
        SCM,
        VALIDATION
    }

    public enum OUTPUT_VARIABLES {
        SCM_REPO_URL,
        APP_SERVICE_URL
    }

    public enum PROJECT_TYPES {
        JAVA_LIBRARY,
        JAVA_REST_SERVICE;

        public static PROJECT_TYPES getType(int index) {
            return PROJECT_TYPES.values()[index];
        }
    }

    public static final String GROUP_ID = "com.example";
    public static final String SRC_DIR = "src.main.java";
    public static final String FTL_EXTENSION = ".ftl";
    public static final String GITHUB_TOKEN_VARIABLE = "GITHUB_TOKEN";
    public static final String AZURE_CLIENT_ID_VARIABLE = "AZURE_CLIENT_ID";
    public static final String AZURE_CLIENT_SECRET_VARIABLE = "AZURE_CLIENT_SECRET";
    public static final String AZURE_TENANT_ID_VARIABLE = "AZURE_TENANT_ID";
    public static final String AZURE_SUBSCRIPTION_ID_VARIABLE = "AZURE_SUBSCRIPTION_ID";
}
