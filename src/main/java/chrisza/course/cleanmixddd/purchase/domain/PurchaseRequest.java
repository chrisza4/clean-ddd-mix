package chrisza.course.cleanmixddd.purchase.domain;

import java.util.ArrayList;
import java.util.List;

import chrisza.course.cleanmixddd.purchase.domain.helpers.*;

public class PurchaseRequest {
    private final User approver;
    private final User owner;
    private final List<PurchaseRequestLine> items;

    public PurchaseRequest(User approver, List<PurchaseRequestLine> items, User owner) {
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
     * @return List<PurchaseRequestLine> return the items
     */
    public List<PurchaseRequestLine> getItems() {
        return items;
    }

    public ValidationResult validate() {
        var errors = new ArrayList<String>();
        if (approver.getLevel() == PermissionLevel.Employee) {
            errors.add("Normal employee can't be an approver of a purchase request");
        }
        if (owner.getLevel() == PermissionLevel.Manager && approver.getLevel() != PermissionLevel.CEO) {
            errors.add("A manager must ask for approval from CEO only");
        }
        return new ValidationResult(errors);
    }
}
