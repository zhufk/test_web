package com.zfk.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zfk.base.util.StringUtils;

@Controller
@RequestMapping("/")
public class LoginController {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "initLogin" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String initLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "login";
	}

	@RequestMapping(value = { "login" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String login(HttpServletRequest request, @RequestParam("username") String username,
			@RequestParam("password") String password, ModelMap map) {
		
		Cookie cookie = new Cookie(name, value)

			this.LOGGER.info("login : loginName={}, sessionId={}, ip={}",
					new Object[] { username, currenUser.getSession().getId(), request.getRemoteAddr() });

			result = "redirect:index";
		} catch (UnknownAccountException uae) {
			throw new WebMsgException(HttpStatus.OK.value(), "用户不存在！", "login");
		} catch (IncorrectCredentialsException ice) {
			throw new WebMsgException(HttpStatus.OK.value(), "登录密码不正确！", "login");
		} catch (AuthenticationException ae) {
			this.LOGGER.error("login error", ae);
		}

		if (StringUtils.isNotBlank(requestUrl)) {
			result = "redirect:" + requestUrl;
		}
		return result;
	}

	@RequestMapping({ "logout" })
	public String logout(HttpServletRequest request) {
		this.LOGGER.info("logout : loginName={},sessionId={}, ip={}",
				new Object[] { UserUtils.getLoginName(), request.getSession().getId(), request.getRemoteAddr() });

		UserInfo userInfo = new UserInfo();
		UserInfo user = UserUtils.getUserInfo();
		userInfo.setId(user.getId());
		userInfo.setUserId(user.getUserId());
		userInfo.setLoginTime(new Date());
		((UserService) SpringUtils.getBean(UserService.class)).update(userInfo);

		UserUtils.getCurrency().logout();

		return "redirect:initLogin";
	}
}
