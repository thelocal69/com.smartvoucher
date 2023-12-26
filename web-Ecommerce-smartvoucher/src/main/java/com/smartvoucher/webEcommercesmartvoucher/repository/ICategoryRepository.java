package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByCategoryCode(String categoryCode);
    List<CategoryEntity> searchAllByNameContainingIgnoreCase(String name);
    CategoryEntity findOneByCategoryCode(String categoryCode);
    CategoryEntity findOneById(Long id);
    Boolean existsByCategoryCode(String categoryCode);
}
