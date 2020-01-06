package com.yc.fresh.mapper;

import java.util.Map;

import com.yc.fresh.entity.AdminInfo;

public interface AdminInfoMapper {
	/**
	 * 管理员登录
	 * @param map
	 * @return
	 */
	public AdminInfo login(Map<String, String> map);
}
