package com.xxf.service.impl;

import com.xxf.entity.*;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.AuthService;
import com.xxf.service.WantedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WantedServiceImpl implements WantedService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private WantedMapper wantedMapper;

    private WantedVOMapper wantedVOMapper;

    private UserMapper userMapper;

    private AuthService authService;

    @Autowired
    public WantedServiceImpl(WantedMapper wantedMapper, WantedVOMapper wantedVOMapper, UserMapper userMapper, AuthService authService) {
        this.wantedMapper = wantedMapper;
        this.wantedVOMapper = wantedVOMapper;
        this.userMapper = userMapper;
        this.authService = authService;
    }

    @Override
    public List<Wanted> listAllWanted() {
        return wantedMapper.selectAll();
    }

    @Override
    public List<WantedVO> listAllUntaked(List<Integer> brands, Integer priceHigh) {
        return wantedVOMapper.selectUntakedByFilter(brands, priceHigh);
    }

    @Override
    public List<WantedVO> listAllTaked(int takedUserId) {
        return wantedVOMapper.selectTakedByUserId(takedUserId);
    }

    @Override
    public DetailVO getDetail(int id) {
        Wanted wanted = wantedMapper.selectOne(id);
        Record record = wantedMapper.selectFromRecord(id);
        User user = userMapper.selectOneById(record.getAddUserId());
        Integer takedUserId = record.getTakedUserId();
        if (takedUserId != null) {
            User takedUser = userMapper.selectOneById(record.getTakedUserId());
            return new DetailVO(wanted, user, record.getAddTime(), takedUser, record.getTakedTime());
        }
        return new DetailVO(wanted, user, record.getAddTime());
    }

    @Transactional(rollbackFor = CafeException.class)
    @Override
    public int addNewWanted(int userId, Wanted wanted) {
        if (wantedMapper.insert(wanted) != 1) {
            throw new CafeException("insert into wanted fail, wanted : " + wanted + ", userId : " + userId);
        }
        int wantedId = wanted.getId();
        String addTime = LocalDateTime.now().format(formatter);
        if (wantedMapper.insertRecord(wantedId, userId, addTime) != 1) {
            throw new CafeException("insert into record fail, wanted : " + wanted + ", userId : " + userId);
        }
        log.info("addNewWanted() userId = {}, wanted = {}", userId, wanted);
        return wantedId;
    }

    @Transactional(rollbackFor = CafeException.class)
    @Override
    public void changeWantedStatus(int wantedId, int taked, int takedUserId, Map<String, String> body) {
        if (wantedMapper.update(wantedId, taked) != 1) {
            throw new CafeException("change wanted status fail, wantedId : " + wantedId);
        }
        String takedTime = LocalDateTime.now().format(formatter);
        if (wantedMapper.updateRecord(wantedId, takedUserId, takedTime) != 1) {
            throw new CafeException("update record fail, wantedId : " + wantedId);
        }
        try {
            DetailVO detail = getDetail(wantedId);
            String openId = detail.getUser().getOpenId();
            String formId = detail.getWanted().getFormId();
            authService.sendUniformMsg(openId, formId, wantedId, body);
        } catch (Exception e) {
            throw new CafeException("send uniform msg fail : " + e.getMessage());
        }
        log.info("changeWantedStatus() wantedId = {}, takedUserId = {}", wantedId, takedUserId);
    }
}
