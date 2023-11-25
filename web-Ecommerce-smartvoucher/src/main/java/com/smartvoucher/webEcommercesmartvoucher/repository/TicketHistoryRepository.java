package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketHistoryDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistoryEntity, Long> {

    @Query("SELECT t FROM ticket_history t WHERE t.serialCode = ?1")
    TicketHistoryEntity findBySerialCode (String serialCode);

    void deleteByIdTicket(TicketEntity ticketEntity);

    void deleteBySerialCode(String serialCode);

}
