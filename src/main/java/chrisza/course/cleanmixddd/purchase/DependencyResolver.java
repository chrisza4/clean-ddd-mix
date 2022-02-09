package chrisza.course.cleanmixddd.purchase;

import chrisza.course.cleanmixddd.purchase.domain.PurchaseRequestService;
import chrisza.course.cleanmixddd.purchase.persistance.PurchaseRequestRepositoryImpl;
import chrisza.course.cleanmixddd.purchase.persistance.UserRepositoryImpl;

public class DependencyResolver {
    public static PurchaseRequestService getService() {
        return new PurchaseRequestService(new PurchaseRequestRepositoryImpl(), new UserRepositoryImpl());
    }
}
