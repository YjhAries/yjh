package com.yc.fresh.service;

import java.util.Map;

import com.yc.fresh.entity.AdminInfo;

public interface IAdminInfoService {
	public AdminInfo login(Map<String, String> map);
}
