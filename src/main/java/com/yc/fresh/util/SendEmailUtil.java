package com.yc.fresh.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendEmailUtil {
	private String sendEmail; // 发件箱
	private String pwd; // 发件箱的授权码
	
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * 发送html格式的邮件
	 * @param receiveEmail
	 * @param nickName
	 * @param code
	 * @return
	 */
	public boolean sendHtmlMail(String receiveEmail, String nickName, String code) {
		if (StringUtil.checkNull(receiveEmail, nickName, code)) {
			return false;
		}
		
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			senderImpl.setHost("smtp.qq.com");  // 邮箱主机
			senderImpl.setDefaultEncoding("utf-8"); // 默认编码集
			
			// 建立邮件内容
			MimeMessage mimeMessage = senderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			
			messageHelper.setTo(receiveEmail); // 设置收件箱
			messageHelper.setFrom(sendEmail); // 设置发件箱
			messageHelper.setSubject("天天生鲜注册中心");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			
			String str = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body><p style='font-size: 20px;font-weight:bold;'>尊敬的："+nickName+"，您好！</p>"
					+ "<p style='text-indent:2em; font-size: 20px;'>欢迎注册天天生鲜网，您本次的注册码是 "
					+ "<span style='font-size:30px;font-weight:bold;color:red'>"+code+"</span>，3分钟之内有效，请尽快使用！</p>"
					+ "<p style='text-align:right; padding-right: 20px;'>"
					+ "<a href='http://www.hyycinfo.com' style='font-size: 18px'>衡阳市源辰信息科技有限公司技术部</a></p>"
					+ "<span style='font-size: 18px; float:right; margin-right: 60px;'>"+sdf.format(new Date())+"</span></body></html>";
			
			messageHelper.setText(str, true); // 设置邮件内容
			
			senderImpl.setUsername(sendEmail); // 发件人
			senderImpl.setPassword(pwd);
			
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); // 让邮件服务器去认证账号和密码
			prop.put("mail.smtp.timeout", "2500"); // 连接超时时间
			
			senderImpl.setJavaMailProperties(prop);
			senderImpl.send(mimeMessage); // 发送邮件
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
