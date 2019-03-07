package com.xxf.web;

import com.xxf.entity.Result;
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

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 获取openId
     *
     * @param code
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{code}")
    public Result login(@PathVariable("code") String code) {
        AuthResponse resp = authService.login(code);
        return new Result(resp);
    }

}
