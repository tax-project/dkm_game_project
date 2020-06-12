package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.vo.FileVo;
import com.dkm.feign.FileFeignClient;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
public class FileFeignClientFallback implements FileFeignClient {


   @Override
   public Result<FileVo> getQrCode(String content) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }
}
