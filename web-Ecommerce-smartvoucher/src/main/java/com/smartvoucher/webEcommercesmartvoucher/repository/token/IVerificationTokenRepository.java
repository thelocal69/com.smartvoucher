package com.smartvoucher.webEcommercesmartvoucher.repository.token;

import com.smartvoucher.webEcommercesmartvoucher.entity.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
