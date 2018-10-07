package com.xxf.service.impl;

import com.xxf.entity.DetailVO;
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
        return null;
//        return wantedMapper.selectOne(id);
    }

    @Override
    public int addNewWanted(Wanted wanted) {
        return wantedMapper.insert(wanted);
    }

    @Override
    public int changeWantedStatus(int id, int taked) {
        return wantedMapper.update(id, taked);
    }
}
