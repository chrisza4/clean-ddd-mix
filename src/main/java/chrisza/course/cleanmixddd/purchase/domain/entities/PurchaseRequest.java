package chrisza.course.cleanmixddd.purchase.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PermissionLevel;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PurchaseRequestStatus;
import chrisza.course.cleanmixddd.purchase.domain.exceptions.UnapprovableException;
import chrisza.course.cleanmixddd.purchase.domain.helpers.*;

public class PurchaseRequest {
    private OptionalInt id;
    private final User approver;
    private final User owner;
    private final List<PurchaseRequestLine> items;
    private PurchaseRequestStatus status;

    public PurchaseRequest(User approver, List<PurchaseRequestLine> items, User owner) {
        this.id = OptionalInt.empty();
        this.items = items;
        this.approver = approver;
        this.owner = owner;
    }

    public PurchaseRequestStatus getStatus() {
        return status;
    }

    public PurchaseRequest(int id, User approver, List<PurchaseRequestLine> items, User owner) {
        this(approver, items, owner);
        this.id = OptionalInt.of(id);
    }

    public OptionalInt getId() {
        return id;
    }

    public void setId(int id) {
        this.id = OptionalInt.of(id);
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
        if (approver == null) {
            errors.add("Purchase request must have an approver");
        }
        if (owner == null) {
            errors.add("Purchase request must have an owner");
        }
        if (approver != null && owner != null) {
            if (approver.getLevel() == PermissionLevel.Employee) {
                errors.add("Normal employee can't be an approver of a purchase request");
            }
            if (owner.getLevel() == PermissionLevel.Manager && approver.getLevel() != PermissionLevel.CEO) {
                errors.add("A manager must ask for approval from CEO only");
            }
        }
        return new ValidationResult(errors);
    }

    public void Approve() throws UnapprovableException {
        var totalAmount = 0;
        for (var item : this.items) {
            totalAmount += item.getQuantity() * item.getProduct().getPrice();
        }
        if (totalAmount > 100000 && approver.getLevel() != PermissionLevel.CEO) {
            throw new UnapprovableException();
        }
        this.status = PurchaseRequestStatus.Approved;
    }
}
