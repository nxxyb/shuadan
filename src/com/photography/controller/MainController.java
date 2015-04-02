package com.photography.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author 徐雁斌
 * @since 2015-2-4
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController{
	
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request, Model model) {
		return "index";
	}
}
