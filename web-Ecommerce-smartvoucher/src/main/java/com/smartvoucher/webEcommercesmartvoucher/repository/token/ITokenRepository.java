package com.smartvoucher.webEcommercesmartvoucher.repository.token;

import com.smartvoucher.webEcommercesmartvoucher.entity.token.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Tokens, Long> {

    @Query(" SELECT t FROM token t JOIN users u ON t.user.id = u.id" +
            "  WHERE u.id = :userId AND (t.expired = false or t.revoke = false )")
    List<Tokens> findAllTokenValidByUser(Long userId);

    Optional<Tokens> findByToken(String token);
}
