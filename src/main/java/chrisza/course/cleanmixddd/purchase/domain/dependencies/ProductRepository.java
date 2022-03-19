package chrisza.course.cleanmixddd.purchase.domain.dependencies;

import chrisza.course.cleanmixddd.purchase.domain.entities.Product;

public interface ProductRepository {
    Product getById(int id);
}
