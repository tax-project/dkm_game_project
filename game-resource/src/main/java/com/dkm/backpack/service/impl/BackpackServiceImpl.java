package com.dkm.backpack.service.impl;

import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.entity.vo.UserBackpackGoodsVo;
import com.dkm.backpack.service.IBackpackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 21:52
 **/
@Service
public class BackpackServiceImpl implements IBackpackService {

    @Resource
    private BackpackMapper backpackMapper;

    @Override
    public List<UserBackpackGoodsVo> getUserBackpackGoods(Long userId) {
        return backpackMapper.getBackpackGoods(userId);
    }
}
