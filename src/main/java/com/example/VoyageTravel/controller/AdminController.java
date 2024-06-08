package com.example.VoyageTravel.controller;

import com.example.VoyageTravel.entity.AdminEntity;
import com.example.VoyageTravel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    //admin login form
    @GetMapping("/adminLogin")
    public String adminLogin() {
        return "adminLogin";
    }

    //admin login
    @PostMapping("/login")
    private  String login(@RequestParam("email")String email,@RequestParam("password") String password){
        AdminEntity admin=adminService.getAdminByEmail(email);
        if(admin != null && password.equals(admin.getPassword())){
            return "redirect:/adminDashboard";
        }else{
            return "redirect:/adminLogin?errorLogin";
        }
    }

    //admin signup form
    @GetMapping("/adminSignup")
    public String adminSignup(Model model) {
        model.addAttribute("addAdmin",new AdminEntity());
        return "adminSignUp";
    }

    //saveAdmin
    @PostMapping("/signup")
    private String saveAdmin(@ModelAttribute("addAdmin") AdminEntity adminEntity){
        adminService.saveAdmin(adminEntity);
        return "adminLogin";
    }

}
