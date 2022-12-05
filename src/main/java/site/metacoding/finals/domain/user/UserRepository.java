package site.metacoding.finals.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 반환타입 메서드명 (매개변수)

    // findBy규칙 : 문법
    public Optional<User> findByUsername(String username);

}
