package com.springsecurity.demo.controller;

import com.springsecurity.demo.model.User;
import com.springsecurity.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // return view
public class IndexController {

    @Autowired // Spring automatically creates the object and performs Dependency Injection (DI)
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // localhost:8080/
    // localhost:8080
    @GetMapping({"","/"})
    public String index() {
        return "index"; // a view file.
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // @GetMapping("/login") doesn't work as we intended bc Spring Security owns the url
    // but after the file SecurityConfig is created, it works as we intended
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm"; // redirect: calls the function
    }

    @Secured("ROLE_ADMIN") // when only one restriction is imposed
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "personal information";
    }

    // usually used when more than one restriction is imposed
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // right before data() method is executed
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "data";
    }
}

/*
mustache: a template engine that Spring boot recommends officially
basic folder of mustache: src/main/resources/
setting for view resolver: templates(prefix), .mustache(suffix) -> essentially it's already set up
 */