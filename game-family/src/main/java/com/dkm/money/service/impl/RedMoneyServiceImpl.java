package com.dkm.money.service.impl;

import com.dkm.money.service.IRedMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RedMoneyServiceImpl implements IRedMoneyService {
}
