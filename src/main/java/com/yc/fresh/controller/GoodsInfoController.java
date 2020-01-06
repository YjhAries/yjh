package com.yc.fresh.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yc.fresh.entity.GoodsInfo;
import com.yc.fresh.service.IGoodsInfoService;
import com.yc.fresh.util.StringUtil;

@RestController
@RequestMapping("/goods")
public class GoodsInfoController {
	@Autowired
	private IGoodsInfoService goodsInfoService;

	@RequestMapping("/upload")
	public Map<String, Object> upload(@RequestParam("upload") MultipartFile photos, HttpServletRequest  request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
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
			
			map.put("fileName", photos.getOriginalFilename());
			map.put("uploaded", 1);
			map.put("url", "../../../" + savePath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/add")
	public int add(MultipartFile[] pic, HttpServletRequest  request, GoodsInfo gf) {
		if (pic != null && pic.length >0 && !pic[0].isEmpty()) { // 说明有图片
			try {
				String savePath = "pics"; 
				
				String basepath = request.getServletContext().getRealPath("");
				String temp = request.getServletContext().getInitParameter("uploadPath");
				if (!StringUtil.checkNull(temp)) {
					savePath = temp;
				}
				
				String picStr = "";
				File dest = null;
				String path = null;
				
				for (MultipartFile fl : pic) {
					path = savePath + "/" + new Date().getTime() + "_" + fl.getOriginalFilename(); // 重命名文件名，以免同名而造成文件覆盖
					dest = new File(new File(basepath).getParentFile(), path); // 将文件上传保存到服务器的这个文件中
					// 保存文件到服务器
					fl.transferTo(dest);
					picStr += path + ";";
				}
				
				if (!"".equals(picStr)) {
					picStr = picStr.substring(0, picStr.lastIndexOf(";"));
				}
				
				gf.setPics(picStr);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return goodsInfoService.add(gf);
	}
	
	@RequestMapping("/finds")
	public Map<String, Object> finds() {
		return goodsInfoService.finds();
	}
	
	@RequestMapping("/findByGno")
	public GoodsInfo findByGno(Integer gno) {
		return goodsInfoService.findByGno(gno);
	}
	
	@RequestMapping("/findByFirst")
	public Map<String, Object> findByFirst(Integer page, Integer rows, Integer tno) {
		return goodsInfoService.findByFirst(page, rows, tno);
	}
	
	@RequestMapping("/findByPage")
	public List<GoodsInfo> findByPage(Integer page, Integer rows, Integer tno) {
		return goodsInfoService.findByPage(page, rows, tno);
	}
}
