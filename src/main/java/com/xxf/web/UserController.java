package com.xxf.web;

import com.xxf.entity.User;
import com.xxf.entity.Wanted;
import com.xxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public User getUserInfo(@PathVariable("id") int id) {
        return userService.getUserDetails(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public boolean addNewUser(@RequestBody User user) {
        return userService.newUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/wanted", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Wanted> getAllWanted(@PathVariable("id") int id) {
        return userService.getAllWantedByUserId(id);
    }

}
