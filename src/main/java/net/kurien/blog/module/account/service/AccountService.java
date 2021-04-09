package net.kurien.blog.module.account.service;

import net.kurien.blog.domain.Account;
import net.kurien.blog.domain.AccountDto;
import net.kurien.blog.domain.SearchCriteria;
import net.kurien.blog.exception.InvalidRequestException;
import net.kurien.blog.exception.NotFoundDataException;

import javax.mail.MessagingException;
import java.util.List;

public interface AccountService {
    Account getAccount(String email);
    Account getAccount(Long id);
    List<Account> getAccounts();
    void add(AccountDto accountDto) throws InvalidRequestException;
    void delete(Long id);

    void signup(AccountDto account) throws InvalidRequestException;
    void sendCertKey(String email, String certKey) throws MessagingException;
    void changePassword(AccountDto account) throws InvalidRequestException, NotFoundDataException;

    void checkPassword(String password) throws InvalidRequestException;
    void checkEmail(String email) throws InvalidRequestException;
    void checkNickname(String nickname) throws InvalidRequestException;

    boolean isExistByEmail(String email);
    boolean isExistByNickname(String nickname);
}
