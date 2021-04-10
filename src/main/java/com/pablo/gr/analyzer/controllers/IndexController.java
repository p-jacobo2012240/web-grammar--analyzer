package com.pablo.gr.analyzer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
   
	@GetMapping({"/index", "/", "/#/analyzer" })
	public String index() {  
		return "index.html";
	}
}
