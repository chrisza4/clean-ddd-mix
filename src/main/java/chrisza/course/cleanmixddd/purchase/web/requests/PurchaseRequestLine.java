package chrisza.course.cleanmixddd.purchase.web.requests;

public class PurchaseRequestLine {
    private Product product;
    private int quantity;

    public PurchaseRequestLine() {

    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
