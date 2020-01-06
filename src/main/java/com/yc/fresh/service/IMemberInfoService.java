package com.yc.fresh.service;

import java.util.Map;

import com.yc.fresh.entity.MemberInfo;

public interface IMemberInfoService {
	/**
	 * 会员登录
	 * @param map
	 * @return
	 */
	public MemberInfo login(Map<String, String> map);
	
	/**
	 * 注册
	 * @param mf
	 * @return
	 */
	public int reg(MemberInfo mf);
}
