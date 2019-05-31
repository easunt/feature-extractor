package com.ovio.extractor.respository;

import com.ovio.extractor.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findAllByIdGreaterThan(Long id);
}
