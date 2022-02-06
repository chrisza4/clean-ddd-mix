package chrisza.course.cleanmixddd.purchase.domain;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepository;

public class PurchaseRequestService {
    private PurchaseRequestRepository repository;

    public PurchaseRequestService(PurchaseRequestRepository repository) {
        this.repository = repository;
    }

    public PurchaseRequest AddNewPurchaseRequest(PurchaseRequest purchaseRequest) {
        if (!purchaseRequest.validate().isValid()) {
            return null;
        }
        return this.repository.Add(purchaseRequest);
    }

    public PurchaseRequest Approve(int id) throws UnapprovableException, NotFoundException {
        try {
            var purchaseRequest = repository.getById(id);
            purchaseRequest.Approve();
            return repository.edit(id, purchaseRequest);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException();
        }

    }
}
