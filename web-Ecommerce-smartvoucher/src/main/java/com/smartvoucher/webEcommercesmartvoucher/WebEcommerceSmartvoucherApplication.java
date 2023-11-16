package com.smartvoucher.webEcommercesmartvoucher;

import com.smartvoucher.webEcommercesmartvoucher.entity.TicketEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.TicketHistoryEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;

@SpringBootApplication()
public class WebEcommerceSmartvoucherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebEcommerceSmartvoucherApplication.class, args);
	}


}
