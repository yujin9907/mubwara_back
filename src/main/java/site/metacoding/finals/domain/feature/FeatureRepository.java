package site.metacoding.finals.domain.feature;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByShopId(Long shopId);
}
