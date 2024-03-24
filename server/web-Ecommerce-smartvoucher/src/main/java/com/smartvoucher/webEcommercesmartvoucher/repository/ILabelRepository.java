package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILabelRepository extends JpaRepository<LabelEntity, Long> {
    LabelEntity findOneByName(String name);
    Boolean existsByName(String name);
    @Query("SELECT la.name FROM label la")
    List<String> getAllByLabelName();
}
