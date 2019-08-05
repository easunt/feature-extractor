package com.ovio.extractor.respository;

import com.ovio.extractor.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findAllByIdGreaterThan(Long id);

    List<Url> findAllByIdIsBetween(Long start, Long end);

    Optional<Url> findById(Long id);
}
