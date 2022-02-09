package chrisza.course.cleanmixddd.purchase.domain.entities;

public class Product {
    private final int id;
    private final String description;
    private long price;

    public Product(int id, String description, long price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
