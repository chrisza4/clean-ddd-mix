package chrisza.course.cleanmixddd.purchase.domain.dependencies;

import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface PurchaseRequestRepository {
    PurchaseRequest Add(PurchaseRequest purchaseRequest);
    PurchaseRequest getById(int id);
    PurchaseRequest edit(int id, PurchaseRequest newPurchaseRequest) throws ChangeSetPersister.NotFoundException;

}
