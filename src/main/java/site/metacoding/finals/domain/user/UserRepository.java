package site.metacoding.finals.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import site.metacoding.finals.config.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    // 반환타입 메서드명 (매개변수)

    @EntityGraph(attributePaths = { "customer", "shop" })
    public User findByUsername(String username);

    @Query("select u from User u where role=?1")
    User findbyTestuser(Role role);

}
