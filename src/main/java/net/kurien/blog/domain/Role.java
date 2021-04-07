package net.kurien.blog.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @OneToMany(mappedBy = "role")
    private Set<AccountRole> accountRoles = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<RoleAuthority> roleAuthorities = new HashSet<>();

    private String roleName;


    @Builder
    public Role(Set<AccountRole> accountRoles, Set<RoleAuthority> roleAuthorities, String roleName) {
        this.accountRoles = accountRoles;
        this.roleAuthorities = roleAuthorities;
        this.roleName = roleName;
    }
}
