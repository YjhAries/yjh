package com.yc.fresh.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.fresh.entity.AdminInfo;
import com.yc.fresh.service.IAdminInfoService;

@Controller  // 相当于@Controller + @ResponseBody  即说明这个控制器中的所有方法，默认都是以json格式返回数据
@RequestMapping("/back")
public class AdminInfoController {
	@Autowired
	private IAdminInfoService adminInfoService;
	
	@RequestMapping("/login")
	@ResponseBody
	public int login(HttpSession session, @RequestParam Map<String, String> map) {
		AdminInfo af = adminInfoService.login(map);
		if (af == null) {
			return -1;
		}
		session.setAttribute("currenrLoginAdmin", af);
		return 1;
	}
	
	@RequestMapping("/idx")
	public String idex(HttpSession session) {
		if (session.getAttribute("currenrLoginAdmin") == null) {
			return "redirect:/back/index.html";
		}
		return "forward:/WEB-INF/manager/index.jsp";
	}
}
