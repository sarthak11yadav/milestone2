package net.milestone2.DAO;
import net.milestone2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByMobileno(String Mobileno);
    User findByEmail(String email);
    User findByUsername(String username);

}
