package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.vo.FileVo;
import com.dkm.feign.FileFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
@Component
public class FileFeignClientFallback implements FileFeignClient {


   @Override
   public Result<FileVo> getQrCode(String content) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }
}
