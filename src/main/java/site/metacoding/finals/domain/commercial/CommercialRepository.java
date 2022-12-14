package site.metacoding.finals.domain.commercial;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialRepository extends JpaRepository<Commercial, Long> {

    @EntityGraph(attributePaths = { "shop", "imageFile" })
    List<Commercial> findAll();

}
