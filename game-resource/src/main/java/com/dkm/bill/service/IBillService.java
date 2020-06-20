package com.dkm.bill.service;

import com.dkm.bill.entity.Bill;
import com.dkm.bill.entity.vo.BillVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/20 14:41
 */
public interface IBillService {

    List<Bill> queryAllBill(BillVo vo);
}
