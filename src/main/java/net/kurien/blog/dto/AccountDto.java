package net.kurien.blog.dto;

import lombok.Getter;
import lombok.Setter;
import net.kurien.blog.common.security.domain.AuthorityType;
import net.kurien.blog.common.type.TrueFalseType;

@Getter
@Setter
public class AccountDto {
    private String email;
    private String password;
    private String nickname;
    private String signupIp;
    private TrueFalseType block;
    private AuthorityType authority;
}
