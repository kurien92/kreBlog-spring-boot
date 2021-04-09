package net.kurien.blog.module.authority.service.impl;

import lombok.RequiredArgsConstructor;
import net.kurien.blog.common.security.domain.AuthorityType;
import net.kurien.blog.module.authority.dao.AuthorityDao;
import net.kurien.blog.module.authority.dao.GroupAuthorityDao;
import net.kurien.blog.domain.Authority;
import net.kurien.blog.module.authority.entity.GroupAuthority;
import net.kurien.blog.module.authority.repository.AuthorityRepository;
import net.kurien.blog.module.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicAuthorityService implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthority(Long id) {
        return authorityRepository.findById(id).get();
    }

    @Override
    public Authority getAuthority(AuthorityType authorityType) {
        return authorityRepository.findByAuthority(authorityType);
    }

    @Override
    public List<Authority> getAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public void add(Authority authority) {
        authorityRepository.save(authority);
    }

    @Override
    public void delete(Long id) {
        authorityRepository.deleteById(id);
    }
}
