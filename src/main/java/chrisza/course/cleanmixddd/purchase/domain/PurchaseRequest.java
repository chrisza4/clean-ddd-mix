package chrisza.course.cleanmixddd.purchase.domain;

import java.util.ArrayList;

public class PurchaseRequest {
    private final User approver;
    private final User owner;
    private final ArrayList<PurchaseRequestLine> items;

    public PurchaseRequest(User approver, ArrayList<PurchaseRequestLine> items, User owner) {
        this.items = items;
        this.approver = approver;
        this.owner = owner;
    }

    public User getApprover() {
        return approver;
    }

    public User getOwner() {
        return owner;
    }

    /**
     * @return ArrayList<PurchaseRequestLine> return the items
     */
    public ArrayList<PurchaseRequestLine> getItems() {
        return items;
    }
}
