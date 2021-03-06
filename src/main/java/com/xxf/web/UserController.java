package com.xxf.web;

import com.xxf.common.EnablePaging;
import com.xxf.entity.Result;
import com.xxf.entity.User;
import com.xxf.entity.Wanted;
import com.xxf.entity.WantedVO;
import com.xxf.service.UserService;
import com.xxf.service.WantedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private WantedService wantedService;

    @Autowired
    public UserController(UserService userService, WantedService wantedService) {
        this.userService = userService;
        this.wantedService = wantedService;
    }

    /**
     * 根据openId获取用户信息
     *
     * @param openId
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{openId}")
    public Result getUserInfo(@PathVariable("openId") String openId) {
        User user = userService.getUserDetails(openId);
        return new Result(user);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "")
    public Result addNewUser(@RequestBody User user) {
        userService.newUser(user);
        return new Result(HttpStatus.CREATED.value());
    }

    /**
     * 根据用户id获取该用户的所有wanted
     *
     * @param id
     * @return
     */
    @EnablePaging
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/wanted")
    public Result getAllWanted(@PathVariable("id") int id, @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
        List<WantedVO> wantedVOList = userService.getAllWantedByUserId(id);
        return new Result(wantedVOList);
    }

    /**
     * 根据用户id获取该用户的所有可用wanted
     *
     * @param id
     * @return
     */
    @EnablePaging
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/untaked")
    public Result getAllUntaked(@PathVariable("id") int id, @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
        List<WantedVO> wantedList = userService.getAllUntakedByUserId(id);
        return new Result(wantedList);
    }

    /**
     * 根据用户id获取该用户的所有历史wanted
     *
     * @param id
     * @return
     */
    @EnablePaging
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/taked")
    public Result getAllTaked(@PathVariable("id") int id, @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
        List<WantedVO> wantedList = userService.getAllTakedByUserId(id);
        return new Result(wantedList);
    }

    /**
     * 新增wanted
     *
     * @param id
     * @param wanted
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{id}")
    public Result addNewWanted(@PathVariable("id") int id, @RequestBody Wanted wanted) {
        int wantedId = wantedService.addNewWanted(id, wanted);
        return new Result(HttpStatus.CREATED.value(), wantedId);
    }

    /**
     * 取消wanted的taked状态，重新释放到可用list中
     *
     * @param userId
     * @param wantedId
     * @return
     */
//    @ResponseStatus(HttpStatus.CREATED)
//    @PutMapping(value = "/{userId}/{wantedId}")
//    public Result untakeWanted(@PathVariable("userId") int userId, @PathVariable("wantedId") int wantedId) {
//        wantedService.changeWantedStatus(wantedId, 0, userId);
//        return new Result(HttpStatus.CREATED.value());
//    }

    /**
     * 补全用户信息
     *
     * @param id
     * @param body
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{id}/complete")
    public Result completeUserInfo(@PathVariable("id") int id, @RequestBody Map<String, String> body) {
        userService.updateUser(id, body.get("position"), body.get("phone"));
        return new Result(HttpStatus.CREATED.value());
    }
}
