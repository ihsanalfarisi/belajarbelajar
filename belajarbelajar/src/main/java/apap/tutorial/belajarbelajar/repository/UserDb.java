package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDb extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
    UserModel findById(String id);
}
