package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDb extends JpaRepository<RoleModel, Long> {
}
