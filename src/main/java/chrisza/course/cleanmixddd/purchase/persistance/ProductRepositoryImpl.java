package chrisza.course.cleanmixddd.purchase.persistance;

import chrisza.course.cleanmixddd.purchase.domain.dependencies.ProductRepository;
import chrisza.course.cleanmixddd.purchase.domain.entities.Product;
import chrisza.course.cleanmixddd.purchase.domain.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Product getById(int id) {
        return this.allProducts.get(id);
    }

    private List<Product> allProducts;

    public ProductRepositoryImpl() {
        this.allProducts = new ArrayList<>();
        allProducts.add(new Product(0, "Macbook", 30000));
        allProducts.add(new Product(1, "Airpods", 2000));
    }
}
