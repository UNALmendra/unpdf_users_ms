package unpdf.users_ms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unpdf.users_ms.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}