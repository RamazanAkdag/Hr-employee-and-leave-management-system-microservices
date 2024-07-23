package com.id3.controller;

import com.id3.model.hrPage.CreatePersonnelRequest;
import com.id3.model.hrPage.CreatePersonnelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/ui/hr")
public class HrController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public String getHrPage(Model model){
        model.addAttribute("createPersonnelRequest", new CreatePersonnelRequest());
        return "hr-page";
    }

    @PostMapping("/create-personnel")
    public String createPersonnel(@ModelAttribute CreatePersonnelRequest request, Model model) {
        String url = "http://personnel-info-service/personnel-info";
        try {
            CreatePersonnelResponse response = restTemplate.postForObject(url, request, CreatePersonnelResponse.class);
            if (response != null && "Personel created successfully".equals(response.getMessage())) {
                // If the creation is successful, add a success message to the model
                model.addAttribute("successMessage", "Personnel created successfully!");
            } else {
                // If the creation failed, add an error message to the model
                model.addAttribute("errorMessage", "Failed to create personnel. Please try again.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            model.addAttribute("errorMessage", "An error occurred while creating personnel: " + e.getMessage());
        }

        // Return to the HR page, displaying the form again
        model.addAttribute("createPersonnelRequest", new CreatePersonnelRequest());
        return "hr-page";
    }
}
