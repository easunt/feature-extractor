package com.ovio.extractor.respository;

import com.ovio.extractor.entity.UrlFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlFeatureRepository extends JpaRepository<UrlFeature, Long> {
    Optional<UrlFeature> findById(Long id);
}
