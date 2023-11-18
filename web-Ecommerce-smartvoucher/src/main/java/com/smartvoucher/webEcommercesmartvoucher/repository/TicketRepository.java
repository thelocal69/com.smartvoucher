package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.CategoryEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
