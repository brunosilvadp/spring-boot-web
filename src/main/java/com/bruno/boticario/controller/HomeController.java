package com.bruno.boticario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bruno.boticario.model.Client;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("page", "home/index");
		model.addAttribute("currentPage", "home");
		
		return "base/app";
	}
}
