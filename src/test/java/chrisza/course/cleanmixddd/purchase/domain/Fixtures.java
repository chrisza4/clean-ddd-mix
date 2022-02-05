package chrisza.course.cleanmixddd.purchase.domain;

import java.util.List;

public class Fixtures {
    public static PurchaseRequest getNoApproverPurchaseRequest() {
        var product1 = new Product(1, "Macbook");
        var product2 = new Product(2, "AirPods");
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var requester = new User(2, "Chris", PermissionLevel.CEO);
        return new PurchaseRequest(null, purchaseRequestItems, requester);
    }
}
