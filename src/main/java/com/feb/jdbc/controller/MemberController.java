package com.feb.jdbc.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.feb.jdbc.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 
	 * @return 처음에 jsp페이지 진입하기 위한 용도.
	 */
	@GetMapping("/index.do")
	public ModelAndView index() {
		  ModelAndView mv= new ModelAndView();
		  mv.addObject("resultMsg","indexOk");
		  mv.setViewName("login");
		 return mv;
	}
	/**
	 * 
	 * @param params 로그인 정보 전달  memberId passwd 
	 * @return 
	 */
	@PostMapping("/login.do")
	public ModelAndView login(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		if(memberService.login(params)) {
			mv.addObject("resultMsg","loginOk");
		}else {
			mv.addObject("resultMsg","loginFail");
		}
		return mv;
	}
	/**
	 * 
	 * @param params 가입 정보 전달받음 memberId passwd email 
	 * @return 
	 */
	@PostMapping("/join.do")
	public ModelAndView join(@RequestParam HashMap<String,String> params) {
		ModelAndView mv =  new ModelAndView();
		mv.setViewName("login");
		if(memberService.join(params)) {
			mv.addObject("resultMsg","joinOk");
		}else {
			mv.addObject("resultMsg","joinFail");
		}
		return mv;
	}
	
	// 서버 리스타트하고 콘트럴로 확이 	
}
