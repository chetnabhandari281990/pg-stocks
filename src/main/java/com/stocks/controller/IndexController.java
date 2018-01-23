package com.stocks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
 // Method for rendering index page
  @RequestMapping(value="/",method = RequestMethod.GET)
  public String stocks(Model model) {
      return "index";
  }
  
  
  // Method for rendering add stock page
	@RequestMapping(value = "/add",method = RequestMethod.GET)
    public String greetingForm(Model model) {
        return "addStock";
    }

}
