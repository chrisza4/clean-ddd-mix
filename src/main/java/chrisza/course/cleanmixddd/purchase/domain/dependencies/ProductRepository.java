package chrisza.course.cleanmixddd.purchase.domain.dependencies;

import chrisza.course.cleanmixddd.purchase.domain.entities.Product;
import chrisza.course.cleanmixddd.purchase.domain.entities.User;

public interface ProductRepository {
    Product getById(int id);
}
