/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utec.preparcial_security.service;

/**
 *
 * @author dmeji
 */

import com.utec.preparcial_security.model.User;
import com.utec.preparcial_security.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        return new org.springframework.security.core.userdetails.User(
        usuario.getUsername(),
        usuario.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole())));
    }
    
}
