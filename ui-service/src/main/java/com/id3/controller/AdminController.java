package com.id3.controller;


import com.id3.model.hrPage.CreatePersonnelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;



@Controller
@RequestMapping("/ui/admin")
public class AdminController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public String getAdminPage(Model model){
        model.addAttribute("createPersonnelRequest", new CreatePersonnelRequest());
        return "admin-page";
    }


}
