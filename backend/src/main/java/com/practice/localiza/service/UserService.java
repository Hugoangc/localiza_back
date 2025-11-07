package com.practice.localiza.service;

import com.practice.localiza.entity.User;
import com.practice.localiza.entity.User; // Este import não é mais necessário aqui, mas não causa mal
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.localiza.auth.Login;
import com.practice.localiza.auth.LoginRepository;

@Service
public class UserService {

    @Autowired
    private LoginRepository loginRepository; //

    @Autowired
    private PasswordEncoder passwordEncoder; //

//    @Transactional
//    public User updateRole(Long userId, String newRole) {
//        User userToUpdate = loginRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
//        userToUpdate.setRole(newRole);
//        return loginRepository.save(userToUpdate);
//    }
    @Transactional
    public User updateRole(Long userId, String newRole) { // <-- Mude para User
        User userLoginToUpdate = loginRepository.findById(userId) // <-- Mude para User
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        userLoginToUpdate.setRole(newRole);
        return loginRepository.save(userLoginToUpdate);
    }

    public User registerUser(Login loginDTO) {

        if (loginRepository.findByUsername(loginDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Erro: Nome de usuário já está em uso!");
        }
        User novoUser = new User();
        novoUser.setUsername(loginDTO.getUsername());

        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());
        novoUser.setPassword(encodedPassword);

        novoUser.setRole("USER");

        return loginRepository.save(novoUser);
    }
}