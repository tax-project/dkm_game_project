package com.dkm.problem.services.impl;

import com.dkm.problem.entities.bo.ProblemEntity;
import com.dkm.problem.entities.vo.ProblemVo;
import com.dkm.problem.mappers.ProblemMapper;
import com.dkm.problem.services.IProblemService;
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
public class ProblemServiceImpl implements IProblemService {
    @Resource
    private ProblemMapper problemMapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     *  得到所有问题
     * @return
     */
    @Override
    @NotNull
    public List<ProblemVo> getAllProblems() {
        val result = new ArrayList<ProblemVo>();
        //查询所有问题
        List<ProblemEntity> var14 = problemMapper.selectAll();
        for (ProblemEntity it : var14) {
            //将查询的问题添加进集合中
            result.add(new ProblemVo(String.valueOf(it.getId()), it.getProblemSubject(), it.getProblemAnswerA(), it.getProblemAnswerB(), it.getProblemAnswerC(), it.getProblemAnswerD(), it.getProblemAnswer()));
        }

        return result;
    }

    /**
     *  增加问题
     * @param problemVo
     * @return
     */
    @Override
    @NotNull
    public ResultVo insert(@NotNull ProblemVo problemVo) {
        long newId = idGenerator.getNumberId();
        //添加问题
        problemMapper.insert(new ProblemEntity(newId, problemVo.getProblemSubject(), problemVo.getProblemAnswerA(), problemVo.getProblemAnswerB(), problemVo.getProblemAnswerC(), problemVo.getProblemAnswerD(), problemVo.getProblemAnswer()));
        return new ResultVo(true, newId);
    }

    /**
     * 修改问题
     * @param id
     * @param problemVo
     * @return
     */
    @Override
    @NotNull
    public ResultVo update(long id, @NotNull ProblemVo problemVo) {
        //装配问题的所有参数
        ProblemEntity entity = new ProblemEntity(id, problemVo.getProblemSubject(), problemVo.getProblemAnswerA(), problemVo.getProblemAnswerB(), problemVo.getProblemAnswerC(), problemVo.getProblemAnswerD(), problemVo.getProblemAnswer());
        //修改问题
        int res = problemMapper.updateById(entity);
        return res == 0 ? new ResultVo(false, "数据行不存在") : new ResultVo(true, entity.getId());
    }

    /**
     * 删除问题
     * @param id
     * @return
     */
    @Override
    @NotNull
    public ResultVo delete(long id) {
        //根据id删除问题
        int result = problemMapper.deleteById(id);
        return result == 0 ? new ResultVo(false, "数据行不存在") : new ResultVo(true, id);
    }
}
