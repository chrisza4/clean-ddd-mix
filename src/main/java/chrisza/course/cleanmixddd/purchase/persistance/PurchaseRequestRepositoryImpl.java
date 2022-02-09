package chrisza.course.cleanmixddd.purchase.persistance;

import java.util.ArrayList;

import chrisza.course.cleanmixddd.purchase.domain.dependencies.PurchaseRequestRepository;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequest;

public class PurchaseRequestRepositoryImpl implements PurchaseRequestRepository {
    private static ArrayList<PurchaseRequest> purchaseRequests;

    public PurchaseRequest Add(PurchaseRequest purchaseRequest) {
        purchaseRequests.add(purchaseRequest);
        var id = purchaseRequests.size() - 1;
        purchaseRequest.setId(id);
        return purchaseRequest;
    }

    public PurchaseRequest getById(int id) {
        return purchaseRequests.get(id);
    }

    public PurchaseRequest edit(int id, PurchaseRequest newPurchaseRequest) throws NotFoundException {
        if (id >= purchaseRequests.size()) {
            throw new NotFoundException();
        }
        purchaseRequests.set(id, newPurchaseRequest);
        return newPurchaseRequest;
    }
}
