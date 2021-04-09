package net.kurien.blog.domain;

import lombok.*;
import net.kurien.blog.common.security.domain.AuthorityType;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Authority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    @Builder
    public Authority(AuthorityType authority) {
        this.authority = authority;
    }
}
