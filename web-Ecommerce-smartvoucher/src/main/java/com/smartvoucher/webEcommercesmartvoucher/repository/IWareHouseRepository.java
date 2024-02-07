package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import org.springframework.data.domain.Pageable;
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
    List<WareHouseEntity> searchAllByNameContainingIgnoreCase(String name);
    List<WareHouseEntity>findAllByCategoryName(String name, Pageable pageable);

//    @Query("SELECT w FROM warehouse w JOIN label la ON w.label.id = la.id" +
//            " WHERE w.label.id = :id AND w.status = 1" +
//            " ORDER BY ?#{#pageable}")
    List<WareHouseEntity>findAllByLabelSlug(String id, Pageable pageable);

    @Query("SELECT count(*) FROM warehouse w JOIN label la ON w.label.id=la.id" +
            " WHERE la.slug = :slug")
    int countByLabel(String slug);

    @Query("SELECT count(*) FROM warehouse w JOIN category c ON w.category.id=c.id" +
            " WHERE c.name = :name")
    int countByCategory(String name);
}
