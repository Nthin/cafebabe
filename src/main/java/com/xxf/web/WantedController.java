package com.xxf.web;

import com.xxf.common.EnablePaging;
import com.xxf.entity.CafeException;
import com.xxf.entity.DetailVO;
import com.xxf.entity.Result;
import com.xxf.entity.WantedVO;
import com.xxf.service.AuthService;
import com.xxf.service.WantedService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wanted")
public class WantedController {

    private WantedService wantedService;

    private AuthService authService;

    @Autowired
    public WantedController(WantedService wantedService, AuthService authService) {
        this.wantedService = wantedService;
        this.authService = authService;
    }

    /**
     * 获取所有wanted
     *
     * @return
     */
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping(value = "")
//    public Result listWanted() {
//        List<Wanted> wantedList = wantedService.listAllWanted();
//        return new Result(wantedList);
//    }

    /**
     * 获取所有可用wanted
     *
     * @return
     */
    @EnablePaging
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/untaked")
    public Result listUntaked(@QueryParam("brand") String brand, @QueryParam("price") String price, @QueryParam("pageNum") Integer pageNum, @QueryParam("pageSize") Integer pageSize) {
        List<Integer> brandList = null;
        Integer priceHigh = null;
        try {
            if (StringUtils.isNotBlank(brand) && !StringUtils.equalsIgnoreCase(brand, "null")) {
                brand = StringUtils.trim(brand);
                brandList = new ArrayList<>();
                String[] brands = StringUtils.split(brand, ",");
                for (String str : brands) {
                    brandList.add(Integer.parseInt(str.trim()));
                }
            }
        } catch (NumberFormatException nfe) {
            throw new CafeException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Query Param in Wrong Format: " + brand);
        }
        try {
            if (StringUtils.isNotBlank(price) && !StringUtils.equalsIgnoreCase(price, "null")) {
                price = StringUtils.trim(price);
                priceHigh = Integer.parseInt(price);
            }
        } catch (NumberFormatException nfe) {
            throw new CafeException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Query Param in Wrong Format: " + price);
        }
        List<WantedVO> wantedVOList = wantedService.listAllUntaked(brandList, priceHigh);
        return new Result(wantedVOList);
    }

    /**
     * 获取wanted详情
     *
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
     *
     * @param id
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    public Result takeWanted(@PathVariable("id") int id, @RequestBody Map<String, String> body) {
        String takedUserId = body.get("takedUserId");
        if (takedUserId == null) {
            throw new CafeException(HttpStatus.METHOD_NOT_ALLOWED.value(), "Query Param is Empty");
        }
        wantedService.changeWantedStatus(id, 1, Integer.parseInt(takedUserId));
        log.info("taked id = {}, takedUserId = {}", id, takedUserId);
        DetailVO detail = wantedService.getDetail(id);
        String openId = detail.getUser().getOpenId();
        String formId = detail.getWanted().getFormId();
        authService.sendUniformMsg(openId, formId, body);
        return new Result(HttpStatus.CREATED.value());
    }
}
