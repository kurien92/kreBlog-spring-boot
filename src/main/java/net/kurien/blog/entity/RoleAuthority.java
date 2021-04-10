package net.kurien.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RoleAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_authority_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    private LocalDateTime createAt;

    @Builder
    public RoleAuthority(Role role, Authority authority, LocalDateTime createAt) {
        this.role = role;
        this.authority = authority;
        this.createAt = createAt;
    }
}
