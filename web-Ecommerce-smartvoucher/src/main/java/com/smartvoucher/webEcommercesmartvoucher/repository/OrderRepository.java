package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByOrderNo(String orderNo);

    @Query("SELECT o FROM orders o WHERE o.orderNo = ?1 AND o.id != ?2")
    Optional<OrderEntity> findByOrderNoAndId(String orderNo, long id);
}
