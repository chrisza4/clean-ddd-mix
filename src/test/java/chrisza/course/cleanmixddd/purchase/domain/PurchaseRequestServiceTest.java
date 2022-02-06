package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepository;

public class PurchaseRequestServiceTest {

    @Test
    public void ShouldSaveNewPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepository.class);
        var expectedNewPurchaseRequestId = 1;
        var approver = new User(1, "Chris", PermissionLevel.CEO);
        var requester = new User(2, "Erikk", PermissionLevel.Employee);
        var purchaseRequest = new PurchaseRequest(approver, new ArrayList<PurchaseRequestLine>(), requester);
        var addedPurchaseRequest = new PurchaseRequest(expectedNewPurchaseRequestId, approver,
                new ArrayList<PurchaseRequestLine>(), requester);
        when(mockRepo.Add(purchaseRequest)).thenReturn(addedPurchaseRequest);

        var service = new PurchaseRequestService(mockRepo);
        var newPurchaseRequest = service.AddNewPurchaseRequest(purchaseRequest);
        assertEquals(expectedNewPurchaseRequestId, newPurchaseRequest.getId().getAsInt());

        verify(mockRepo).Add(purchaseRequest);
    }

    @Test
    public void ShouldNotSaveInvalidPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepository.class);
        var purchaseRequest = Fixtures.getNoApproverPurchaseRequest();
        when(mockRepo.Add(purchaseRequest)).thenThrow(new AssertionError("Should not be called"));

        var service = new PurchaseRequestService(mockRepo);
        var result = service.AddNewPurchaseRequest(purchaseRequest);
        assertNull(result);
        verify(mockRepo, never()).Add(purchaseRequest);
    }
}
