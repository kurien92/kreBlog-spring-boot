package net.kurien.blog.module.authority.service;

import net.kurien.blog.common.security.domain.AuthorityType;
import net.kurien.blog.entity.Authority;

import java.util.List;

public interface AuthorityService {
    Authority getAuthority(Long id);
    Authority getAuthority(AuthorityType authorityType);
    List<Authority> getAuthorities();
    void add(Authority authority);
    void delete(Long id);
}
