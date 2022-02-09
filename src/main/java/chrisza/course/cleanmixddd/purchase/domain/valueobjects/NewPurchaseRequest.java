package chrisza.course.cleanmixddd.purchase.domain.valueobjects;


import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequestLine;

import java.util.ArrayList;

public class NewPurchaseRequest {

    private final int approverId;
    private final int ownerId;
    private final ArrayList<PurchaseRequestLine> items;

    public NewPurchaseRequest() {
        this.approverId = 0;
        this.ownerId = 0;
        this.items = null;
    }

    public NewPurchaseRequest(int approverId, ArrayList<PurchaseRequestLine> items, int ownerId) {
        this.items = items;
        this.approverId = approverId;
        this.ownerId = ownerId;
    }

    /**
     * @return ArrayList<PurchaseRequestLine> return the items
     */
    public ArrayList<PurchaseRequestLine> getItems() {
        return items;
    }

    public int getApproverId() {
        return approverId;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
