package com.springsecurity.demo.repository;

import com.springsecurity.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository holds basic CRUD functions
// without the @Repository annotation, IoC works fine since it inherits JpaRepository
// automatically registered as Bean
public interface UserRepository extends JpaRepository<User, Integer> {
}
