package br.com.restwithsptingbootandjava.repositories;

import br.com.restwithsptingbootandjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            "SELECT u FROM User u where u.userName = :userName"
    )
    User findByUsername(@Param("userName") String username);
}
