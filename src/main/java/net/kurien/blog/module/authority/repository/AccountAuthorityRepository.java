package net.kurien.blog.module.authority.repository;

import net.kurien.blog.domain.AccountAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAuthorityRepository extends JpaRepository<AccountAuthority, Long> {
}
