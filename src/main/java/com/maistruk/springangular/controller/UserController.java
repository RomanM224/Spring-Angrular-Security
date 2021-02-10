package com.maistruk.springangular.controller;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

//        @PostMapping("/login")
//        public boolean login(@RequestBody User user) {
//            return
//              user.getUsername().equals("user") && user.getPassword().equals("pass");
//        }
        
        @PostMapping("/user")
        public Principal user(HttpServletRequest request) {
            String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
            return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
        }
    }