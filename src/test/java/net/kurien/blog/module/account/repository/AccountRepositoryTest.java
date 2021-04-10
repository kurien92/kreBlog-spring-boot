package net.kurien.blog.module.account.repository;

import net.kurien.blog.common.type.TrueFalseType;
import net.kurien.blog.entity.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    static List<Account> accounts = new ArrayList<>();

    @BeforeAll
    static void beforeAll(@Autowired AccountRepository accountRepository) {
        accounts.add(Account.builder()
                .block(TrueFalseType.FALSE)
                .email("kurien92@gmail.com")
                .nickname("kre")
                .password("test")
                .signupTime(LocalDateTime.now())
                .signupIp("192.168.0.55")
                .build());

        accounts.add(Account.builder()
                .block(TrueFalseType.FALSE)
                .email("kurien@naver.com")
                .nickname("kre29")
                .password("test222")
                .signupTime(LocalDateTime.now())
                .signupIp("192.168.0.11")
                .build());

        accounts.add(Account.builder()
                .block(TrueFalseType.TRUE)
                .email("kurien92@naver.com")
                .nickname("kre92")
                .password("test333")
                .signupTime(LocalDateTime.now())
                .signupIp("192.168.0.44")
                .build());

        accountRepository.deleteAll();
        accountRepository.saveAll(accounts);

        assertThat(accountRepository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("회원번호로 조회한 값은 입력한 값과 동일해야 한다.")
    void findByNo() {
        Optional<Account> accountById = accountRepository.findById(1l);

        assertThat(accounts.get(0).toString()).isEqualTo(accountById.get().toString());
    }

    @Test
    @DisplayName("이메일 조회한 값은 입력한 값과 동일해야 한다.")
    void findById() {
        Account accountByEmail = accountRepository.findByEmail("kurien@naver.com");

        assertThat(accounts.get(1).toString()).isEqualTo(accountByEmail.toString());
    }

    @Test
    @DisplayName("회원번호로 회원정보를 삭제한다.")
    void deleteById() {
        accountRepository.deleteById(3l);

        assertThat(accountRepository.count()).isEqualTo(2);
    }
}