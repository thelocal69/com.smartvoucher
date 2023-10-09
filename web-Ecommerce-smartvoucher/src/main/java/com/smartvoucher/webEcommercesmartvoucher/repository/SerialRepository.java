package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialRepository extends JpaRepository<SerialEntity, Long> {


    @Query("SELECT s FROM serial s WHERE s.serialCode = ?1")
    SerialEntity findSerialBySerialCode(String serialCode);
}
