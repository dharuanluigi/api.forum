package br.com.alura.api.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tb_user_activation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String code;
    private Instant createdAt = Instant.now();
    @OneToOne
    private User user;

    public UserActivation(String activationCode, Instant createdAt, User user) {
        this.id = null;
        this.code = activationCode;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Boolean isExpired() {
        var expirationTimeInSeconds = 5 * 60;
        var expiresAt = this.createdAt.plusSeconds(expirationTimeInSeconds);
        return Instant.now().isAfter(expiresAt);
    }
}
