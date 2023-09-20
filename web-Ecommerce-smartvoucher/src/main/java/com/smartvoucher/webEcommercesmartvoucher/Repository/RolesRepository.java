package com.smartvoucher.webEcommercesmartvoucher.Repository;

import com.smartvoucher.webEcommercesmartvoucher.Entity.RolesEntity;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {


}
