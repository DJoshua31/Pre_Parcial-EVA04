/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utec.preparcial_security.config;

/**
 *
 * @author dmeji
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // este método hace el mapeo de /uploads/** a la carpeta fisica uploads
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }
}
