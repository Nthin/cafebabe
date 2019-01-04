package com.xxf.service.impl;

import com.xxf.entity.*;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.WantedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WantedServiceImpl implements WantedService {

    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
        int userId = wantedMapper.selectFromRecord(id);
        User user = userMapper.selectOneById(userId);
        return new DetailVO(wanted, user);
    }

    @Transactional(rollbackFor = CafeException.class)
    @Override
    public void addNewWanted(int userId, Wanted wanted) {
        if (wantedMapper.insert(wanted) != 1) {
            throw new CafeException("insert into wanted fail, wanted : " + wanted + ", userId : " + userId);
        }
        String addTime = sdf.format(new Date());
        if (wantedMapper.insertRecord(wanted.getId(), userId, addTime) != 1) {
            throw new CafeException("insert into record fail, wanted : " + wanted + ", userId : " + userId);
        }
    }

    @Transactional(rollbackFor = CafeException.class)
    @Override
    public void changeWantedStatus(int wantedId, int taked, int takedUserId) {
        if (wantedMapper.update(wantedId, taked) != 1) {
            throw new CafeException("change wanted status fail, wantedId : " + wantedId);
        }
        String takedTime = sdf.format(new Date());
        if (wantedMapper.updateRecord(wantedId, takedUserId, takedTime) != 1) {
            throw new CafeException("update record fail, id : " + wantedId);
        }
    }
}
