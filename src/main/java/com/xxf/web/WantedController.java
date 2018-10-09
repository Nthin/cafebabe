package com.xxf.web;

import com.xxf.entity.DetailVO;
import com.xxf.entity.Wanted;
import com.xxf.entity.WantedVO;
import com.xxf.service.WantedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wanted")
public class WantedController {

    @Autowired
    private WantedService wantedService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Wanted> listWanted() {
        return wantedService.listAllWanted();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/untaked", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<WantedVO> listUntaked() {
        return wantedService.listAllUntaked();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public DetailVO getDetail(@PathVariable("id") int id) {
        return wantedService.getDetail(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public boolean addNewWanted(@RequestBody Wanted wanted, @RequestParam("userId") int userId) {
        return wantedService.addNewWanted(wanted, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/take/{id}", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public boolean changeWantedStatus(@PathVariable("id") int id, @RequestParam("status") int taked) {
        return wantedService.changeWantedStatus(id, taked);
    }
}
