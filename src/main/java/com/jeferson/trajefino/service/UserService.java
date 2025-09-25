package com.jeferson.trajefino.service;

import com.jeferson.trajefino.model.User;
import com.jeferson.trajefino.model.UserDTO;
import com.jeferson.trajefino.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
