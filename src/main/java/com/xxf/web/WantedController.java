package com.xxf.web;

import com.xxf.entity.*;
import com.xxf.service.WantedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wanted")
public class WantedController {

    private WantedService wantedService;

    @Autowired
    public WantedController(WantedService wantedService) {
        this.wantedService = wantedService;
    }

    /**
     * 获取所有wanted
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    public Result listWanted() {
        List<Wanted> wantedList = wantedService.listAllWanted();
        return new Result(wantedList);
    }

    /**
     * 获取所有可用wanted
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/untaked")
    public Result listUntaked(@QueryParam("brand") String brand, @QueryParam("price") String price) {
        List<Integer> brandList = new ArrayList<>();
        Integer priceHigh = null;
        try {
            if (StringUtils.isNotBlank(brand)) {
                brand = StringUtils.trim(brand);
                String[] brands = StringUtils.split(brand, ",");
                for (String str : brands) {
                    brandList.add(Integer.parseInt(str.trim()));
                }
            }
            if (StringUtils.isNotBlank(price)) {
                price = StringUtils.trim(brand);
                priceHigh = Integer.parseInt(price);
            }
        } catch (NumberFormatException nfe) {
            throw new CafeException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Query Param in Wrong Format");
        }
        List<WantedVO> wantedVOList = wantedService.listAllUntaked(brandList, priceHigh);
        return new Result(wantedVOList);
    }

    /**
     * 获取wanted详情
     * @param id
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public Result getDetail(@PathVariable("id") int id) {
        DetailVO detailVO = wantedService.getDetail(id);
        return new Result(detailVO);
    }

    /**
     * 将wanted的状态改为taked
     * @param id
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    public Result takeWanted(@PathVariable("id") int id) {
        wantedService.changeWantedStatus(id, 1);
        return new Result(HttpStatus.CREATED.value());
    }
}
