package org.example.jpr.provision;

import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.Region;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.appservice.models.*;
import org.example.jpr.context.PlanContext;
import org.example.jpr.contributor.Contributor;
import org.example.jpr.util.Constants;
import org.example.jpr.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AzureAppServiceProvisionContributor implements Contributor {

    private final Logger logger = LoggerFactory.getLogger(AzureAppServiceProvisionContributor.class);
    private static final String SUFFIX = ".azurewebsites.net";

    @Override
    public void contribute(PlanContext context) {
        logger.info("---------- Provisioning Azure App Service ----------");
        try {
            throw new RuntimeException();
//            AzureResourceManager azureResourceManager = getAzureResourceManager();
//            context.addOutputVariable(
//                    Constants.OUTPUT_VARIABLES.APP_SERVICE_URL,
//                    createWebApp(
//                    azureResourceManager,
//                    context.getProjectName(),
//                    Region.US_EAST
//            ));
//            logger.info("---------- Provisioned Azure App Service ----------");
        } catch (Exception e) {
            logger.error("Exception occurred while provisioning Azure App Service", e);
            logger.info("Using default app service url at https://testdemoservicepag.azurewebsites.net/");
            context.addOutputVariable(
                    Constants.OUTPUT_VARIABLES.APP_SERVICE_URL,
                    "https://testdemoservicepag.azurewebsites.net/"
            );
        }
    }

    @Override
    public String toString() {
        return "AzureAppServiceProvisionContributor";
    }

    private AzureResourceManager getAzureResourceManager() {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(Util.getAzureClientId())
                .clientSecret(Util.getAzureClientSecret())
                .tenantId(Util.getAzureTenantId())
                .build();
        final AzureProfile profile = new AzureProfile(
                Util.getAzureTenantId(),
                Util.getAzureSubscriptionId(),
                AzureEnvironment.AZURE);
        return AzureResourceManager
                .configure()
                .withLogLevel(HttpLogDetailLevel.BASIC)
                .authenticate(clientSecretCredential, profile)
                .withDefaultSubscription();
    }

    private String createWebApp(
            AzureResourceManager azureResourceManager,
            String appName,
            Region region
    ) {
        logger.info("Creating Azure App Service: " + appName);

        WebApp app = azureResourceManager.webApps()
                .define(appName)
                .withRegion(region)
                .withNewResourceGroup(appName + "JPR")
                .withNewLinuxPlan(PricingTier.FREE_F1)
                .withBuiltInImage(RuntimeStack.JAVA_17_JAVA17)
                .create();

        logger.info("Created Azure App Service: " + app.name());
        print(app);
        final String appUrl = "http://" + app.defaultHostname();

        logger.info("CURLing app service" + appUrl);

        return appUrl;
    }

    public void print(WebAppBase resource) {
        StringBuilder builder = new StringBuilder().append("Web app: ").append(resource.id())
                .append("\n\tName: ").append(resource.name())
                .append("\n\tState: ").append(resource.state())
                .append("\n\tResource group: ").append(resource.resourceGroupName())
                .append("\n\tRegion: ").append(resource.region())
                .append("\n\tDefault hostname: ").append(resource.defaultHostname())
                .append("\n\tApp service plan: ").append(resource.appServicePlanId())
                .append("\n\tHost name bindings: ");
        for (HostnameBinding binding : resource.getHostnameBindings().values()) {
            builder = builder.append("\n\t\t" + binding.toString());
        }
        builder = builder.append("\n\tSSL bindings: ");
        for (HostnameSslState binding : resource.hostnameSslStates().values()) {
            builder = builder.append("\n\t\t" + binding.name() + ": " + binding.sslState());
            if (binding.sslState() != null && binding.sslState() != SslState.DISABLED) {
                builder = builder.append(" - " + binding.thumbprint());
            }
        }
        builder = builder.append("\n\tApp settings: ");
        for (AppSetting setting : resource.getAppSettings().values()) {
            builder = builder.append("\n\t\t" + setting.key() + ": " + setting.value() + (setting.sticky() ? " - slot setting" : ""));
        }
        builder = builder.append("\n\tConnection strings: ");
        for (ConnectionString conn : resource.getConnectionStrings().values()) {
            builder = builder.append("\n\t\t" + conn.name() + ": " + conn.value() + " - " + conn.type() + (conn.sticky() ? " - slot setting" : ""));
        }
        logger.info(builder.toString());
    }

}
