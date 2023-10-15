package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.OrdersEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    Optional<OrdersEntity> findByOrderNo(String orderNo);

    @Query("SELECT o FROM orders o WHERE o.orderNo = ?1 AND o.id != ?2")
    List<OrdersEntity> findByOrderNoAndId(String orderNo, long id);
}
