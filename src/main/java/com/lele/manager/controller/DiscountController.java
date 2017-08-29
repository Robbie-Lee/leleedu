package com.lele.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lele.manager.annotation.Auth;
import com.lele.manager.annotation.Auth.AuthType;
import com.lele.manager.entity.Discount;
import com.lele.manager.service.DiscountService;
import com.lele.manager.sys.dao.Pagination;
import com.lele.manager.utils.CommonResult;

@Controller
@RequestMapping("/discount")
public class DiscountController extends BaseController {

	@Autowired
	DiscountService discountService;
	
	@Auth(auth=AuthType.PAGE, description="折扣配置页面")
	@RequestMapping(value="/discount.do", method = RequestMethod.GET)
	public ModelAndView discount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Pagination<Discount> discountPage = discountService.getDiscountInfo(0, 20);
		
		ModelAndView mv = new ModelAndView("discount/manager");
		mv.addObject("discountInfo", discountPage);
		
		return mv;
	}
	
	@Auth(auth=AuthType.INTERFACE, description="折扣查询接口")
	@RequestMapping(value={"morediscount.json"}, method = RequestMethod.GET)
	public @ResponseBody
	Object getDiscount(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "curPage", required = false, defaultValue = "0") int curPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
		return discountService.getDiscountInfo(curPage, pageSize);
	}
	
	@Auth(auth=AuthType.INTERFACE, description="折扣保存接口")
	@RequestMapping(value={"savediscount.json"}, method = RequestMethod.POST)
	public @ResponseBody
	Object saveDiscount(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "lowerFee", required = true) int lowerFee,
			@RequestParam(value = "upperFee", required = true) int upperFee,
			@RequestParam(value = "discountRate", required = true) float discountRate) {
		
		int result = discountService.saveDiscount(lowerFee, upperFee, discountRate);

		CommonResult cr = new CommonResult();
		if (result == -1) {
			cr.setResult("failed");
		    cr.setErrCode("输入不正确");
		}
		else if (result == -2) {
		    cr.setResult("failed");
		    cr.setErrCode("该折扣已经存在");
		}
		else if (result == -3) {
		    cr.setResult("failed");
		    cr.setErrCode("该折扣与其他折扣有重叠");
		}
		else {
			cr.setResult("success");
			cr.setErrCode("保存成功");
		}
		
		return cr;
		
	}
}
