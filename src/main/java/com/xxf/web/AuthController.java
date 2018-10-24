package com.xxf.web;

import com.xxf.entity.auth.AuthResponse;
import com.xxf.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public AuthResponse listWanted(@PathVariable("code") String code) {
        try {
            return authService.login(code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
