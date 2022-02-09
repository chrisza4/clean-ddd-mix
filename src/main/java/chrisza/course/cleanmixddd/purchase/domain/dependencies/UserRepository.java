package chrisza.course.cleanmixddd.purchase.domain.dependencies;

import chrisza.course.cleanmixddd.purchase.domain.entities.User;

public interface UserRepository {
    User getById(int id);
}
