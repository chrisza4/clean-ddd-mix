package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequest;
import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequestLine;
import chrisza.course.cleanmixddd.purchase.domain.entities.User;
import chrisza.course.cleanmixddd.purchase.domain.exceptions.UnapprovableException;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PermissionLevel;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PurchaseRequestStatus;
import org.junit.jupiter.api.Test;

public class PurchaseRequestTest {
    @Test
    void testIsValidReturnTrueForValidPurchaseRequest() {
        var purchaseRequest = Fixtures.getNormalPurchaseRequest();

        assertTrue(purchaseRequest.validate().isValid());
    }

    @Test
    void testIsValidReturnInvalidWhenEmployeeIsApprover() {
        var product1 = Fixtures.getMacbook();
        var product2 = Fixtures.getAirPods();
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
        var product1 = Fixtures.getMacbook();
        var product2 = Fixtures.getAirPods();
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(1, "Erikk", PermissionLevel.Manager);
        var requester = new User(2, "Chris", PermissionLevel.Manager);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, requester);

        assertFalse(purchaseRequest.validate().isValid());
    }

    @Test
    void testIsValidReturnInvalidWhenNoApprover() {
        var product1 = Fixtures.getMacbook();
        var product2 = Fixtures.getAirPods();
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var requester = new User(2, "Chris", PermissionLevel.Manager);

        var purchaseRequest = new PurchaseRequest(null, purchaseRequestItems, requester);
        assertFalse(purchaseRequest.validate().isValid());
        assertEquals("Purchase request must have an approver", purchaseRequest.validate().getErrorMessages().get(0));
    }

    @Test
    void testIsValidReturnInvalidWhenNoOwner() {
        var product1 = Fixtures.getMacbook();
        var product2 = Fixtures.getAirPods();
        var item1 = new PurchaseRequestLine(product1, 3);
        var item2 = new PurchaseRequestLine(product2, 4);
        var purchaseRequestItems = List.of(item1, item2);
        var approver = new User(2, "Chris", PermissionLevel.Manager);

        var purchaseRequest = new PurchaseRequest(approver, purchaseRequestItems, null);
        assertFalse(purchaseRequest.validate().isValid());
        assertEquals("Purchase request must have an owner", purchaseRequest.validate().getErrorMessages().get(0));
    }

    @Test
    void testCanApprove() {
        var purchaseRequest = Fixtures.getNormalPurchaseRequest();
        try {
            purchaseRequest.Approve();
            assertEquals(PurchaseRequestStatus.Approved, purchaseRequest.getStatus());
        } catch (UnapprovableException e) {
            throw new AssertionError("Should approved");
        }
    }

    @Test
    void testCannotApproveIfAmountExceeds100000AndNotCEOAsApprover() {
        var purchaseRequest = Fixtures.getUnapproveablePurchaseRequest();
        assertThrows(UnapprovableException.class, () -> purchaseRequest.Approve());
    }
}
