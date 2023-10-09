package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
