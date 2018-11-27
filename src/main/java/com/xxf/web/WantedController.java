package com.xxf.web;

import com.xxf.entity.DetailVO;
import com.xxf.entity.Result;
import com.xxf.entity.Wanted;
import com.xxf.entity.WantedVO;
import com.xxf.service.WantedService;
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
    public Result listUntaked(@QueryParam("by") String by, @QueryParam("value") String value, @QueryParam("gte") Integer gte, @QueryParam("lte") Integer lte) {
        System.out.println("by=" + by +",value="+value + ",gte=" + gte + ",lte=" + lte);
        List<WantedVO> wantedVOList = wantedService.listAllUntaked(by, value, gte, lte);
        return new Result(wantedVOList);
    }

    /**
     * 根据品牌对wanted进行筛选
     * @param brand
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{brand}/untaked")
    public Result listUntakedByBrand(@PathVariable("brand") int brand) {
        List<WantedVO> wantedVOList;
        if (brand == -1) {
            wantedVOList = new ArrayList<>();
//            wantedVOList = wantedService.listAllUntaked();
        } else {
            wantedVOList = wantedService.listUntakedByBrand(brand);
        }
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
