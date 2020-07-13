package com.dkm.diggings.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.vo.OccupyResultVo;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.diggings.rule.MineRule;
import com.dkm.diggings.service.IHistoryService;
import com.dkm.diggings.service.IOccupiedService;
import com.dkm.diggings.service.IStaticService;
import com.dkm.exception.ApplicationException;
import com.dkm.utils.ObjectUtils;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 关于占领
 *
 * @author OpenE
 */
@Service
public class OccupiedServiceImpl implements IOccupiedService {
    /**
     * 占领成功
     */
    @Resource
    private IHistoryService historyService;
    @Resource
    private MineMapper mineMapper;

    @Resource
    private IStaticService staticService;

    @Resource
    private MineRule mineRule;


    @Override
    public OccupyResultVo occupy(long battleId, Long userId, Long familyId) {
        val item = mineMapper.selectById(battleId);
        if (ObjectUtils.isNullOrEmpty(item)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未找到此矿山.");
        }
        val result = new OccupyResultVo();
        val info = staticService.getItemsLevelTypes().get(item.getItemLevel());
        result.setInfo(info);
        val herUserId = item.getUserId();
        if (herUserId == userId) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "不允许自己攻击自己哦");
        }
        val successRate = mineRule.calculateSuccessRate(staticService.getSkillLevel(userId), herUserId == 0 ?
                info.getNpcSkillLevel() : staticService.getSkillLevel(herUserId));
        if (mineRule.occupy(successRate)) {
            result.setStatus(true);
            occupied(userId, familyId, item.getId());
        } else {
            result.setStatus(false);
        }
        return result;
    }


    /**
     * 占领完成
     */
    private void occupied(Long userId, Long familyId, long mineId) {
        val unfinishedHistory = historyService.getUnfinishedHistory(userId, familyId);
        if (unfinishedHistory != null) {
            historyService.destroy(unfinishedHistory.getId(), mineId);
        }
        historyService.createNewItem(userId, familyId, mineId);
    }
}
