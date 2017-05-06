package com.lele.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lele.manager.service.ScoreLevelService;

@Controller
@RequestMapping("/score")
public class ScoreLevelController extends BaseController {

	@Autowired
	ScoreLevelService scoreLevelService;
	
	@RequestMapping(value="/level.json", method = RequestMethod.GET)
	public @ResponseBody
	Object getScoreList(HttpServletRequest request, HttpServletResponse response) {
		
		return scoreLevelService.getAllScoreLevel();
	}
}
