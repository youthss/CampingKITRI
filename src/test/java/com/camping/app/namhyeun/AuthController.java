package com.camping.app.namhyeun;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camping.app.domain.Member;
import com.camping.app.service.MemberService;

@RestController("json/AuthController")
@RequestMapping("/json/auth")
public class AuthController {

	MemberService memberService;
	ServletContext servletContext;

	public AuthController(MemberService memberService, ServletContext servletContext) {
		this.memberService = memberService;
		this.servletContext = servletContext;
	}

	// final static Logger logger = LogManager.getLogger(AuthController.class);

	@PostMapping("login")
	public Object login(String email, String password, String name, HttpSession session, int sns_no,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> content = new HashMap<>();
		Member member = memberService.get(email, password);
		request.setAttribute("email", email);
		if (member == null) {
			content.put("status", "fail");
			request.setAttribute("status", "fail");
			content.put("message", "이메일 없거나 암호가 맞지 않습니다.");
		} else {
			session.setAttribute("loginUser", member);

			content.put("status", "success");
			request.setAttribute("status", "success");
		}
		return content;
	}

	@GetMapping("logout")
	public Object logout(HttpSession session, HttpServletRequest request) throws Exception {

		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser != null) {
			request.setAttribute("email", loginUser.getEmail());
		}
		// logger.debug("세션 무효화시킴!");
		// logger.debug("loginUser: " + loginUser);

		session.invalidate();

		HashMap<String, Object> content = new HashMap<>();
		content.put("status", "success");
		request.setAttribute("status", "success");
		return content;
	}

	@GetMapping("user")
	public Object user(HttpSession session) throws Exception {

		Member loginUser = (Member) session.getAttribute("loginUser");

		HashMap<String, Object> content = new HashMap<>();

		if (loginUser != null) {
			content.put("status", "success");
			content.put("user", loginUser);
		} else {
			content.put("status", "fail");
		}

		return content;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginGET() {
		return "logintest/login";
	}

	@RequestMapping(value = "loginPostNaver", method = RequestMethod.GET)
	public String loginPOSTNaver(HttpSession session) {
		return "user/loginPostNaver";
	}

	@PostMapping("snslogin")
	public Object snsLogin(String email, int sns_no, String token, HttpSession session, HttpServletRequest request) {
		HashMap<String, Object> content = new HashMap<>();

		if (accessToken(token) == false && sns_no == 1) {
			request.setAttribute("status", "fail");
			content.put("status", "accessTokenFail");
			content.put("message", "올바르지 않는 토큰입니다.");
			return content;
		}

		Member member = memberService.snsget(email, sns_no);
		request.setAttribute("email", email);

		if (member == null) {
			request.setAttribute("status", "fail");
			content.put("status", "fail");
			content.put("message", "이메일이 없거나 암호가 맞지 않습니다.");
		} else {
			request.setAttribute("status", "success");
			session.setAttribute("loginUser", member);
			content.put("status", "success");
		}

		return content;
	}

	@GetMapping("authCheck")
	public Object authCheck(HttpSession session) {

		HashMap<String, Object> content = new HashMap<>();

		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser != null) {
			content.put("status", "success");
			content.put("auth", loginUser.getAuth());
		} else {
			content.put("status", "fail");

		}
		return content;
	}

	public boolean accessToken(String token) {
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		try {
			String apiURL = "https://openapi.naver.com/v1/nid/me";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			if (responseCode == 200) { // 토큰 정상 호출
				return true;
			} else { // 토큰 비정상
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);// 예외 호출
			return false;
		}
	}
}
