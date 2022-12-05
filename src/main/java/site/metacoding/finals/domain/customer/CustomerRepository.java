package site.metacoding.finals.domain.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(Long id);

    @Query("select c from Customer c join fetch Reservation r where c.id = ?1")
    Optional<Customer> findByReservationId(@Param("customerId") Long customerId);

}
