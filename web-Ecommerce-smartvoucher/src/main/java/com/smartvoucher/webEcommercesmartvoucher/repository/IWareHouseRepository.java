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
    List<WareHouseEntity> findAllByStatus(int status);
    @Query(value="SELECT * from warehouse WHERE warehouse.id_label= :id and warehouse.status = 1", nativeQuery = true)
    List<WareHouseEntity> findAllByLabel (@Param("id") int id);

    @Query("SELECT w FROM warehouse w JOIN category c ON w.category.id=c.id" +
            " WHERE w.category.id = :id")
    List<WareHouseEntity>findAllByCategoryId(long id);
}
