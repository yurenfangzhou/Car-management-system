package com.oracle.handler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.service.PartsBillService;
import com.oracle.vo.PartsRepBill;

@Controller
@RequestMapping("pages/partssys/partsrepbill/")
public class PartsBillHandler {

	@Autowired
	PartsBillService service;

	@RequestMapping("/partsrepbilllist/{start}")
	public String partsrepbilllist(Map<String, Object> map, @PathVariable("start") Integer start) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		PageHelper.startPage(start, 5);

		list = service.getAll();		

		PageInfo<Map<String, Object>> info = new PageInfo<Map<String, Object>>(list);

		map.put("pageInfo", info);

		return "/pages/partssys/partsrepbill/partsrepbilllist";
	}

	@RequestMapping("/jump")
	public String jump(@RequestParam("num") int num) {

		return "redirect:/pages/partssys/partsrepbill/partsrepbilllist/" + num;
	}

	@RequestMapping(value="/getJsons",produces="application/json;charset=UTF-8")
	@ResponseBody
	public List<Map<String,Object>> getJsons(String inout) {
	
		return service.getJson(inout);
	}
	
	@RequestMapping("/getBills")
	public String getBills(String billtype,String partsname,Date billtime){
		
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("billtype",billtype);
		map.put("partsname",partsname);
		map.put("billtime",billtime);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		//PageHelper.startPage(1, 5);

		list = service.getBillsByLike(map);
		
		System.out.println(list);

		//PageInfo<Map<String, Object>> info = new PageInfo<Map<String, Object>>(list);

		map.put("pageInfo",list);
		
		return "pages/partssys/partsrepbill/partsrepbilllist2";
	}
	
}
