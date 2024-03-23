package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query("SELECT t FROM ticket t WHERE t.idSerial = ?1")
    Optional<TicketEntity> findByIdSerial(SerialEntity idSerial);
    @Query("SELECT t FROM ticket t " +
            "JOIN users u ON t.idUser.id = u.id " +
            "WHERE u.id = ?1")
    TicketEntity findByIdUser(long id);

    @Query("SELECT t FROM ticket t WHERE t.idSerial = ?1")
    TicketEntity findBySerialCode(SerialEntity serialCode);

    void deleteByIdSerial(SerialEntity serialEntity);

    List<TicketEntity> findByIdOrder(OrderEntity order);
    List<TicketEntity> findAllByIdOrder(OrderEntity order, Pageable pageable);
    @Query("SELECT count(*) FROM ticket t JOIN orders o ON t.idOrder.id=o.id" +
            " WHERE o.id=:id")
    int countByIdOrder(long id);

}
