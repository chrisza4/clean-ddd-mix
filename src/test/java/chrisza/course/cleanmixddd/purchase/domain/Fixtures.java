package chrisza.course.cleanmixddd.purchase.domain;

import java.util.List;

public class Fixtures {
    public static PurchaseRequest getNoApproverPurchaseRequest() {
        var product1 = getMacbook();
        var product2 = getAirPods();
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var requester = new User(2, "Chris", PermissionLevel.CEO);
        return new PurchaseRequest(1, null, purchaseRequestItems, requester);
    }

    public static PurchaseRequest getNormalPurchaseRequest() {
        var product1 = getMacbook();
        var product2 = getAirPods();
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(1, "Chris", PermissionLevel.CEO);
        var requester = new User(2, "Erikk", PermissionLevel.Employee);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, requester);
        return purchaseRequest;
    }

    public static PurchaseRequest getUnapproveablePurchaseRequest() {
        var product1 = getMacbook();
        var product2 = getAirPods();
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(1, "Chris", PermissionLevel.Manager);
        var requester = new User(2, "Erikk", PermissionLevel.Employee);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, requester);
        return purchaseRequest;
    }

    public static Product getMacbook() {
        return new Product(1, "Macbook", 30000);
    }

    public static Product getAirPods() {
        return new Product(2, "AirPods", 10000);
    }
}
