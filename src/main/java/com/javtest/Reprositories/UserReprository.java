package com.javtest.Reprositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javtest.Models.User;

public interface UserReprository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    List<User> findByRole(int i);
}
