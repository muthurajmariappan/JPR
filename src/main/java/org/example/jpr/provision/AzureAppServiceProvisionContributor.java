package org.example.jpr.provision;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.Region;
import com.azure.core.management.profile.AzureProfile;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.appservice.models.JavaVersion;
import com.azure.resourcemanager.appservice.models.PricingTier;
import com.azure.resourcemanager.appservice.models.WebApp;
import com.azure.resourcemanager.appservice.models.WebContainer;
import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AzureAppServiceProvisionContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(AzureAppServiceProvisionContributor.class);
    private static final String SUFFIX = ".azurewebsites.net";

    @Override
    public void contribute(PlanContext context) {
        logger.info("Provisioning Azure App Service");
//        WebApp app = azure.webApps().define("newLinuxWebApp")
//                .withExistingLinuxPlan(myLinuxAppServicePlan)
//                .withExistingResourceGroup("myResourceGroup")
//                .withPrivateDockerHubImage("username/my-java-app")
//                .withCredentials("dockerHubUser","dockerHubPassword")
//                .withAppSetting("PORT","8080")
//                .create();
        logger.info("Provisioned Azure App Service");
    }

    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate
            final AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
            final TokenCredential credential = new DefaultAzureCredentialBuilder()
                    .authorityHost(profile.getEnvironment().getActiveDirectoryEndpoint())
                    .build();

            AzureResourceManager azureResourceManager = AzureResourceManager
                    .configure()
                    .withLogLevel(HttpLogDetailLevel.BASIC)
                    .authenticate(credential, profile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azureResourceManager.subscriptionId());

            runSample(azureResourceManager);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static WebApp createWebApp(
            AzureResourceManager azureResourceManager,
            String appName,
            Region region,
            String resourceGroupName
    ) {
        final String appUrl = appName + SUFFIX;

        System.out.println("Creating web app " + appName + " with master branch...");

        WebApp app = azureResourceManager.webApps()
                .define(appName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .withNewWindowsPlan(PricingTier.STANDARD_S1)
                .withJavaVersion(JavaVersion.JAVA_8_NEWEST)
                .withWebContainer(WebContainer.TOMCAT_8_0_NEWEST)
                .defineSourceControl()
                .withPublicGitRepository("https://github.com/jianghaolu/azure-site-test.git")
                .withBranch("master")
                .attach()
                .create();

        System.out.println("Created web app " + app.name());
        Utils.print(app);

        System.out.println("CURLing " + appUrl + "...");
        System.out.println(Utils.sendGetRequest("http://" + appUrl));
        return app;
    }
}
