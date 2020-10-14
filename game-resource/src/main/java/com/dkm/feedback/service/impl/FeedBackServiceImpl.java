package com.dkm.feedback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feedback.dao.FeedBackMapper;
import com.dkm.feedback.entity.FeedBack;
import com.dkm.feedback.entity.vo.FeedBackVo;
import com.dkm.feedback.service.IFeedBackService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.utils.IdGenerator;
import com.dkm.wallet.entity.Withdrawal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/19 14:19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements IFeedBackService {

    @Autowired
    private LocalUser localUser;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 添加反馈信息
     */
    @Override
    public int insertFeedBack(FeedBackVo feedBackVo) {
        //装配信息添加
        FeedBack feedBack=new FeedBack();
        feedBack.setFContent(feedBackVo.getFContent());
        feedBack.setFStatus(feedBackVo.getFStatus());
        feedBack.setUserId(localUser.getUser().getId());
        feedBack.setReplyStatus(0);
        feedBack.setId(idGenerator.getNumberId());

        //添加反馈信息
        int insert = baseMapper.insert(feedBack);
        if(insert<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR, "添加反馈信息失败");
        }
        return insert;
    }

    /**
     * 查询自己反馈的信息
     */
    @Override
    public List<FeedBack> queryAllFeedBack() {

        //查询所有的反馈信息
        LambdaQueryWrapper<FeedBack> queryWrapper = new LambdaQueryWrapper<FeedBack>()
                .eq(FeedBack::getUserId ,localUser.getUser().getId());

        List<FeedBack> feedBacks = baseMapper.selectList(queryWrapper);
        if(feedBacks.size()==0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"无反馈信息");
        }
        //返回
        return feedBacks;
    }
}
