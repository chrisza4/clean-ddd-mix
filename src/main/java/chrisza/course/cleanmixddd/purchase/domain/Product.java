package chrisza.course.cleanmixddd.purchase.domain;

public class Product {
    private final int id;
    private final String description;

    public Product(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
