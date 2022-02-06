package chrisza.course.cleanmixddd.purchase.domain;

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

    public PurchaseRequest Approve(int id) {
        throw new UnsupportedOperationException();
    }
}
