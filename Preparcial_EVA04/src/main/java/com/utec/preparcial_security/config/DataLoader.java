/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utec.preparcial_security.config;

/**
 *
 * @author dmeji
 */

import com.utec.preparcial_security.model.User;
import com.utec.preparcial_security.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User u = new User();
                u.setUsername("admin");
                u.setPassword(encoder.encode("12345")); // contraseña
                u.setEmail("admin@correo.com");
                u.setFechaNacimiento(LocalDate.of(2000, 1, 1)); // El formato es año,mes,día
                u.setRole("ROLE_USER");
                userRepository.save(u);
            }
        };
    }
}
