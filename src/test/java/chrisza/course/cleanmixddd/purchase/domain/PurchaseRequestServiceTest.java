package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class PurchaseRequestServiceTest {

    @Test
    public void ShouldSaveNewPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var expectedNewPurchaseRequestId = 1;
        var approver = new User(1, "Chris", PermissionLevel.CEO);
        var requester = new User(2, "Erikk", PermissionLevel.Employee);
        var purchaseRequest = new PurchaseRequest(approver, new ArrayList<>(), requester);
        var addedPurchaseRequest = new PurchaseRequest(expectedNewPurchaseRequestId, approver,
                new ArrayList<>(), requester);
        when(mockRepo.Add(purchaseRequest)).thenReturn(addedPurchaseRequest);

        var service = new PurchaseRequestService(mockRepo);
        var newPurchaseRequest = service.AddNewPurchaseRequest(purchaseRequest);
        assertTrue(newPurchaseRequest.getId().isPresent());
        assertEquals(expectedNewPurchaseRequestId, newPurchaseRequest.getId().getAsInt());

        verify(mockRepo).Add(purchaseRequest);
    }

    @Test
    public void ShouldNotSaveInvalidPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequest = Fixtures.getNoApproverPurchaseRequest();
        when(mockRepo.Add(purchaseRequest)).thenThrow(new AssertionError("Should not be called"));

        var service = new PurchaseRequestService(mockRepo);
        var result = service.AddNewPurchaseRequest(purchaseRequest);
        assertNull(result);
        verify(mockRepo, never()).Add(purchaseRequest);
    }

    @Test
    public void ShouldBeAbleToApprovePurchaseRequestById() throws NotFoundException, UnapprovableException {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        var purchaseRequest = Fixtures.getNormalPurchaseRequest();
        when(mockRepo.getById(purchaseRequestId)).thenReturn(purchaseRequest);
        when(mockRepo.edit(purchaseRequestId, purchaseRequest)).thenReturn(purchaseRequest);

        var service = new PurchaseRequestService(mockRepo);
        var result = service.Approve(purchaseRequestId);
        assertEquals(purchaseRequest, result);
        assertEquals(PurchaseRequestStatus.Approved, result.getStatus());
        verify(mockRepo).edit(purchaseRequestId, purchaseRequest);
    }

    @Test
    public void ShouldThrowAndNotSaveIfUnappovable() throws NotFoundException {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        var purchaseRequest = Fixtures.getUnapproveablePurchaseRequest();
        when(mockRepo.getById(purchaseRequestId)).thenReturn(purchaseRequest);
        when(mockRepo.edit(purchaseRequestId, purchaseRequest)).thenReturn(purchaseRequest);

        var service = new PurchaseRequestService(mockRepo);
        assertThrows(UnapprovableException.class, () -> service.Approve(purchaseRequestId));
        verify(mockRepo, never()).edit(purchaseRequestId, purchaseRequest);
    }

    @Test
    public void ShouldThrowNotFoundIfIdDoesnotExists() {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        when(mockRepo.getById(purchaseRequestId)).thenThrow(new IndexOutOfBoundsException());

        var service = new PurchaseRequestService(mockRepo);
        assertThrows(NotFoundException.class, () -> service.Approve(purchaseRequestId));

    }

    @Test
    public void ShouldBeAbleToRequestPrById() throws NotFoundException {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        var purchaseRequest = Fixtures.getUnapproveablePurchaseRequest();
        when(mockRepo.getById(purchaseRequestId)).thenReturn(purchaseRequest);

        var service = new PurchaseRequestService(mockRepo);
        assertEquals(purchaseRequest, service.getById(purchaseRequestId));

    }

    @Test
    public void ShouldThrowNotFoundExceptionIfCannotGetPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        when(mockRepo.getById(purchaseRequestId)).thenThrow(new IndexOutOfBoundsException());

        var service = new PurchaseRequestService(mockRepo);
        assertThrows(NotFoundException.class, () -> service.getById(purchaseRequestId));

    }
}
