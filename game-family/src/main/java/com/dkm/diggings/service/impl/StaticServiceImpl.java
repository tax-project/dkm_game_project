package com.dkm.diggings.service.impl;

import com.dkm.diggings.bean.FamilyAddition;
import com.dkm.diggings.bean.entity.MineLevelEntity;
import com.dkm.diggings.bean.vo.MineInfoVo;
import com.dkm.diggings.dao.FamilyAdditionMapper;
import com.dkm.diggings.dao.MineLevelMapper;
import com.dkm.diggings.service.IStaticService;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OpenE
 */
@Service
public class StaticServiceImpl implements IStaticService {


    @Resource
    private MineLevelMapper mineLevelMapper;

    private final List<MineInfoVo> mineInfoVos =
            new ArrayList<>();

    @Override
    public MineInfoVo getItemsLevelType(int level) {
        return getItemsLevelTypes().get(level);
    }

    @Override
    public List<MineInfoVo> getItemsLevelTypes() {
        synchronized (mineInfoVos) {
            if (mineInfoVos.size() == 0) {
                val entityList = mineLevelMapper.selectList(null);
                for (MineLevelEntity levelEntity : entityList) {
                    MineInfoVo mineInfoVo = new MineInfoVo();
                    mineInfoVo.setNpcName(levelEntity.getNpcName());
                    mineInfoVo.setNpcSkillLevel(levelEntity.getNpcLevel());
                    mineInfoVo.setGoldYield(levelEntity.getGoldYield());
                    mineInfoVo.setIntegralYield(levelEntity.getIntegralYield());
                    mineInfoVo.setLevel(levelEntity.getLevel());
                    mineInfoVos.add(mineInfoVo);
                }
            }
        }
        return mineInfoVos;
    }

    @Resource
    private FamilyAdditionMapper familyAdditionMapper;

    @Override
    public List<FamilyAddition> getFamilyType() {
        return familyAdditionMapper.selectList(null);
    }
}
