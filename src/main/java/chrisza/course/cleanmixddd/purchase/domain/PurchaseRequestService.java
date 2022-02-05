package chrisza.course.cleanmixddd.purchase.domain;

import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepository;

public class PurchaseRequestService {
    private PurchaseRequestRepository repository;

    public PurchaseRequestService(PurchaseRequestRepository repository) {
        this.repository = repository;
    }

    public int AddNewPurchaseRequest(PurchaseRequest purchaseRequest) {
        return this.repository.AddAndGetId(purchaseRequest);
    }
}
