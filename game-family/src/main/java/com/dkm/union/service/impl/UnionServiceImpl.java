package com.dkm.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.vo.FamilyImgsVo;
import com.dkm.union.dao.UnionMapper;
import com.dkm.union.entity.UnionEntity;
import com.dkm.union.entity.vo.UnionFamilyInfoVo;
import com.dkm.union.entity.vo.UnionUserVo;
import com.dkm.union.service.IUnionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: game_project
 * @description: 实现类
 * @author: zhd
 * @create: 2020-06-18 10:42
 **/
@Service
public class UnionServiceImpl implements IUnionService {


    @Resource
    private UnionMapper unionMapper;
    @Resource
    private FamilyDao familyDao;

    @Override
    public Map<String, Object> getUnionInfo(Long unionId) {
        //根据工会id查询工会信息
        UnionEntity unionEntity = unionMapper.selectOne(new LambdaQueryWrapper<UnionEntity>()
              .eq(UnionEntity::getUnionId, unionId));
        if(unionEntity==null){
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND);
        }
        //查询家族信息
        List<UnionFamilyInfoVo> unionFamily = familyDao.getUnionFamily(unionEntity.getUnionId());
        //根据用户id的集合查询工会信息的集合
        List<UnionUserVo> unionUser = unionMapper.getUnionUser(Stream.of(unionEntity.getUserId(),
              unionEntity.getViceUserId1(), unionEntity.getViceUserId2()).collect(Collectors.toList()));
        unionUser.forEach(a->{
            if(a.getUserId().equals(unionEntity.getUserId()))a.setLevel(2);
            else a.setLevel(1);
        });
        if(unionFamily!=null){
            //得到id的集合
            List<Long> collect = unionFamily.stream().mapToLong(UnionFamilyInfoVo::getFamilyId).boxed()
                  .collect(Collectors.toList());
            //转map
            Map<Long, List<FamilyImgsVo>> collect1 = familyDao.getImgs(collect)
                  .stream().collect(Collectors.groupingBy(FamilyImgsVo::getFamilyId, Collectors.toList()));
            unionFamily.forEach(a-> a.setImgs(collect1.get(a.getFamilyId())
                  .subList(0, Math.min(collect1.get(a.getFamilyId()).size(), 9))
                  .stream().map(FamilyImgsVo::getWeChatHeadImgUrl).collect(Collectors.toList())));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("users",unionUser);
        map.put("familys",unionFamily);
        map.put("unionName",unionEntity.getUnionName());
        //得到数据进行返回
        return map;
    }
}
