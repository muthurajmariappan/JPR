package org.example.jpr.util;

public class Constants {

    public enum STAGES {
        GENERATION,
        PROVISION,
        SCM,
        VALIDATION
    }

    public enum OUTPUT_VARIABLES {
        SCM_REPO_URL
    }

    public enum PROJECT_TYPES {
        JAVA_LIBRARY,
        JAVA_REST_SERVICE
    }

    public static final String GROUP_ID = "com.example";

    public static final String SRC_DIR = "src.main.java";
    public static final String FTL_EXTENSION = ".ftl";

}
