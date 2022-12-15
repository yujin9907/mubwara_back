package site.metacoding.finals.domain.subscribe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    public List<Subscribe> findByCustomerId(Long customerId);

    public void deleteByCustomerId(Long customerId);

    Subscribe findByCustomerIdAndShopId(@Param("customerId") Long customerId, @Param("shopId") Long shopId);
}