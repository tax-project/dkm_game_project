package com.dkm.seed.services.impl;

import com.dkm.seed.entities.bo.SeedEntity;
import com.dkm.seed.entities.vo.SeedVo;
import com.dkm.seed.mappers.SeedMapper;
import com.dkm.seed.services.ISeedService;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.bean.ResultVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;


import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        rollbackFor = {Exception.class}
)

public class SeedServiceImpl implements ISeedService {
    @Resource
    private SeedMapper seedMapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     *  查询数据库的所有种子
     * @return
     */
    @Override
    @NotNull
    public List<SeedVo> getAll() {
        //查询所有种子
        val list = seedMapper.selectAll();
        val result = new ArrayList<SeedVo>();
        for (SeedEntity entity : list) {
            //将查询的种子加进集合中
            result.add(new SeedVo(String.valueOf(entity.getSeedId()), entity.getSeedName(), entity.getSeedGrade(), entity.getSeedProdgold(), entity.getSeedProdred(), entity.getSeedExperience(), entity.getSeedGold(), entity.getSeedImg()));
        }
        return result;
    }

    /**
     *  添加种子
     * @param seedVo 种子的信息
     * @return
     */
    @Override
    @NotNull
    public ResultVo insert(@NotNull SeedVo seedVo) {
        long newId = idGenerator.getNumberId();
        //添加种子
        seedMapper.insert(new SeedEntity(newId, seedVo.getName(), seedVo.getLevel(), seedVo.getFirstGold(), seedVo.getFirstPrize(), seedVo.getXp(), seedVo.getGold(), seedVo.getImageUrl()));
        return new ResultVo(true, newId);
    }

    /**
     * 修改种子
     * @param id
     * @param seedVo 修改种子的实体类
     * @return
     */
    @Override
    @NotNull
    public ResultVo update(long id, @NotNull SeedVo seedVo) {
        SeedEntity entity = new SeedEntity(id, seedVo.getName(), seedVo.getLevel(), seedVo.getFirstGold(), seedVo.getFirstPrize(), seedVo.getXp(), seedVo.getGold(), seedVo.getImageUrl());
        //根据id修改种子
        int res = seedMapper.updateById(entity);
        return res == 0 ? new ResultVo(false, "数据行不存在") : new ResultVo(true, entity.getSeedId());
    }

    /**
     * 删除种子
     * @param id id
     * @return
     */
    @Override
    @NotNull
    public ResultVo delete(long id) {
        //根据id删除种子
        int result = seedMapper.deleteById(id);
        return result == 0 ? new ResultVo(false, "数据行不存在") : new ResultVo(true, id);
    }
}
