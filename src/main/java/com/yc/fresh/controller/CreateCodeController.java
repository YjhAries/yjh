package com.yc.fresh.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class CreateCodeController {
	
	@RequestMapping("/code")
	public void createCode(HttpSession session, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		String code = getRandomCode(); // 获取生成的验证码
		session.setAttribute("vcode", code);
		
		// 生成验证码图片
		BufferedImage image = getCodeImage(code, 70, 36);
		
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	/**
	 * 生成验证码图片的方法
	 * @param code
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @return
	 */
	private BufferedImage getCodeImage(String code, int width, int height) {
		// 创建图片对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// 绘制图片内容
		// 获取图片绘制工具
		Graphics g = image.getGraphics();
		
		// 设置绘制的颜色
		g.setColor(getRandomColor(210, 40)); // 0 ~ 255
		
		// 绘制背景色
		g.fillRect(0, 0, width, height);
		
		// 绘制干扰线
		Random rd = new Random();
		int x1, y1, x2, y2;
		for (int i = 0; i < 50; i ++) {
			x1 = rd.nextInt(width);
			y1 = rd.nextInt(height);
			x2 = rd.nextInt(width);
			y2 = rd.nextInt(height);
			
			// 设置干扰线的颜色
			g.setColor(getRandomColor(140, 60));
			
			// 绘制干扰线
			g.drawLine(x1, y1, x2, y2);
		}
		
		// 绘制验证码
		// 设置验证码的字体
		g.setFont(new Font("Courier New", Font.ITALIC, 22));
		
		// 绘制验证码
		char[] chs = code.toCharArray();
		for (int i = 0, len = chs.length; i < len; i ++) {
			// 设置验证码的颜色
			g.setColor(getRandomColor(30, 90));
			
			// 绘制验证码
			g.drawString(String.valueOf(chs[i]), 8 + 14*i, 23);
		}
		
		g.dispose();
		
		return image;
	}
	
	/**
	 * 生成随机颜色
	 * @param start 颜色的开始值
	 * @param bound 范围
	 * @return
	 */
	private Color getRandomColor(int start, int bound) {
		Random rd = new Random();
		int r = start + rd.nextInt(bound);
		int g = start + rd.nextInt(bound);
		int b = start + rd.nextInt(bound);
		return new Color(r, g, b);
	}

	private String getRandomCode() {
		Random rd = new Random();
		StringBuffer sbf = new StringBuffer();
		int flag = 0;
		for (int i = 0; i < 4; i ++) {
			flag = rd.nextInt(3);
			switch (flag) {
			case 0: sbf.append(rd.nextInt(10)); break;
			case 1: sbf.append( (char)(rd.nextInt(26) + 65)); break;
			case 2: sbf.append( (char)(rd.nextInt(26) + 97)); break;
			}
		}
		return sbf.toString();
	}
}
