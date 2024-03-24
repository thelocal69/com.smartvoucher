package com.smartvoucher.webEcommercesmartvoucher.entity.token;

import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "token")
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "revokes")
    private boolean revoke;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;
}
