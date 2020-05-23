package com.dkm.integral.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IntegralServiceImpl {
}
