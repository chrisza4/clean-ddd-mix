package chrisza.course.cleanmixddd.purchase.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import chrisza.course.cleanmixddd.purchase.domain.dependencies.PurchaseRequestRepository;
import chrisza.course.cleanmixddd.purchase.domain.dependencies.UserRepository;
import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequest;
import chrisza.course.cleanmixddd.purchase.domain.entities.User;
import chrisza.course.cleanmixddd.purchase.domain.exceptions.UnapprovableException;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.NewPurchaseRequest;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PermissionLevel;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PurchaseRequestStatus;
import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class PurchaseRequestServiceTest {

    @Test
    public void ShouldSaveNewPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepository.class);
        var mockUserRepo = mock(UserRepository.class);
        var expectedNewPurchaseRequestId = 1;
        var approver = new User(1, "Chris", PermissionLevel.CEO);
        var requester = new User(2, "Erikk", PermissionLevel.Employee);
        when(mockUserRepo.getById(1)).thenReturn(approver);
        when(mockUserRepo.getById(2)).thenReturn(requester);
        var addedPurchaseRequest = new PurchaseRequest(expectedNewPurchaseRequestId, approver,
                new ArrayList<>(), requester);
        when(mockRepo.Add(any())).thenReturn(addedPurchaseRequest);

        var newPurchaseRequestInfo = new NewPurchaseRequest(1, new ArrayList<>(), 2);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
        var newPurchaseRequest = service.AddNewPurchaseRequest(newPurchaseRequestInfo);
        assertTrue(newPurchaseRequest.getId().isPresent());
        assertEquals(expectedNewPurchaseRequestId, newPurchaseRequest.getId().getAsInt());
        assertEquals(PurchaseRequestStatus.Ready, newPurchaseRequest.getStatus());

        verify(mockRepo).Add(any());
    }

    @Test
    public void ShouldNotSaveIfApproverDoesNotExists() {
        var mockRepo = mock(PurchaseRequestRepository.class);
        var mockUserRepo = mock(UserRepository.class);
        var expectedNewPurchaseRequestId = 1;
        var requester = new User(2, "Erikk", PermissionLevel.Employee);
        when(mockUserRepo.getById(1)).thenReturn(null);
        when(mockUserRepo.getById(2)).thenReturn(requester);

        var newPurchaseRequestInfo = new NewPurchaseRequest(1, new ArrayList<>(), 2);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
        var result = service.AddNewPurchaseRequest(newPurchaseRequestInfo);
        assertNull(result);
    }

    @Test
    public void ShouldBeAbleToApprovePurchaseRequestById() throws NotFoundException, UnapprovableException {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        var purchaseRequest = Fixtures.getNormalPurchaseRequest();
        when(mockRepo.getById(purchaseRequestId)).thenReturn(purchaseRequest);
        when(mockRepo.edit(purchaseRequestId, purchaseRequest)).thenReturn(purchaseRequest);

        var mockUserRepo = mock(UserRepository.class);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
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

        var mockUserRepo = mock(UserRepository.class);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
        assertThrows(UnapprovableException.class, () -> service.Approve(purchaseRequestId));
        verify(mockRepo, never()).edit(purchaseRequestId, purchaseRequest);
    }

    @Test
    public void ShouldThrowNotFoundIfIdDoesnotExists() {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        when(mockRepo.getById(purchaseRequestId)).thenThrow(new IndexOutOfBoundsException());

        var mockUserRepo = mock(UserRepository.class);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
        assertThrows(NotFoundException.class, () -> service.Approve(purchaseRequestId));

    }

    @Test
    public void ShouldBeAbleToRequestPrById() throws NotFoundException {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        var purchaseRequest = Fixtures.getUnapproveablePurchaseRequest();
        when(mockRepo.getById(purchaseRequestId)).thenReturn(purchaseRequest);

        var mockUserRepo = mock(UserRepository.class);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
        assertEquals(purchaseRequest, service.getById(purchaseRequestId));

    }

    @Test
    public void ShouldThrowNotFoundExceptionIfCannotGetPurchaseRequest() {
        var mockRepo = mock(PurchaseRequestRepositoryImpl.class);
        var purchaseRequestId = 1;
        when(mockRepo.getById(purchaseRequestId)).thenThrow(new IndexOutOfBoundsException());

        var mockUserRepo = mock(UserRepository.class);
        var service = new PurchaseRequestService(mockRepo, mockUserRepo);
        assertThrows(NotFoundException.class, () -> service.getById(purchaseRequestId));

    }
}
