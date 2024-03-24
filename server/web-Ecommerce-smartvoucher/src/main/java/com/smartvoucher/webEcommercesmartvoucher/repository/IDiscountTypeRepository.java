package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiscountTypeRepository extends JpaRepository<DiscountTypeEntity, Long> {
    List<DiscountTypeEntity> findByCode(String code);
    List<DiscountTypeEntity> searchAllByNameContainingIgnoreCase(String name);
    @Query("SELECT d.name FROM discount_type d")
    List<String> getAllByDiscountName();
    DiscountTypeEntity findOneByCode(String code);
    DiscountTypeEntity findOneById(Long id);
    Boolean existsByCode(String code);
    Boolean existsByName(String name);
    List<DiscountTypeEntity> findAllByStatus(int status);
    DiscountTypeEntity findOneByName(String name);
}
