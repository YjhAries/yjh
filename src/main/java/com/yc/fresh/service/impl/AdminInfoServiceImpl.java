package com.yc.fresh.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.AdminInfo;
import com.yc.fresh.mapper.AdminInfoMapper;
import com.yc.fresh.service.IAdminInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class AdminInfoServiceImpl implements IAdminInfoService {
	@Autowired
	private AdminInfoMapper mapper;

	@Override
	public AdminInfo login(Map<String, String> map) {
		if (StringUtil.checkNull(map.get("account"), map.get("pwd"))) {
			return null;
		}
		return mapper.login(map);
	}

}
