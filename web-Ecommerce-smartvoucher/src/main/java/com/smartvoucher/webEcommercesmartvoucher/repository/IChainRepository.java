package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChainRepository extends JpaRepository<ChainEntity, Long> {
    List<ChainEntity> findAllByChainCode(String chainCode);
    ChainEntity findOneByChainCode(String chainCode);
    ChainEntity findOneById(Long id);
    Boolean existsByChainCode(String chainCode);
}
