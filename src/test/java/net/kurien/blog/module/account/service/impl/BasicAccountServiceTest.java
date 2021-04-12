package net.kurien.blog.module.account.service.impl;

import net.kurien.blog.common.type.TrueFalseType;
import net.kurien.blog.dto.AccountDto;
import net.kurien.blog.entity.Account;
import net.kurien.blog.exception.DuplicatedDataException;
import net.kurien.blog.exception.InvalidRequestException;
import net.kurien.blog.exception.NotFoundDataException;
import net.kurien.blog.module.account.repository.AccountRepository;
import net.kurien.blog.module.account.service.AccountService;
import net.kurien.blog.module.authority.repository.AccountAuthorityRepository;
import net.kurien.blog.module.mail.service.MailService;
import net.kurien.blog.module.mail.service.impl.DummyMailService;
import net.kurien.blog.util.EncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BasicAccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountAuthorityRepository accountAuthorityRepository;

    @TestConfiguration
    public static class DummyConfig {
        @Bean
        public MailService mailService() {
            return new DummyMailService();
        }
    }

    List<Account> accounts = new ArrayList<>();

    @BeforeEach
    void beforeAll() {
        accounts.add(Account.builder()
                .block(TrueFalseType.FALSE)
                .email("kurien92@gmail.com")
                .nickname("kre")
                .password("test")
                .signupTime(LocalDateTime.of(2020, 10, 11, 13,23,54, 200))
                .signupIp("192.168.0.55")
                .build());

        accounts.add(Account.builder()
                .block(TrueFalseType.FALSE)
                .email("kurien@naver.com")
                .nickname("kre29")
                .password("test222")
                .signupTime(LocalDateTime.of(2020, 1, 1, 13,5,44, 200))
                .signupIp("192.168.0.11")
                .build());

        accounts.add(Account.builder()
                .block(TrueFalseType.TRUE)
                .email("kurien92@naver.com")
                .nickname("kre92")
                .password("test333")
                .signupTime(LocalDateTime.of(2021, 12, 21, 13,44,32, 200))
                .signupIp("192.168.0.44")
                .build());


        accountAuthorityRepository.deleteAll();
        accountRepository.deleteAll();

        accountRepository.saveAll(accounts);

        assertThat(accountRepository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("추가되었던 사용자와 아이디로 조회된 사용자는 같아야 한다.")
    void getAccount() {
        Account accountById1 = accountService.getAccount(accounts.get(0).getId());
        Account accountById2 = accountService.getAccount(accounts.get(1).getId());
        Account accountById3 = accountService.getAccount(accounts.get(2).getId());

        assertThat(accounts.get(0)).isEqualTo(accountById1);
        assertThat(accounts.get(1)).isEqualTo(accountById2);
        assertThat(accounts.get(2)).isEqualTo(accountById3);
    }

    @Test
    @DisplayName("추가되었던 사용자와 이메일로 조회된 사용자는 같아야 한다.")
    void testGetAccount() {
        Account accountByEmail1 = accountService.getAccount(accounts.get(0).getEmail());
        Account accountByEmail2 = accountService.getAccount(accounts.get(1).getEmail());
        Account accountByEmail3 = accountService.getAccount(accounts.get(2).getEmail());

        assertThat(accounts.get(0)).isEqualTo(accountByEmail1);
        assertThat(accounts.get(1)).isEqualTo(accountByEmail2);
        assertThat(accounts.get(2)).isEqualTo(accountByEmail3);
    }

    @Test
    @DisplayName("추가되었던 사용자와 조회된 사용자는 같아야 한다.")
    void getAccounts() {
        List<Account> findAccounts = accountService.getAccounts();
        List<Account> findAccounts2 = accountService.getAccounts();

        assertThat(this.accounts).isEqualTo(findAccounts);
        assertThat(findAccounts2).isEqualTo(findAccounts2);
    }

    @Test
    @DisplayName("추가된 사용자의 수와 조회된 사용자의 수는 같아야 한다.")
    void add() {
        Account account = Account.builder()
                .block(TrueFalseType.TRUE)
                .email("kurien92@naver.com")
                .nickname("kre92")
                .password("test333")
                .signupTime(LocalDateTime.of(2021, 12, 21, 13,12,32, 200))
                .signupIp("192.168.0.44")
                .build();

        accountRepository.save(account);

        Long count = accountRepository.count();
        assertThat(count).isEqualTo(4L);
    }

    @Test
    @DisplayName("추가되었던 사용자를 삭제한 경우에도 조회된 갯수는 같아야 한다.")
    void delete() {
        accountService.delete(accounts.get(0).getId());
        accountService.delete(accounts.get(1).getId());

        Long count = accountRepository.count();
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("회원가입한 사용자의 수와 조회된 사용자의 수는 같아야 한다.")
    void signup() throws InvalidRequestException, DuplicatedDataException {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("kurien@kakao.com");
        accountDto.setPassword("test1234");
        accountDto.setNickname("ASDFGHJKL:");
        accountDto.setBlock(TrueFalseType.FALSE);
        accountDto.setSignupIp("111.222.113.32");

        accountService.signup(accountDto);

        Long count = accountRepository.count();
        assertThat(count).isEqualTo(4L);
    }

    @Test
    @DisplayName("[Dummy] 기입된 메일 주소로 인증번호를 전송한다.")
    void sendCertKey() {
        assertThatNoException().isThrownBy(() -> accountService.sendCertKey("kurien92@gmail.com", "1234"));
    }

    @Test
    @DisplayName("특정 회원의 비밀번호를 입력된 비밀번호로 변경한다.")
    void changePassword() throws NotFoundDataException, InvalidRequestException {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("kurien92@gmail.com");
        accountDto.setPassword("1234test");
        accountDto.setNickname("dddd");
        accountDto.setBlock(TrueFalseType.FALSE);
        accountDto.setSignupIp("111.222.113.32");

        assertThat(EncryptionUtil.checkPassword("1234test", accounts.get(0).getPassword())).isFalse();
        accountService.changePassword(accountDto);

        Account accountByEmail = accountService.getAccount("kurien92@gmail.com");
        assertThat(EncryptionUtil.checkPassword("1234test", accountByEmail.getPassword())).isTrue();
    }

    @ParameterizedTest(name = "비밀번호 형식에 맞지 않다면 예외를 출력한다. - {index}: {0}")
    @ValueSource(strings = {"1234", "", "1"})
    void checkPasswordFail(String input) {
        assertThrows(InvalidRequestException.class, () -> accountService.checkPassword(input));
    }

    @ParameterizedTest(name = "비밀번호 형식이 맞다면 정상처리된다. - {index}: {0}")
    @ValueSource(strings = {"12345678", "dskjfadss", "한글입니다.ㅋㄴㅇㅁㄴ"})
    void checkPasswordSuccess(String input) {
        assertThatNoException().isThrownBy(() -> accountService.checkPassword(input));
    }

    @ParameterizedTest(name = "이메일이 형식에 맞지 않다면 예외를 출력한다. - {index}: {0}")
    @ValueSource(strings = {"test", "t", "@gmail.com", "@@gmail.com"})
    void checkEmailFailWithInvalidRequestException(String input) {
        assertThrows(InvalidRequestException.class, () -> accountService.checkEmail(input));
    }

    @Test
    @DisplayName("이메일이 기존 데이터와 중복되는 경우 시 예외를 출력한다.")
    void checkEmailFailWithDuplicatedDataException() {
        for(Account account : accounts) {
            assertThrows(DuplicatedDataException.class, () -> accountService.checkEmail(account.getEmail()));
        }
    }

    @ParameterizedTest(name = "이메일 형식이 맞다면 정상처리된다. - {index}: {0}")
    @ValueSource(strings = {"12345@naver.com", "_test@gmail.com", "a@b.c"})
    void checkEmailSuccess(String input) {
        assertThatNoException().isThrownBy(() -> accountService.checkEmail(input));
    }

    @ParameterizedTest(name = "닉네임이 형식에 맞지 않다면 예외를 출력한다. - {index}: {0}")
    @ValueSource(strings = {"te", "abcdefghijklmnopqrstu"})
    void checkNicknameFail(String input) {
        assertThrows(InvalidRequestException.class, () -> accountService.checkNickname(input));
    }

    @Test
    @DisplayName("닉네임이 기존 데이터와 중복되는 경우 시 예외를 출력한다.")
    void checkNicknameFailWithDuplicatedDataException() {
        for(Account account : accounts) {
            assertThrows(DuplicatedDataException.class, () -> accountService.checkNickname(account.getNickname()));
        }
    }

    @ParameterizedTest(name = "닉네임 형식이 맞다면 정상처리된다. - {index}: {0}")
    @ValueSource(strings = {"12345678", "abcdefgds"})
    void checkNicknameSuccess(String input) {
        assertThatNoException().isThrownBy(() -> accountService.checkPassword(input));
    }

    @Test
    @DisplayName("입력된 이메일이 이미 존재하는 데이터라면 true, 존재하지 않는다면 false를 반환한다.")
    void isExistByEmail() {
        assertThat(accountService.isExistByEmail(accounts.get(0).getEmail())).isTrue();
        assertThat(accountService.isExistByEmail("kurien92@kakao.com")).isFalse();
    }

    @Test
    @DisplayName("입력된 닉네임이 이미 존재하는 데이터라면 true, 존재하지 않는다면 false를 반환한다.")
    void isExistByNickname() {
        assertThat(accountService.isExistByNickname(accounts.get(0).getNickname())).isTrue();
        assertThat(accountService.isExistByNickname("test1111")).isFalse();
    }
}