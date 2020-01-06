package com.yc.fresh.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.mapper.MemberInfoMapper;
import com.yc.fresh.service.IMemberInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class MemberInfoServiceImpl implements IMemberInfoService {
	@Autowired
	private MemberInfoMapper mapper;

	@Override
	public MemberInfo login(Map<String, String> map) {
		if (StringUtil.checkNull(map.get("account"), map.get("pwd"))) {
			return null;
		}
		return mapper.login(map);
	}

	@Override
	public int reg(MemberInfo mf) {
		if (StringUtil.checkNull(mf.getNickName(), mf.getEmail(), mf.getPwd())) {
			return -1;
		} 
		return mapper.reg(mf);
	}

}
