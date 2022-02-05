package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PurchaseRequestTest {
    @Test
    void testIsValidReturnTrueForValidPurchaseRequest() {
        var product1 = new Product(1, "Macbook");
        var product2 = new Product(2, "AirPods");
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(1, "Chris", PermissionLevel.CEO);
        var requester = new User(2, "Erikk", PermissionLevel.Employee);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, requester);

        assertTrue(purchaseRequest.validate().isValid());
    }

    @Test
    void testIsValidReturnInvalidWhenEmployeeIsApprover() {
        var product1 = new Product(1, "Macbook");
        var product2 = new Product(2, "AirPods");
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(1, "Erikk", PermissionLevel.Employee);
        var requester = new User(2, "Chris", PermissionLevel.CEO);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, requester);

        assertFalse(purchaseRequest.validate().isValid());
    }

    @Test
    void testIsValidReturnInvalidWhenManagerAskForManagerApproval() {
        var product1 = new Product(1, "Macbook");
        var product2 = new Product(2, "AirPods");
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(1, "Erikk", PermissionLevel.Manager);
        var requester = new User(2, "Chris", PermissionLevel.Manager);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, requester);

        assertFalse(purchaseRequest.validate().isValid());
    }
}
