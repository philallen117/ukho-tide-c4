import java.rmi.server.RemoteStub;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.*;

public class UKHOPortal {

    private static final long WORKSPACE_ID = 47220;
    private static final String API_KEY = "a4ed7081-277b-48f3-bf6d-ebe5d0bec256";
    private static final String API_SECRET = "d208cc57-8361-40ca-8d41-53c9fef0ec87";
    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }
    public static void main(String[] args) throws Exception {
        Workspace workspace = new Workspace("UKHO API Portal", "Solution overview for data Tides product.");
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        // Context

        Person subscriber = model.addPerson("Subscriber", "Pays for data access");
        SoftwareSystem portal = model.addSoftwareSystem("UKHO Developer Portal", "Branded Store,\nAPI Gatewahy");
        SoftwareSystem clientApp = model.addSoftwareSystem("Client App", "Using tides product");
        SoftwareSystem psp = model.addSoftwareSystem("Stripe", "Payment Service Provider");
        Person backOfficeUser = model.addPerson("Back Office", "Handles accounts\nand after-sales");
        subscriber.uses(portal, "Subscribes to product");
        portal.uses(psp, "Delegates card transaction");
        subscriber.uses(psp, "Pays for product");
        clientApp.uses(portal, "Calls");
        backOfficeUser.uses(portal, "Get renewals\nCancel subscriptions");
        backOfficeUser.uses(psp, "Get transactions, settlements");

        SystemContextView contextView = views.createSystemContextView(portal, "System Context", "UKHO Developer Portal");
        contextView.setPaperSize(PaperSize.A4_Landscape);
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        // Containers

        Container apim = portal.addContainer("APIM", "Configured to enforce\nproduct rate limits", "Azure PaaS");
        Container wrapper = portal.addContainer("Tides Wrapper", "Enforces business model", "Azure Function");
        Container backEnd = portal.addContainer("Tides Back End", "Serves calls", "Azure Scale Set");
        clientApp.uses(apim, "Calls\nwith subscription keys");
        apim.uses(wrapper, "Calls\nwith subscription details");

        wrapper.uses(backEnd, "Calls\n[if valid request for subscription]");
        Container reminderPage = portal.addContainer("Reminders Page", "Lists customers with expiring subscriptions", "Azure Function");
        Container cancelPage = portal.addContainer("Cancel Page", "Cancels an active subscription", "Azure Function");
        reminderPage.uses(apim, "uses API");
        cancelPage.uses(apim, "uses API");
        backOfficeUser.uses(reminderPage, "Gets subscriber e-mails");
        backOfficeUser.uses(cancelPage, "Cancels,\ne.g. after chargeback");

        ContainerView frontOfficeCV = views.createContainerView(portal, "Container", "UKHO Developer Portal");
        frontOfficeCV.setPaperSize(PaperSize.A4_Landscape);
        frontOfficeCV.addAllContainers();
        frontOfficeCV.add(clientApp);
        frontOfficeCV.add(backOfficeUser);
    
        // ContainerView backOfficeCV = views.createContainerView(portal, "Back Office", "UKHO Developer Portal")
        // backOfficeCV.setPaperSize(PaperSize.A4_Landscape);
        // backOfficeCV.add(backOfficeUser);
        // backOfficeCV.add(cancelPage);

        // Upload

        uploadWorkspaceToStructurizr(workspace);
    }
}