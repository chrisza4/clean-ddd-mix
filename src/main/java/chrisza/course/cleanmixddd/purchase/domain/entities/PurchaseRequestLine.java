package chrisza.course.cleanmixddd.purchase.domain.entities;

public class PurchaseRequestLine {
    private Product product;
    private int quantity;

    public PurchaseRequestLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
