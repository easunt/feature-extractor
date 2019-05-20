package com.ovio.extractor.respository;

import com.ovio.extractor.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
