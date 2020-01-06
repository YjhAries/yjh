package com.yc.fresh.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yc.fresh.entity.GoodsType;
import com.yc.fresh.service.IGoodsTypeService;
import com.yc.fresh.util.StringUtil;

@RestController
@RequestMapping("/type")
public class GoodsTypeController {
	@Autowired
	private IGoodsTypeService goodsTypeService;
	
	@RequestMapping("/add")
	public int add(String tname, MultipartFile photos, HttpServletRequest  request) {
		if (photos.isEmpty()) { // 说明没有选择商品类型的图片
			return -1;
		}
		
		if (StringUtil.checkNull(tname)) { // 
			return -1;
		}
		
		try {
			String savePath = "pics"; 
			
			String path = request.getServletContext().getRealPath("");
			String temp = request.getServletContext().getInitParameter("uploadPath");
			if (!StringUtil.checkNull(temp)) {
				savePath = temp;
			}
			                                              // 获取用户上传的文件代的名称
			savePath += "/" + new Date().getTime() + "_" + photos.getOriginalFilename(); // 重命名文件名，以免同名而造成文件覆盖
			
			File dest = new File(new File(path).getParentFile(), savePath); // 将文件上传保存到服务器的这个文件中
			
			// 保存文件到服务器
			photos.transferTo(dest);
			
			// 将类型信息存到数据库中
			return goodsTypeService.add(new GoodsType(tname, savePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping("finds")
	public List<GoodsType> finds() {
		return goodsTypeService.finds();
	}
}
