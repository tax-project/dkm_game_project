package com.dkm.skill.services.impl;

import com.dkm.skill.entities.bo.SkillEntity;
import com.dkm.skill.entities.vo.SkillVo;
import com.dkm.skill.mappers.SkillMapper;
import com.dkm.skill.services.ISkillService;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.bean.ResultVo;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(
        rollbackFor = {Exception.class}
)

public class SkillServiceImpl implements ISkillService {
    @Resource
    private SkillMapper skillMapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 查询所有技能
     * @return
     */
    @NotNull
    public List<SkillVo> getAll() {
        //查询所有数据
        val var5 = skillMapper.selectAll();
        val result = new ArrayList<SkillVo>();
        for (SkillEntity entity : var5) {
            //将数据添加到集合中
            result.add(new SkillVo(String.valueOf(entity.getId()), entity.getSkName(), entity.getSkEffectOne(), entity.getSkEffectTwo()));
        }
        return result;
    }

    /**
     * 增加技能
     * @param skillVo 技能的参数
     * @return
     */
    @NotNull
    public ResultVo insert(@NotNull SkillVo skillVo) {
        long newId = idGenerator.getNumberId();
        //添加数据
        skillMapper.insert(new SkillEntity(newId, skillVo.getSkinName(), skillVo.getFirstSkinEffect(), skillVo.getSecondSkinEffect()));
        return new ResultVo(true, newId);
    }

    /**
     * 修改技能
     * @param id id
     * @param skillVo 技能的参数
     * @return
     */
    @NotNull
    public ResultVo update(long id, @NotNull SkillVo skillVo) {
        SkillEntity entity = new SkillEntity(id, skillVo.getSkinName(), skillVo.getFirstSkinEffect(), skillVo.getSecondSkinEffect());
        //修改数据
        int res = skillMapper.updateById(entity);
        return res == 0 ? new ResultVo(false, "数据行不存在") : new ResultVo(true, entity.getId());
    }

    /**
     * 删除技能
     * @param id id
     * @return
     */
    @NotNull
    public ResultVo delete(long id) {
        //删除数据
        int result = skillMapper.deleteById(id);
        return result == 0 ? new ResultVo(false, "数据行不存在") : new ResultVo(true, id);
    }
}
