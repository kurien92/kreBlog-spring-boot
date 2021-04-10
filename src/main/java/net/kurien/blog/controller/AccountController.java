package net.kurien.blog.controller;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kurien.blog.common.template.Template;
import net.kurien.blog.dto.AccountDto;
import net.kurien.blog.exception.InvalidRequestException;
import net.kurien.blog.exception.NotFoundDataException;
import net.kurien.blog.module.account.service.AccountService;
import net.kurien.blog.util.CertificationUtil;
import net.kurien.blog.util.RequestUtil;
import net.kurien.blog.util.TokenUtil;
import net.kurien.blog.util.ValidationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final Template template;

    @RequestMapping(value="/account/signup", method = RequestMethod.GET)
    public String signup(HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchAlgorithmException {
        template.setSubTitle("Sign up");
        template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/account.css\">");

        model.addAttribute("token", TokenUtil.createToken(request, "signup", 30 * 60 * 1000));

        return "account/signup";
    }

    @RequestMapping(value="/account/find", method = RequestMethod.GET)
    public String find(HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchAlgorithmException {
        template.setSubTitle("Find");
        template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/account.css\">");

        model.addAttribute("token", TokenUtil.createToken(request, "find", 30 * 60 * 1000));

        return "account/find";
    }

    @ResponseBody
    @RequestMapping(value="/account/findCheck", method = RequestMethod.POST)
    public JsonObject findCheck(String token, AccountDto accountDto, Model model, HttpServletRequest request) {
        /**
         * 세션에 입력된 메일과 등록하려는 메일주소가 같은지 확인.
         * 인증이 되었는지 확인
         */
        JsonObject json = new JsonObject();

        try {
            if(TokenUtil.checkToken(request, "find", token) == false) {
                throw new InvalidRequestException("비밀번호변경 가능 시간이 만료되었습니다. 다시 시도해주세요.");
            }

            if(CertificationUtil.checkCerted(request, "find", accountDto.getEmail()) == false) {
                throw new InvalidRequestException("인증된 메일 주소가 아닙니다. 다시 시도하시기 바랍니다.");
            }

            accountService.changePassword(accountDto);

            CertificationUtil.clearCert(request, "find");
        } catch(InvalidRequestException | NotFoundDataException e) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", e.getMessage());

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/sendCertKey", method = RequestMethod.POST)
    public JsonObject sendCertKey(String email, String certType, HttpServletRequest request) {
        /**
         * 1. 사용자 메일 주소 형태 검증
         * 2. 이미 사용중인 주소인지 확인
         * 3. 사용중이지 않다면 아이디와 인증번호 세션에 저장
         * 4. 저장한 인증번호 발송
         * 5. success 리턴
         */
        JsonObject json = new JsonObject();

        try {
            if (ValidationUtil.email(email) == false) {
                throw new InvalidRequestException("메일주소 형식이 잘못되었습니다. 확인하신 후 다시 시도하시기 바랍니다.");
            }
        } catch(InvalidRequestException e) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", e.getMessage());

            return json;
        }

        try {
            String certKey = CertificationUtil.createCertKey(request, certType, email, 5, 3 * 60 * 1000);

            accountService.sendCertKey(email, certKey);
        } catch(MessagingException e) {
            log.error("메일 전송 오류 발생", e);

            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", "메일 전송 중 오류가 발생했습니다. 다시 시도하시거나 증상이 계속되는 경우 관리자에게 문의하시기 바랍니다.");

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/checkCertKey")
    public JsonObject checkCertKey(String email, String certKey, String certType, HttpServletRequest request) {
        /**
         * 1. 세션에 저장된 인증번호와 이메일과 동일한지 확인
         * 2. 동일하다면 success 리턴
         */
        JsonObject json = new JsonObject();

        if(CertificationUtil.checkCertKey(request, certType, email, certKey) == false) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", "인증을 실패했습니다. 다시한번 시도하시기 바랍니다.");

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/signupCheck", method = RequestMethod.POST)
    public JsonObject signupCheck(String token, AccountDto accountDto, Model model, HttpServletRequest request) {
        /**
         * 세션에 입력된 메일과 등록하려는 메일주소가 같은지 확인.
         * 인증이 되었는지 확인
         */

        JsonObject json = new JsonObject();

        try {
            if(TokenUtil.checkToken(request, "signup", token) == false) {
                throw new InvalidRequestException("회원가입 가능 시간이 만료되었습니다. 다시 시도해주세요.");
            }

            if(CertificationUtil.checkCerted(request, "signup", accountDto.getEmail()) == false) {
                throw new InvalidRequestException("인증된 메일 주소가 아닙니다. 다시 시도하시기 바랍니다.");
            }

            accountDto.setSignupIp(RequestUtil.getRemoteAddr(request));

            accountService.signup(accountDto);

            CertificationUtil.clearCert(request, "find");
        } catch(InvalidRequestException e) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", e.getMessage());

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/checkEmail", method = RequestMethod.POST)
    public JsonObject checkEmail(String email) {
        JsonObject json = new JsonObject();

        try {
            accountService.checkEmail(email);
        } catch(InvalidRequestException e) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", e.getMessage());

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/checkNickname", method = RequestMethod.POST)
    public JsonObject checkNickname(String nickname) {
        JsonObject json = new JsonObject();

        try {
            accountService.checkNickname(nickname);
        } catch(InvalidRequestException e) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", e.getMessage());

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/sendFindCertKey", method = RequestMethod.POST)
    public JsonObject sendFindCertKey(String accountEmail, HttpServletRequest request) {
        JsonObject json = new JsonObject();

        try {
            if(ValidationUtil.email(accountEmail) == false) {
                throw new InvalidRequestException("메일주소 형식이 잘못되었습니다. 확인하신 후 다시 시도하시기 바랍니다.");
            }

            if(accountService.isExistByEmail(accountEmail) == false) {
                throw new InvalidRequestException("등록된 메일주소가 없습니다. 확인 후 다시 시도하시기 바랍니다.");
            }
        } catch(InvalidRequestException e) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", e.getMessage());

            return json;
        }

        try {
            String certKey = CertificationUtil.createCertKey(request, "find", accountEmail, 5,  3 * 60 * 1000);

            accountService.sendCertKey(accountEmail, certKey);
        } catch (MessagingException e) {
            log.error("메일 전송 오류 발생", e);

            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", "메일 전송 중 오류가 발생했습니다. 다시 시도하시거나 증상이 계속되는 경우 관리자에게 문의하시기 바랍니다.");

            return json;
        }

        json.addProperty("result", "success");
        json.add("value", new JsonObject());
        json.addProperty("message", "");

        return json;
    }

    @ResponseBody
    @RequestMapping(value="/account/checkFindCertKey")
    public JsonObject checkFindCertKey(String email, String certKey, HttpServletRequest request) {
        /**
         * 1. 세션에 저장된 인증번호와 이메일과 동일한지 확인
         * 2. 동일하다면 success 리턴
         */
        JsonObject json = new JsonObject();

        if(CertificationUtil.checkCertKey(request, "find", email, certKey) == false) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", "인증을 실패했습니다. 다시한번 시도하시기 바랍니다.");

            return json;
        }

        if(!accountService.isExistByEmail(email)) {
            json.addProperty("result", "fail");
            json.add("value", new JsonObject());
            json.addProperty("message", "존재하지 않는 이메일입니다.");

            return json;
        }

        JsonObject jsonValue = new JsonObject();

        json.addProperty("result", "success");
        json.add("value", jsonValue);
        json.addProperty("message", "");

        return json;
    }
}
