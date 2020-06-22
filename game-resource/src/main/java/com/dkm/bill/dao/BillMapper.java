package com.dkm.bill.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.bill.entity.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/20 14:41
 */
@Component
public interface BillMapper extends IBaseMapper<Bill> {

    List<Bill> queryAllBill(@Param("bType") Integer bType,@Param("bIncomeExpenditure") Integer bIncomeExpenditure,@Param("userId") Long userId);

}
