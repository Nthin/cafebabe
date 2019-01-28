package com.xxf.service.impl;

import com.xxf.entity.*;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.WantedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WantedServiceImpl implements WantedService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private WantedMapper wantedMapper;

    private WantedVOMapper wantedVOMapper;

    private UserMapper userMapper;

    @Autowired
    public WantedServiceImpl(WantedMapper wantedMapper, WantedVOMapper wantedVOMapper, UserMapper userMapper) {
        this.wantedMapper = wantedMapper;
        this.wantedVOMapper = wantedVOMapper;
        this.userMapper = userMapper;
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
    public void addNewWanted(int userId, Wanted wanted) {
        if (wantedMapper.insert(wanted) != 1) {
            throw new CafeException("insert into wanted fail, wanted : " + wanted + ", userId : " + userId);
        }
        int wantedId = wanted.getId();
        String addTime = LocalDateTime.now().format(formatter);
        if (wantedMapper.insertRecord(wantedId, userId, addTime) != 1) {
            throw new CafeException("insert into record fail, wanted : " + wanted + ", userId : " + userId);
        }
    }

    @Transactional(rollbackFor = CafeException.class)
    @Override
    public void changeWantedStatus(int wantedId, int taked, int takedUserId) {
        if (wantedMapper.update(wantedId, taked) != 1) {
            throw new CafeException("change wanted status fail, wantedId : " + wantedId);
        }
        String takedTime = LocalDateTime.now().format(formatter);
        if (wantedMapper.updateRecord(wantedId, takedUserId, takedTime) != 1) {
            throw new CafeException("update record fail, id : " + wantedId);
        }
    }

    @Override
    public List<WantedVO> listAllTaked(int takedUserId) {
        return wantedVOMapper.selectTakedByUserId(takedUserId);
    }
}
