package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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
}
