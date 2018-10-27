package com.xxf.web;

import com.xxf.entity.Result;
import com.xxf.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{code}")
    public Result login(@PathVariable("code") String code) {
        return new Result(authService.login(code));
    }

}
