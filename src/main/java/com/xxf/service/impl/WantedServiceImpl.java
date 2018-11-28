package com.xxf.service.impl;

import com.xxf.common.Utils;
import com.xxf.entity.*;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.WantedService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WantedServiceImpl implements WantedService {

    private static final String DEFAULT_SEPARATE_CHAR = ",";

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
    public List<WantedVO> listAllUntaked(String by, String value, Integer gte, Integer lte) {
        List<WantedVO> allUntaked = wantedVOMapper.selectUntaked();
        if (StringUtils.isBlank(by)) {
            return allUntaked;
        }
        if (StringUtils.isNotBlank(value)) {
            String[] values = StringUtils.split(value, DEFAULT_SEPARATE_CHAR);
            return allUntaked.stream().filter(wantedVO -> ArrayUtils.contains(values, getWantedVOValueByName(wantedVO, by, String.class))).collect(Collectors.toList());
        }
        if (gte != null || lte != null) {
            return allUntaked.stream().filter(wantedVO -> {
                Integer wantedVOVal = (Integer) getWantedVOValueByName(wantedVO, by, Integer.class);
                if (gte == null) {
                    return wantedVOVal <= lte;
                }
                if (lte == null) {
                    return wantedVOVal >= gte;
                }
                return wantedVOVal >= gte && wantedVOVal <= lte;
            }).collect(Collectors.toList());
        }
        return allUntaked;
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
        if (wantedMapper.insertRecord(wanted.getId(), userId) != 1) {
            throw new CafeException("insert into record fail, wanted : " + wanted + ", userId : " + userId);
        }
    }

    @Override
    public void changeWantedStatus(int id, int taked) {
        if (wantedMapper.update(id, taked) != 1) {
            throw new CafeException("change wanted status fail, id : " + id);
        }
    }

    private Object getWantedVOValueByName(WantedVO wantedVO, String name, Class cls) {
        return ConvertUtils.convert(Utils.getFieldValueByName(name, wantedVO), cls);
    }
}
