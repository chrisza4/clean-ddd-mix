package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(mockRepo.AddAndGetId(purchaseRequest)).thenReturn(expectedNewPurchaseRequestId);

        var service = new PurchaseRequestService(mockRepo);
        var purchaseRequestId = service.AddNewPurchaseRequest(purchaseRequest);
        assertEquals(expectedNewPurchaseRequestId, purchaseRequestId);

        verify(mockRepo).AddAndGetId(purchaseRequest);
    }

    @Test
    public void ShouldNotSaveInvalidPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepository.class);
        var purchaseRequest = Fixtures.getNoApproverPurchaseRequest();
        when(mockRepo.AddAndGetId(purchaseRequest)).thenThrow(new AssertionError("Should not be called"));

        var service = new PurchaseRequestService(mockRepo);
        var result = service.AddNewPurchaseRequest(purchaseRequest);
        assertEquals(0, result);
        verify(mockRepo, never()).AddAndGetId(purchaseRequest);
    }
}
