package com.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // return view
public class IndexController {

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

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "SignUp Completed";
    }
}

/*
mustache: a template engine that Spring boot recommends officially
basic folder of mustache: src/main/resources/
setting for view resolver: templates(prefix), .mustache(suffix) -> essentially it's already set up
 */