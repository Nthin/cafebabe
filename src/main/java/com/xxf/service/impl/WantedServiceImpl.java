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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class WantedServiceImpl implements WantedService {

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
        List<WantedVO> allUntaked = wantedVOMapper.selectUntaked();
        Predicate<WantedVO> filter = wantedVO -> true;
        if (!brands.isEmpty()) {
            for (int i = 0; i < brands.size(); i++) {
                final int index = i;
                Predicate<WantedVO> brandFilter = wantedVO -> wantedVO.getBrand().equals(brands.get(index));
                if (i == 0) {
                    filter = filter.and(brandFilter);
                } else {
                    filter = filter.or(brandFilter);
                }
            }
        }
        if (priceHigh != null) {
            Predicate<WantedVO> priceFilter = wantedVO -> wantedVO.getPriceHigh() <= priceHigh;
            filter = filter.and(priceFilter);
        }
        return allUntaked.stream()
                .filter(filter)
                .collect(Collectors.toList());
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
}
