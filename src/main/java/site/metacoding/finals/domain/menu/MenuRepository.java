package site.metacoding.finals.domain.menu;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @EntityGraph(attributePaths = "imageFile")
    List<Menu> findByShopId(Long shopId);
}
