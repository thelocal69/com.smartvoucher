package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.DiscountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiscountTypeRepository extends JpaRepository<DiscountTypeEntity, Long> {
    List<DiscountTypeEntity> findByCode(String code);
    DiscountTypeEntity findOneByCode(String code);
    DiscountTypeEntity findOneById(Long id);
    Boolean existsByCode(String code);
    List<DiscountTypeEntity> findAllByStatus(int status);
}
