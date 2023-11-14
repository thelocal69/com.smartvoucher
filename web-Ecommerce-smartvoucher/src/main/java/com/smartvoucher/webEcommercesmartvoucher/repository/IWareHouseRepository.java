package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWareHouseRepository extends JpaRepository<WareHouseEntity, Long> {
    List<WareHouseEntity> findAllByWarehouseCode(String wareHouseCode);
    WareHouseEntity findOneById(Long id);
    WareHouseEntity findOneByWarehouseCode(String warehouseCode);
    @Query(value="SELECT * from warehouse WHERE warehouse.id_label= :id and warehouse.status = 1", nativeQuery = true)
    List<WareHouseEntity> findAllByLabel (@Param("id") int id);

}
