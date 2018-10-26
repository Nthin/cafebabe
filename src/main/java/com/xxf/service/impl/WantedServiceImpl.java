package com.xxf.service.impl;

import com.xxf.entity.*;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.WantedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WantedServiceImpl implements WantedService {

    @Autowired
    private WantedMapper wantedMapper;

    @Autowired
    private WantedVOMapper wantedVOMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Wanted> listAllWanted() {
        return wantedMapper.selectAll();
    }

    @Override
    public List<WantedVO> listAllUntaked() {
        return wantedVOMapper.selectUntaked();
    }

    @Override
    public List<WantedVO> listUntakedByBrand(int brand) {
        return wantedVOMapper.selectUntakedByBrand(brand);
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
            throw new CafeException(400, "insert into wanted fail, wanted : " + wanted + ", userId : " + userId);
        }
        if (wantedMapper.insertRecord(wanted.getId(), userId) != 1) {
            throw new CafeException(400, "insert into record fail, wanted : " + wanted + ", userId : " + userId);
        }
    }

    @Override
    public void changeWantedStatus(int id, int taked) {
        if (wantedMapper.update(id, taked) != 1) {
            throw new CafeException(400, "change wanted status fail, id : " + id);
        }
    }
}
