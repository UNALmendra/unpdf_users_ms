package users;
import org.springframework.data.jpa.repository.JpaRepository;

interface UsersRepository extends JpaRepository<User, Int>{
    
}