package com.example.VoyageTravel.controller;

import com.example.VoyageTravel.entity.ContactEntity;
import com.example.VoyageTravel.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GeneralController {

    @Autowired
    private ContactService contactService;


    // Home page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // about page
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Contact page
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("createContact",new ContactEntity());
        return "contactUs";
    }

    @PostMapping("/saveContact")
    public String saveContactForm(@ModelAttribute("createContact") ContactEntity contactEntity){
        contactService.saveContact(contactEntity);
        return "redirect:/";
    }

}
