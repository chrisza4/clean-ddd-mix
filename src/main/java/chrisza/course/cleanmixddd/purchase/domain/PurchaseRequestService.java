package chrisza.course.cleanmixddd.purchase.domain;

import chrisza.course.cleanmixddd.purchase.domain.dependencies.PurchaseRequestRepository;
import chrisza.course.cleanmixddd.purchase.domain.dependencies.UserRepository;
import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequest;
import chrisza.course.cleanmixddd.purchase.domain.exceptions.UnapprovableException;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.NewPurchaseRequest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class PurchaseRequestService {
    private PurchaseRequestRepository purchaseRequestRepository;
    private UserRepository userRepository;

    public PurchaseRequestService(PurchaseRequestRepository purchaseRequestRepository, UserRepository userRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.userRepository = userRepository;
    }

    public PurchaseRequest AddNewPurchaseRequest(NewPurchaseRequest newPurchaseRequest) {
        var approver = userRepository.getById(newPurchaseRequest.getApproverId());
        var owner = userRepository.getById(newPurchaseRequest.getOwnerId());
        var purchaseRequest = new PurchaseRequest(approver, newPurchaseRequest.getItems(), owner);
        if (!purchaseRequest.validate().isValid()) {
            return null;
        }

        return this.purchaseRequestRepository.Add(purchaseRequest);
    }

    public PurchaseRequest Approve(int id) throws UnapprovableException, NotFoundException {
        try {
            var purchaseRequest = purchaseRequestRepository.getById(id);
            purchaseRequest.Approve();
            return purchaseRequestRepository.edit(id, purchaseRequest);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException();
        }
    }

    public PurchaseRequest getById(int id) throws NotFoundException {
        try {
            return purchaseRequestRepository.getById(id);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException();
        }
    }
}
