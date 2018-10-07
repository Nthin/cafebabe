package com.xxf.web;

import com.xxf.entity.Wanted;
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
    public List<Wanted> listUntaked() {
        return wantedService.listAllUntaked();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Wanted getDetail(@PathVariable("id") int id) {
        return wantedService.getDetail(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public int addNewWanted(@RequestBody Wanted wanted) {
        return wantedService.addNewWanted(wanted);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/take/{id}", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public int changeWantedStatus(@PathVariable("id") int id, @RequestParam("status") int taked) {
        return wantedService.changeWantedStatus(id, taked);
    }
}
