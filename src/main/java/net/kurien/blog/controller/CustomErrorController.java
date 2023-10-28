package net.kurien.blog.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.kurien.blog.common.template.Template;
import net.kurien.blog.exception.handler.BasicExceptionHandler;

@Slf4j
@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
	private final Template template;

	@Autowired
	public CustomErrorController(Template template) {
		this.template = template;
	}

	@RequestMapping
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				return "error/badRequest";
			} else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error/forbidden";
			} else if(statusCode == HttpStatus.NOT_FOUND.value()) {
					return "error/notFound";
			} else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error/internalServerError";
			} else if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				return "error/serviceUnavailable";
			}
		}

		return "error/exception";
	}


	@RequestMapping("/badRequest")
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String badRequest(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requestURI = (String)request.getAttribute("javax.servlet.forward.request_uri");
		String referer = request.getHeader("referer");

		model.addAttribute("referer", referer);
		
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/error.css\">");
		
		log.info("exception requestURI: " + requestURI);

		model.addAttribute("exceptionMsg", "잘못된 요청입니다.");
		model.addAttribute("exceptionDescription", "잘못된 데이터가 전송되었습니다.<br>데이터를 확인한 뒤 다시 시도하여주십시오.");

		return "error/exception";
	}
	
	@RequestMapping("/forbidden")
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public String accessDenied(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requestURI = (String)request.getAttribute("javax.servlet.forward.request_uri");
		String referer = request.getHeader("referer");

		model.addAttribute("referer", referer);
		
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/error.css\">");
		
		log.info("exception requestURI: " + requestURI);

		model.addAttribute("exceptionMsg", "접근 권한이 없습니다.");
		model.addAttribute("exceptionDescription", "접근권한이 없습니다.<br>접근권한이 있는 계정으로 로그인하시기 바랍니다.");

		return "error/exception";
	}

	@RequestMapping("/notFound")
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String notFound(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requestURI = (String)request.getAttribute("javax.servlet.forward.request_uri");
		String referer = request.getHeader("referer");

		model.addAttribute("referer", referer);
		
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/error.css\">");
		
		log.info("exception requestURI: " + requestURI);

		model.addAttribute("exceptionMsg", "페이지를 찾을 수 없습니다.");
		model.addAttribute("exceptionDescription", "페이지가 이동되었거나 삭제되었습니다.<br>카테고리를 선택하시거나 아래에 표시된 버튼을 통해 이동하시기 바랍니다.");

		return "error/exception";
	}

	@RequestMapping("/internalServerError")
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String internalServerError(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requestURI = (String)request.getAttribute("javax.servlet.forward.request_uri");
		String referer = request.getHeader("referer");

		model.addAttribute("referer", referer);
		
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/error.css\">");
		
		log.info("exception requestURI: " + requestURI);

		model.addAttribute("exceptionMsg", "예상하지 못한 오류가 발생했습니다.");
		model.addAttribute("exceptionDescription", "예상하지 못한 오류가 발생했습니다.<br>카테고리를 선택하시거나 아래에 표시된 버튼을 통해 이동하시기 바랍니다.");

		return "error/exception";
	}
	
	@RequestMapping("/serviceUnavailable")
	@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE)
	public String serviceUnavailable(HttpServletRequest request, HttpServletResponse response, Model model) {
		String requestURI = (String)request.getAttribute("javax.servlet.forward.request_uri");
		String referer = request.getHeader("referer");

		model.addAttribute("referer", referer);
		
		template.getCss().add("<link rel=\"stylesheet\" href=\"/css/module/error.css\">");
		
		log.info("exception requestURI: " + requestURI);

		model.addAttribute("exceptionMsg", "서비스가 원활하지 않습니다.");
		model.addAttribute("exceptionDescription", "사용자가 많거나 오류로 인하여 서비스가 원활하지 않습니다.<br>잠시 후 다시 시도해보시거나 문제가 지속되는 경우 관리자에게 문의하시기 바랍니다.");

		return "error/exception";
	}

	public String getErrorPath() {
		return null;
	}
}
