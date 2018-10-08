package com.xxf.service.impl;

import com.xxf.entity.DetailVO;
import com.xxf.entity.User;
import com.xxf.entity.Wanted;
import com.xxf.entity.WantedVO;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.WantedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DetailVO getDetail(int id) {
        Wanted wanted = wantedMapper.selectOne(id);
        int userId = wantedMapper.selectFromRecord(id);
        User user = userMapper.selectOne(userId);
        return new DetailVO(wanted, user);
    }

    @Override
    public boolean addNewWanted(Wanted wanted) {
        return wantedMapper.insert(wanted) == 1;
    }

    @Override
    public boolean changeWantedStatus(int id, int taked) {
        return wantedMapper.update(id, taked) == 1;
    }
}
