package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.ChainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChainRepository extends JpaRepository<ChainEntity, Long> {
    List<ChainEntity> findAllByChainCode(String chainCode);
    ChainEntity findOneByChainCode(String chainCode);
    ChainEntity findOneByName(String name);
    List<ChainEntity> searchAllByNameContainingIgnoreCase(String name);
    ChainEntity findOneById(Long id);
    Boolean existsByChainCode(String chainCode);
    Boolean existsByName(String name);
    List<ChainEntity> findAllByStatus(int status);
    @Query("SELECT c.name FROM chains c")
    List<String> getChainName();
}
