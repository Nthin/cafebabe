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

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean addNewWanted(Wanted wanted, int userId) {
        if (wantedMapper.insert(wanted) != 1) {
            throw new RuntimeException("insert into wanted fail");
        }
        if (wantedMapper.insertRecord(wanted.getId(), userId) != 1) {
            throw new RuntimeException("insert into record fail");
        }
        return true;
    }

    @Override
    public boolean changeWantedStatus(int id, int taked) {
        return wantedMapper.update(id, taked) == 1;
    }
}
