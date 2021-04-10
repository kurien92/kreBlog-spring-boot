package net.kurien.blog.common.security;

import lombok.RequiredArgsConstructor;
import net.kurien.blog.common.security.domain.User;
import net.kurien.blog.common.type.TrueFalseType;
import net.kurien.blog.entity.Account;
import net.kurien.blog.entity.AccountRole;
import net.kurien.blog.entity.RoleAuthority;
import net.kurien.blog.module.account.service.AccountService;
import net.kurien.blog.entity.AccountAuthority;
import net.kurien.blog.entity.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountService accountService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String PREFIX = "ROLE_";

        Account account = accountService.getAccount(username);

        if(account == null) {
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 잘못 입력되었습니다.");
        }

        Set<GrantedAuthority> authoritiesSet = new HashSet<>();

        Set<AccountAuthority> accountAuthorities = account.getAccountAuthorities();

        for(AccountAuthority accountAuthority : accountAuthorities) {
            Authority authority = accountAuthority.getAuthority();

            authoritiesSet.add(new SimpleGrantedAuthority(PREFIX + authority.getAuthority()));
        }

        Set<AccountRole> accountRoles = account.getAccountRoles();

        Set<RoleAuthority> roleAuthorities = new HashSet<>();

        for(AccountRole accountRole : accountRoles) {
            roleAuthorities.addAll(accountRole.getRole().getRoleAuthorities());
        }

        for(RoleAuthority roleAuthority : roleAuthorities) {
            Authority authority = roleAuthority.getAuthority();
            authoritiesSet.add(new SimpleGrantedAuthority(PREFIX + authority.getAuthority()));
        }

        User user = new User(
            account.getId(),
            account.getEmail(),
            account.getPassword(),
            account.getNickname(),
            account.getBlock() == TrueFalseType.TRUE,
            authoritiesSet
        );

        return user;
    }
}