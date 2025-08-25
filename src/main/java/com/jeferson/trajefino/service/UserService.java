package com.jeferson.trajefino.service;

import com.jeferson.trajefino.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(new ArrayList<>() {
        	
		private static final long serialVersionUID = 1L;

		{
            add(new User("Jeferson", "jefersonguerrajr", "1990-01-01"));
            add(new User("Maria", "maria123", "1995-05-15"));
            add(new User("João", "joaozinho", "1988-12-20"));
        }});
    }

}
