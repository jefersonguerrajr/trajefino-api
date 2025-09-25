package com.jeferson.trajefino.repository;

import com.jeferson.trajefino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
