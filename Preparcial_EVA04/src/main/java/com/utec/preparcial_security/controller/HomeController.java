/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utec.preparcial_security.controller;

/**
 *
 * @author dmeji
 */

import com.utec.preparcial_security.model.User;
import com.utec.preparcial_security.repository.UserRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
    public String login(){
        return "login"; // la pagína html de login
    }
    
    @GetMapping("/home")
    public String home(Model model, Principal principal){
        User usuario = userRepository.findByUsername(principal.getName()).orElse(null);
        model.addAttribute("usuario", usuario);
        return "home";  // la página html de home
    }
}
