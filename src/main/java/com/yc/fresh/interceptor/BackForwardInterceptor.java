package com.yc.fresh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台子页面内部转发拦截器
 * 源辰信息
 * @author navy
 * @date 2019年12月1日
 */
public class BackForwardInterceptor implements HandlerInterceptor {
	/**
	 * 视图显示之后，一般用来释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	
	}
	
	/**
	 * 访问了控制器，在调用视图显示之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {
	
	}
	
	/**
	 * 在访问控制器之前执行，如果返回值为false，则不会访问控制器，请求到此结束
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 我这里是拦截后要做内部转发
		String path = request.getServletPath();
		path = path.substring(path.lastIndexOf("/") + 1); // 拿到用户要访问的页面
		request.getRequestDispatcher("/WEB-INF/manager/" + path).forward(request, response); // 内部转发到指定的页面
		return false;
	}
}
