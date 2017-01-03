package com.mullet.backend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataTablesController {

	@RequestMapping("/datatables/language/zh")
	public @ResponseBody Map<String,Object> editUserStatus(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sProcessing", "处理中...");
		map.put("sLengthMenu", "每页 _MENU_ 项");
		map.put("sZeroRecords", "没有检索到数据");
		map.put("sInfo", "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项");
		map.put("sInfoEmpty", "显示第 0 至 0 项结果，共 0 项");
		map.put("sInfoFiltered", "(由 _MAX_ 项结果过滤)");
		map.put("sInfoPostFix", "");
		map.put("sSearch", "搜索:");
		map.put("sUrl", "");
		map.put("sEmptyTable", "数据为空");
		map.put("sInfoThousands", ",");
		map.put("sLoadingRecords", "加载中...");
		
		Map<String,String> pagemap = new HashMap<String,String>();
		pagemap.put("sFirst", "首页");
		pagemap.put("sPrevious", "上一页");
		pagemap.put("sNext", "下一页");
		pagemap.put("sLast", "末页");
		map.put("oPaginate", pagemap);

		Map<String,String> ordermap = new HashMap<String,String>();
		ordermap.put("sSortAscending", "升序排列");
		ordermap.put("sSortDescending", "降序排列");
		map.put("oAria", ordermap);
		
		
		
		return map;
	}

}
