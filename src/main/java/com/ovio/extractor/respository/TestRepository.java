package com.ovio.extractor.respository;

import com.ovio.extractor.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    
}
