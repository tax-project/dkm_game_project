package com.dkm.diggings.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.vo.OccupyResultVo;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.diggings.rule.MineRule;
import com.dkm.diggings.service.IDiggingsService;
import com.dkm.diggings.service.IHistoryService;
import com.dkm.diggings.service.IOccupiedService;
import com.dkm.diggings.service.IStaticService;
import com.dkm.exception.ApplicationException;
import com.dkm.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 关于占领
 *
 * @author OpenE
 */
@Slf4j
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
    @Resource
    private IDiggingsService diggingsService;


    @Override
    public OccupyResultVo occupy(long mineId, Long userId, Long familyId) {
        val item = mineMapper.selectById(mineId);
        if (diggingsService.getOccupationSize(userId) <= 0) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "你没有占领次数了");
        }
        if (ObjectUtils.isNullOrEmpty(item)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未找到此矿山.");
        }
        val result = new OccupyResultVo();
        val info = staticService.getItemsLevelTypes().get(item.getItemLevel());
        result.setInfo(info);
        val herUserId = item.getUserId();
        if (herUserId == userId && !historyService.expired(mineId, userId, familyId)) {
            log.info("用户尝试自我占领，占领取消");
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "不允许自己攻击自己哦");
        }
        clearLast(userId, familyId);
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

    private void clearLast(Long userId, Long familyId) {
        val unfinishedHistory = historyService.getUnfinishedHistory(userId, familyId);
        if (unfinishedHistory != null) {
            historyService.destroy(unfinishedHistory.getId(), unfinishedHistory.getMineId());
        }
    }


    @Override
    public OccupyResultVo disOccupy(long mineId, Long userId, Long familyId) {
        val item = mineMapper.selectById(mineId);
        if (ObjectUtils.isNullOrEmpty(item)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未找到此矿山.");
        }

        if (item.getUserId() != userId) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "你没有权限处理他人的挖矿！");
        }
        val unfinishedHistory = historyService.getUnfinishedHistory(userId, familyId);
        historyService.destroy(unfinishedHistory.getId(), mineId);
        final val occupyResultVo = new OccupyResultVo();
        occupyResultVo.setStatus(true);
        return occupyResultVo;
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
