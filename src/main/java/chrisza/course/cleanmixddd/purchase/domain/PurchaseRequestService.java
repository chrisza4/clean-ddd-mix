package chrisza.course.cleanmixddd.purchase.domain;

import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepositoryImpl;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class PurchaseRequestService {
    private PurchaseRequestRepositoryImpl repository;

    public PurchaseRequestService(PurchaseRequestRepositoryImpl repository) {
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

    public PurchaseRequest getById(int id) throws NotFoundException {
        try {
            return repository.getById(id);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException();
        }
    }
}
