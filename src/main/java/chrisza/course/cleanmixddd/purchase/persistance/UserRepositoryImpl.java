package chrisza.course.cleanmixddd.purchase.persistance;

import chrisza.course.cleanmixddd.purchase.domain.dependencies.UserRepository;
import chrisza.course.cleanmixddd.purchase.domain.entities.User;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.PermissionLevel;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getById(int id) {
        return allUsers.get(id);
    }

    public UserRepositoryImpl() {
        this.allUsers = new ArrayList<>();
        this.allUsers.add(new User(0, "Chris", PermissionLevel.Manager));
        this.allUsers.add(new User(1, "Erikk", PermissionLevel.Employee));
        this.allUsers.add(new User(2, "Jobs", PermissionLevel.CEO));
    }

    private List<User> allUsers;
}
