package com.dkm.seed.service;

import com.dkm.seed.entity.Pirate;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/1 16:19
 */
public interface IPirateService {

    /**
     * 添加被盗信息
     * @param pirate
     * @return
     */
    int addPirate(@RequestBody Pirate pirate);
}
