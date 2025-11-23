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
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Carpeta donde se guardarán las imagenes
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    @GetMapping("/perfil")
    public String perfil(Model model, Principal principal,
                         @RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "success", required = false) String success) {
        User usuario = userRepository.findByUsername(principal.getName()).orElse(null);
        model.addAttribute("usuario", usuario);
        if (error != null) {
            if ("size".equals(error)) {
                model.addAttribute("errorMessage", "La imagen es demasiado grande. Tamaño máximo: 2 MB.");
            } else {
                model.addAttribute("errorMessage", "Error al subir la imagen.");
            }
        }
        if (success != null) {
            model.addAttribute("successMessage", "Perfil actualizado correctamente.");
        }
        return "perfil";    // perfil.html
    }
    
    @PostMapping("/perfil")
    public String actualizarPerfil(@RequestParam("email") String email,
                                    @RequestParam("fechaNacimiento") String fechaNacimiento,
                                    @RequestParam("password") String password,
                                    @RequestParam(value = "imagen", required = false) MultipartFile imagen,
                                    Principal principal) throws IOException {
        User usuario = userRepository.findByUsername(principal.getName()).orElse(null);
        if (usuario == null) {
            return "redirect:/login";
        }
        
        usuario.setEmail(email);
        
        if (fechaNacimiento !=null && !fechaNacimiento.isBlank()) {
            usuario.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        }
        // En caso que el usuario escriba una nueva contraseña
        if (password != null && !password.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }

        // Validación y guardado de imagen (si se sube)
        long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB
        if (imagen != null && !imagen.isEmpty()) {
            if (imagen.getSize() > MAX_FILE_SIZE) {
                return "redirect:/perfil?error=size";
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = principal.getName() + "_" + imagen.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(imagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            usuario.setNombreImagen(filename);
        }
        
        userRepository.save(usuario);
        
        return "redirect:/perfil?success";
    }
}
