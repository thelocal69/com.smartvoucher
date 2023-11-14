package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerialRepository extends JpaRepository<SerialEntity, Long> {
    SerialEntity findBySerialCode(String serialCode);
    SerialEntity findBySerialCodeAndId(String serialCode, long id);
}
