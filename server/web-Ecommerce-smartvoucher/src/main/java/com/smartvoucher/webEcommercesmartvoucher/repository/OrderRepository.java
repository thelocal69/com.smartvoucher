package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findOneById(Long id);
    OrderEntity findByOrderNo(String orderNo);

    @Query("SELECT o FROM orders o WHERE o.orderNo = ?1 AND o.id != ?2")
    Optional<OrderEntity> findByOrderNoAndId(String orderNo, long id);
    @Query("SELECT o FROM orders o " +
            "JOIN users u ON o.idUser.id = u.id " +
            "WHERE u.id = ?1 ")
    List<OrderEntity> findAllOrderByIdUser(long id);

    @Query("SELECT o FROM orders o " +
            "JOIN users u ON o.idUser.id = u.id " +
            "WHERE u.id = ?1 ")
    OrderEntity findOneByIdUser(long id);

    List<OrderEntity> findAllByIdUser(UserEntity idUser, Pageable pageable);
    @Query("SELECT count(*) FROM orders o JOIN users u ON o.idUser.id = u.id" +
            " WHERE u.id=:id")
    int countOrderByIdUser(Long id);
}
