package com.dkm.server.service.impl;

import com.dkm.family.dao.FamilyDao;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.server.service.IServerService;
import com.dkm.union.dao.UnionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-19 19:45
 **/
@Service
public class ServerServiceImpl implements IServerService {

    @Resource
    private FamilyDetailDao familyDetailDao;

    @Resource
    private FamilyDao familyDao;

    @Resource
    private UnionMapper unionMapper;

    @Override
    public Map<String, Object> getServerList() {
        Map<String, Object> map = new HashMap<>();
        map.put("users",familyDetailDao.selectCount(null));
        map.put("family",familyDao.selectCount(null));
        map.put("serverName","凌波微光");
        return map;
    }
}
