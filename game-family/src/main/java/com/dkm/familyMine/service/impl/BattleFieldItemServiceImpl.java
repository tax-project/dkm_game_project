package com.dkm.familyMine.service.impl;

import com.dkm.familyMine.service.IBattleFieldItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BattleFieldItemServiceImpl implements IBattleFieldItemService {
    
}
