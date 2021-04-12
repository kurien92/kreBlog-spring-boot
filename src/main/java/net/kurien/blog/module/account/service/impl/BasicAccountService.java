package net.kurien.blog.module.account.service.impl;

import lombok.RequiredArgsConstructor;
import net.kurien.blog.common.security.domain.AuthorityType;
import net.kurien.blog.common.type.TrueFalseType;
import net.kurien.blog.dto.AccountDto;
import net.kurien.blog.entity.Account;
import net.kurien.blog.entity.AccountAuthority;
import net.kurien.blog.entity.Authority;
import net.kurien.blog.exception.DuplicatedDataException;
import net.kurien.blog.exception.InvalidRequestException;
import net.kurien.blog.exception.NotFoundDataException;
import net.kurien.blog.module.account.repository.AccountRepository;
import net.kurien.blog.module.account.service.AccountService;
import net.kurien.blog.module.authority.repository.AccountAuthorityRepository;
import net.kurien.blog.module.authority.service.AuthorityService;
import net.kurien.blog.module.mail.service.MailService;
import net.kurien.blog.util.EncryptionUtil;
import net.kurien.blog.util.ValidationUtil;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicAccountService implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthorityService authorityService;
    private final AccountAuthorityRepository accountAuthorityRepository;
    private final MailService mailService;

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(!accountOptional.isPresent()) {
            return null;
        }
        return accountOptional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void add(AccountDto accountDto) throws InvalidRequestException, DuplicatedDataException {
        checkPassword(accountDto.getPassword());
        checkEmail(accountDto.getEmail());
        checkNickname(accountDto.getNickname());

        Authority authority = authorityService.getAuthority(accountDto.getAuthority());

        Account account = Account.builder()
                .email(accountDto.getEmail())
                .password(EncryptionUtil.hashPassword(accountDto.getPassword()))
                .nickname(accountDto.getNickname())
                .block(TrueFalseType.FALSE)
                .signupTime(LocalDateTime.now())
                .signupIp(accountDto.getSignupIp())
                .build();

        AccountAuthority accountAuthority = AccountAuthority.builder()
                .account(account)
                .authority(authority)
                .build();

        accountRepository.save(account);
        accountAuthorityRepository.save(accountAuthority);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void signup(AccountDto accountDto) throws InvalidRequestException, DuplicatedDataException {
        checkPassword(accountDto.getPassword());
        checkEmail(accountDto.getEmail());
        checkNickname(accountDto.getNickname());

        Account account = Account.builder()
                .email(accountDto.getEmail())
                .password(EncryptionUtil.hashPassword(accountDto.getPassword()))
                .nickname(accountDto.getNickname())
                .block(TrueFalseType.FALSE)
                .signupTime(LocalDateTime.now())
                .signupIp(accountDto.getSignupIp())
                .build();

        Authority authority = authorityService.getAuthority(AuthorityType.USER);

        AccountAuthority accountAuthority = AccountAuthority.builder()
                .account(account)
                .authority(authority)
                .createTime(LocalDateTime.now())
                .build();

        accountRepository.save(account);
        accountAuthorityRepository.save(accountAuthority);
    }

    @Override
    public void sendCertKey(String email, String certKey) throws MessagingException {
        mailService.send("kurien92@gmail.com", email, "Kurien's Blog 인증메일입니다.", "인증번호는 '" + certKey + "' 입니다.");
    }

    @Override
    @Transactional
    public void changePassword(AccountDto accountDto) throws NotFoundDataException, InvalidRequestException {
        Account account = accountRepository.findByEmail(accountDto.getEmail());

        if(account == null) {
            throw new NotFoundDataException("해당 이메일이 존재하지 않습니다.");
        }

        if(EncryptionUtil.checkPassword(accountDto.getPassword(), account.getPassword()) == true) {
            throw new InvalidRequestException("이미 사용중인 비밀번호입니다. 다른 비밀번호를 입력해주세요.");
        }

        String hashedPassword = EncryptionUtil.hashPassword(accountDto.getPassword());

        account.changePassword(hashedPassword);
    }

    public void checkPassword(String accountPassword) throws InvalidRequestException {
        if(ValidationUtil.password(accountPassword) == false) {
            throw new InvalidRequestException("비밀번호 형식이 아닙니다. 확인 후 다시시도하여주시기 바랍니다.");
        }
    }

    public void checkEmail(String accountEmail) throws InvalidRequestException, DuplicatedDataException {
        if (ValidationUtil.email(accountEmail) == false) {
            throw new InvalidRequestException("이메일 형식이 아닙니다. 확인 후 다시시도하여주시기 바랍니다.");
        }

        if (isExistByEmail(accountEmail)) {
            throw new DuplicatedDataException("중복된 이메일이 있습니다. 다른 이메일을 입력해주세요.");
        }
    }

    public void checkNickname(String nickname) throws InvalidRequestException, DuplicatedDataException {
        if(ValidationUtil.length(nickname, 3, 20) == false) {
            throw new InvalidRequestException("닉네임의 길이를 확인하여주시기 바랍니다.");
        }


        if(isExistByNickname(nickname)) {
            throw new DuplicatedDataException("중복된 닉네임이 있습니다. 다른 닉네임을 입력해주세요.");
        }
    }

    public boolean isExistByEmail(String email) {
        Account account = Account.builder()
                .email(email)
                .build();

        Example<Account> accountExample = Example.of(account);
        Long emailCount = accountRepository.count(accountExample);

        if(emailCount == 0) {
            return false;
        }

        return true;
    }

    public boolean isExistByNickname(String nickname) {
        Account account = Account.builder()
                .nickname(nickname)
                .build();

        Example<Account> accountExample = Example.of(account);

        Long emailCount = accountRepository.count(accountExample);

        if(emailCount == 0) {
            return false;
        }

        return true;
    }
}
