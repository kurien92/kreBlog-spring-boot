package net.kurien.blog.module.account.service;

import net.kurien.blog.domain.Account;
import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.exception.InvalidRequestException;

import javax.mail.MessagingException;
import java.util.List;

public interface AccountService {
    Account getUser(String email);
    Account getUser(Long id);

    net.kurien.blog.module.account.entity.Account getByEmail(String accountEmail);

    List<net.kurien.blog.module.account.entity.Account> getList(SearchCriteria criteria);
    void signUp(net.kurien.blog.module.account.entity.Account account) throws InvalidRequestException;
    void sendCertKey(String accountEmail, String certKey) throws MessagingException;
    void add(net.kurien.blog.module.account.entity.Account account);
    void update(net.kurien.blog.module.account.entity.Account account);
    void delete(String accountId);
    void delete(Integer accountNo);
    void passwordChange(net.kurien.blog.module.account.entity.Account account) throws InvalidRequestException;

    void checkId(String accountId) throws InvalidRequestException;
    void checkPassword(String accountPassword) throws InvalidRequestException;
    void checkEmail(String accountEmail) throws InvalidRequestException;
    void checkNickname(String accountNickname) throws InvalidRequestException;

    boolean isExistById(String accountId);
    boolean isExistByEmail(String accountEmail);
    boolean isExistByNickname(String accountNickname);
}
