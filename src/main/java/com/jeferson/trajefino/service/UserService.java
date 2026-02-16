package com.jeferson.trajefino.service;

import com.jeferson.trajefino.model.User;
import com.jeferson.trajefino.model.dto.UserDTO;
import com.jeferson.trajefino.model.enums.UserRole;
import com.jeferson.trajefino.repository.UserRepository;
import com.jeferson.trajefino.exception.ResourceNotFoundException;
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

    public ResponseEntity<User> editUser(UserDTO userDto, long id) throws Exception {
        if(userDto.getId() == null || userDto.getId() <= 0){
            throw new Exception("ID do usuário é obrigatório para edição");
        }
        User user = User.builder()
                .id(id)
                .userName(userDto.getUserName())
                .name(userDto.getName())
                .birthDate(userDto.getBirthDate())
                .build();
        return ResponseEntity.ok(userRepository.save(user));
    }

    public ResponseEntity<User> saveUser(UserDTO userDto) throws Exception {
        if(userDto.getUserName() == null || userDto.getUserName().trim().isEmpty()){
            throw new Exception("Username é obrigatório");
        }
        if(userDto.getFullName() == null || userDto.getFullName().trim().isEmpty()){
            throw new Exception("Nome completo é obrigatório");
        }
        if(userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()){
            throw new Exception("Senha é obrigatória");
        }

        User user = User.builder()
            .userName(userDto.getUserName())
            .name(userDto.getName())
            .fullName(userDto.getFullName())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .birthDate(userDto.getBirthDate())
            .role(userDto.getRole() != null ? userDto.getRole() : UserRole.ROLE_CUSTOMER)
            .createdAt(LocalDateTime.now())
            .enabled(true)
            .build();
        return ResponseEntity.ok(userRepository.save(user));
    }

    @Transactional
    public ResponseEntity<User> partialUpdateUser(Long id, UserDTO userDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // Atualiza apenas os campos que foram fornecidos (não nulos)
        if (userDTO.getUserName() != null && !userDTO.getUserName().trim().isEmpty()) {
            user.setUserName(userDTO.getUserName());
        }
        if (userDTO.getName() != null && !userDTO.getName().trim().isEmpty()) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getFullName() != null && !userDTO.getFullName().trim().isEmpty()) {
            user.setFullName(userDTO.getFullName());
        }
        if (userDTO.getBirthDate() != null && !userDTO.getBirthDate().trim().isEmpty()) {
            user.setBirthDate(userDTO.getBirthDate());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
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
                .role(userDTO.getRole() != null ? userDTO.getRole() : UserRole.ROLE_CUSTOMER)
                .createdAt(LocalDateTime.now())
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

}
