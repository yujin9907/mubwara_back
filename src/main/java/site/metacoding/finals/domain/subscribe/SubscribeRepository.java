package site.metacoding.finals.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    public Subscribe findByCustomerId(Long customerId);

    public void deleteByCustomerId(Long customerId);
}