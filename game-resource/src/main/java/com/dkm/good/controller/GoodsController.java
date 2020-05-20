package com.dkm.good.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.good.entity.vo.GoodsVo;
import com.dkm.good.service.IGoodsService;
import com.dkm.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@RestController
@RequestMapping("/v1/goods")
public class GoodsController {

   @Autowired
   private IGoodsService goodsService;

   @PostMapping("/insertGoods")
   public void insertGoods (@RequestBody GoodsVo vo) {
      goodsService.insertGood(vo);
   }
}
