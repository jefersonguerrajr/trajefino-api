package com.jeferson.trajefino.service;

import com.jeferson.trajefino.model.User;
import com.jeferson.trajefino.model.UserDTO;
import com.jeferson.trajefino.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<User> saveUser(UserDTO userDto) throws Exception {
        User user = User.builder()
            .userName(userDto.getUserName())
            .name(userDto.getName())
            .birthDate(userDto.getBirthDate())
            .build();

        if(userDto.getUserName().trim().isEmpty()){
            throw new Exception("Nome não pode estar em branco");
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    @Transactional
    public User registerUser(UserDTO userDTO) throws Exception {

        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new Exception("Username já está em uso: " + userDTO.getUserName());
        }
        User user = User.builder()
                .userName(userDTO.getUserName())
                .fullName(userDTO.getFullName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .birthDate(userDTO.getBirthDate())
                .role(userDTO.getRole() != null ? userDTO.getRole() : "ROLE_USER")
                .createdAt(LocalDateTime.now())
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

}
