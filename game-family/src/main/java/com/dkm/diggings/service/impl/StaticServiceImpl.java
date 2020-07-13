package com.dkm.diggings.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.FamilyAddition;
import com.dkm.diggings.bean.entity.MineLevelEntity;
import com.dkm.diggings.bean.vo.MineInfoVo;
import com.dkm.diggings.dao.FamilyAdditionMapper;
import com.dkm.diggings.dao.MineLevelMapper;
import com.dkm.diggings.service.IStaticService;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.UserFeignClient;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OpenE
 */
@Service
public class StaticServiceImpl implements IStaticService {

    @Resource
    private FamilyAdditionMapper familyAdditionMapper;

    @Autowired
    private ResourceFeignClient resourceFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;
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


    @Override
    public List<FamilyAddition> getFamilyType() {
        return familyAdditionMapper.selectList(null);
    }


    @Override
    public Integer getSkillLevel(Long userId) {
        val week = LocalDate.now().getDayOfWeek().getValue();
        val listResult = resourceFeignClient.querySkillByUserId(userId).getData();
        if (listResult == null) {
            throw new ApplicationException(CodeType.FEIGN_CONNECT_ERROR, "获取技能等级出错。");
        }
        if (week > 6) {
            val renownVoResult = userFeignClient.queryUserSection(userId);
            return renownVoResult.getData().getUserInfoRenown().intValue();
        } else {
            return listResult.get(week - 1).getSkGrade();
        }
    }
}
