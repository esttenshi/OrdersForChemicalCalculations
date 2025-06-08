package com.javtest.Reprositories; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.javtest.Models.Role; 
 
public interface RoleReprository extends JpaRepository<Role, Integer> { 
}