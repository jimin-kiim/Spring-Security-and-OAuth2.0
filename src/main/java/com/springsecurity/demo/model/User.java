package com.springsecurity.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity // notating that it's JPA entity. would be mapped with table of db
@Data // Getter, Setter, toString, equals, hashCode can be used
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // using auto-increment
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; // ROLE_USER, ROLE_ADMIN
    @CreationTimestamp
    private Timestamp createDate;
}
