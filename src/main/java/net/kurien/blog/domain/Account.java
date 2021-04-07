package net.kurien.blog.domain;

import lombok.*;
import net.kurien.blog.common.type.TrueFalseType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString(exclude = {"accountAuthorities", "accountRoles"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @OneToMany(mappedBy = "account")
    private Set<AccountAuthority> accountAuthorities = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private Set<AccountRole> accountRoles = new HashSet<>();

    @Column(length = 200)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(length = 30)
    private String nickname;

    @Enumerated(EnumType.ORDINAL)
    private TrueFalseType block;

    private LocalDateTime signupTime;

    @Column(length = 15)
    private String signupIp;

    @Builder
    public Account(Set<AccountAuthority> accountAuthorities, Set<AccountRole> accountRoles, String email, String password, String nickname, TrueFalseType block, LocalDateTime signupTime, String signupIp) {
        this.accountAuthorities = accountAuthorities;
        this.accountRoles = accountRoles;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.block = block;
        this.signupTime = signupTime;
        this.signupIp = signupIp;
    }
}
