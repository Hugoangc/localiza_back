package com.practice.localiza.auth;

import java.util.Optional;

import com.practice.localiza.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.localiza.entity.User;
public interface LoginRepository extends JpaRepository<User, Long>{
    public Optional<User> findByUsername(String login);
}
