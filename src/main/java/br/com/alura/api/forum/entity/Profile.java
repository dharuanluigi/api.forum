package br.com.alura.api.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Profile implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
