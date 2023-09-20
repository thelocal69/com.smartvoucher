package com.smartvoucher.webEcommercesmartvoucher.Repository;

import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialRepository extends JpaRepository<SerialEntity, Long> {

}
