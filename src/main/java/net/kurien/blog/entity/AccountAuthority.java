package net.kurien.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AccountAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_authority_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    private LocalDateTime createTime;

    @Builder
    public AccountAuthority(Account account, Authority authority, LocalDateTime createTime) {
        this.account = account;
        this.authority = authority;
        this.createTime = createTime;
    }
}
