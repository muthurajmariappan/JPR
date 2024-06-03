package org.example.jpr.contributor;

import org.example.jpr.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContributorFactoryTest {

    @Test
    void getContributors_test_generation() {
        Assertions.assertEquals(4,
                ContributorFactory.getContributors(
                Constants.STAGES.GENERATION,
                Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                ).size()
        );
    }

    @Test
    void getContributors_test_scm() {
        Assertions.assertEquals(1,
                ContributorFactory.getContributors(
                        Constants.STAGES.SCM,
                        Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                ).size()
        );
    }

    @Test
    void getContributors_test_provision() {
        Assertions.assertEquals(1,
                ContributorFactory.getContributors(
                        Constants.STAGES.PROVISION,
                        Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                ).size()
        );
    }

    @Test
    void getContributors_test_validation() {
        Assertions.assertEquals(1,
                ContributorFactory.getContributors(
                        Constants.STAGES.VALIDATION,
                        Constants.PROJECT_TYPES.JAVA_REST_SERVICE
                ).size()
        );
    }
}
