package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "category")
public class CategoryEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updateBy;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updateAt;
    @Column(name = "category_code", unique = true)
    private String categoryCode;
    @Column(name = "name")
    private String name;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "banner_url")
    private String bannerUrl;

    // field được references
    @OneToMany(mappedBy = "category")
    private List<WareHouseEntity> wareHouseEntityList;

    @OneToMany(mappedBy = "idCategory")
    private List<TicketEntity> ticketEntities;

}
