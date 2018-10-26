package com.xxf.web;

import com.xxf.entity.DetailVO;
import com.xxf.entity.Result;
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
    @GetMapping(value = "")
    public Result listWanted() {
        List<Wanted> wantedList = wantedService.listAllWanted();
        return new Result(wantedList);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/untaked")
    public Result listUntaked() {
        List<WantedVO> wantedVOList = wantedService.listAllUntaked();
        return new Result(wantedVOList);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{brand}/untaked")
    public Result listUntakedByBrand(@PathVariable("brand") int brand) {
        if (brand == 0) {
            return listUntaked();
        }
        List<WantedVO> wantedVOList = wantedService.listUntakedByBrand(brand);
        return new Result(wantedVOList);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Result getDetail(@PathVariable("id") int id) {
        DetailVO detailVO = wantedService.getDetail(id);
        return new Result(detailVO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "/{id}")
    public Result takeWanted(@PathVariable("id") int id) {
        wantedService.changeWantedStatus(id, 1);
        return new Result();
    }
}
