package site.metacoding.finals.domain.subscribe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    public List<Subscribe> findByCustomerId(Long customerId);

    public void deleteByCustomerId(Long customerId);
}