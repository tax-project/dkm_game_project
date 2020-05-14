package com.dkm.mounts.service.impl;

import com.dkm.mounts.dao.MountsMapper;
import com.dkm.mounts.entity.MountsInfoEntity;
import com.dkm.mounts.service.MountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhd
 * @date 2020/5/11 17:26
 */
@Service
public class MountServiceImpl implements MountService {

    @Resource
    private MountsMapper mountsMapper;

    @Override
    public List<MountsInfoEntity> getAll() {
        return mountsMapper.selectList(null);
    }

    @Override
    public List<MountsInfoEntity> haveMounts(Long userId) {
        return mountsMapper.haveMounts(userId);
    }
}
