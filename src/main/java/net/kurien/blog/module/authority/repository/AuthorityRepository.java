package net.kurien.blog.module.authority.repository;

import net.kurien.blog.common.security.domain.AuthorityType;
import net.kurien.blog.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthority(AuthorityType authorityType);
}
