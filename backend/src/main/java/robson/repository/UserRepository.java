package robson.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import robson.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}