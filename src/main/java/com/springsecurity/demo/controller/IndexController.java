package com.springsecurity.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // return view
public class IndexController {

    // localhost:8080/
    // localhost:8080
    @GetMapping({"","/"})
    public String index() {
        return "index"; // a view file.
    }
}

/*
mustache: a template engine that Spring boot recommends officially
basic folder of mustache: src/main/resources/
setting for view resolver: templates(prefix), .mustache(suffix) -> essentially it's already set up
 */