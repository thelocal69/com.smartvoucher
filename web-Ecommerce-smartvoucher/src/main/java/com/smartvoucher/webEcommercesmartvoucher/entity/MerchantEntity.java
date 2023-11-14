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
@Entity(name = "merchant")
public class MerchantEntity{
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
    @Column(name = "merchant_code", unique = true)
    private String merchantCode;
    @Column(name = "name")
    private String name;
    @Column(name = "legal_name")
    private String legalName;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "merchant")
    private List<ChainEntity> chainEntityList;

    @OneToMany(mappedBy = "merchant")
    private List<StoreEntity> storeEntityList;
}
