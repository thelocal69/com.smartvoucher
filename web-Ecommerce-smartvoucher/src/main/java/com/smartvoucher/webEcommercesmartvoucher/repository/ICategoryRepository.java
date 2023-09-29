package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByCategoryCode(String categoryCode);
    CategoryEntity findOneByCategoryCode(String categoryCode);
    CategoryEntity findOneById(Long id);
    Boolean existsByCategoryCode(String categoryCode);
}
