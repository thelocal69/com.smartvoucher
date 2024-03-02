package com.smartvoucher.webEcommercesmartvoucher.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "wishlist")
public class WishListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "banner_url")
    private String bannerUrl;
    @Column(name = "price")
    private Double price;
    @Column(name = "original_price")
    private Double originalPrice;
    @Column(name = "max_discount_amount")
    private Double maxDiscountAmount;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "status")
    private int status;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updateBy;
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @NonNull
    private UserEntity idUser;
    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    @NonNull
    private WareHouseEntity idWarehouse;
}
