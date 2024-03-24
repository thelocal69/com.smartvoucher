package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.WishListEntity;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishListEntity, Long> {
 WishListEntity findOneById(Long id);
 WishListEntity findByIdUser_IdAndIdWarehouse_Id(Long idUser, Long idWarehouse);
 List<WishListEntity> findAllByIdUser_Id(Long idUser, Pageable pageable);
 @Query("SELECT count(*) FROM wishlist wt JOIN users u ON wt.idUser.id=u.id" +
         " WHERE u.id=:idUser")
 int countByIdUser(long idUser);

void deleteById(@NonNull Long id);
}
