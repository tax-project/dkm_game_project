package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.vo.FileVo;
import com.dkm.feign.fallback.FileFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
@FeignClient(value = "file", fallback = FileFeignClientFallback.class)
public interface FileFeignClient {


   /**
    *  生成自己名片的二维码并上传服务器
    * @param content  二维码的内容
    * @return 二维码的地址
    */
   @GetMapping("/v1/file/getQrCode")
   Result<FileVo> getQrCode(@RequestParam("content") String content);
}
