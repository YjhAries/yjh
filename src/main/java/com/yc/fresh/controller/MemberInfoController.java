package com.yc.fresh.controller;

import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.service.IMemberInfoService;
import com.yc.fresh.util.SendEmailUtil;

@RestController  // 相当于@Controller + @ResponseBody  即说明这个控制器中的所有方法，默认都是以json格式返回数据
@RequestMapping("/member")
public class MemberInfoController {
	@Autowired
	private SendEmailUtil sendEmailUtil;
	
	@Autowired
	private IMemberInfoService memberInfoService;

	@RequestMapping("/code")
	public int sendCode(String email, String nickName, HttpSession session) {
		String code = "";
		Random rd = new Random();
		while (code.length() < 6) {
			code += rd.nextInt(10);
		}
		
		// 发送验证码到邮箱
		if (sendEmailUtil.sendHtmlMail(email, nickName, code)) { // 说明验证码发送成功
			// 将验证码存到session中
			session.setAttribute("code", code);
			
			// 启用一个定时任务，3分钟后清空
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					session.removeAttribute("code");
				}
			};
			
			Timer timer = new Timer();
			timer.schedule(task, 180000); // 3分钟后执行task任务一次
			return 1;
		}
		return 0;
	}
	
	@RequestMapping("/reg")
	public int reg(MemberInfo mf, HttpSession session) {
		Object obj = session.getAttribute("code");
		if (obj == null) {
			return -2; // 说明验证码过期
		}
		
		String code = (String) obj;
		if (!code.equals(mf.getRealName())) { // 注意：前台注册的时候将验证码暂存到真实姓名属性中
			return -3; // 验证码错误
		}
		
		return memberInfoService.reg(mf);
	}
	
	@RequestMapping("/login")
	public int login(HttpSession session, @RequestParam Map<String, String> map) {
		String code = map.get("code"); // 获取用户输入的验证码
		String vcode = (String) session.getAttribute("vcode"); // 从session中获取生成的验证码
		
		if (!code.equalsIgnoreCase(vcode)) {
			return -2; // 验证码错误
		}
		
		MemberInfo mf = memberInfoService.login(map);
		if (mf == null) {
			return -1;
		}
		
		session.setAttribute("currenrLoginUser", mf);
		return 1;
	}
	
	@RequestMapping("/check")
	public MemberInfo check(HttpSession session) {
		Object obj = session.getAttribute("currenrLoginUser");
		if (obj == null) {
			return new MemberInfo();
		}
		return (MemberInfo) obj;
	}
}
