package com.dkm.updateImage.services.impl

import com.dkm.constanct.CodeType
import com.dkm.exception.ApplicationException
import com.dkm.feign.AnotherFileFeignClient
import com.dkm.updateImage.entities.ImageUpdateResultVo
import com.dkm.updateImage.services.IPictureService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.annotation.Resource

@Service
class PictureServiceImpl : IPictureService {
    @Resource
    private lateinit var anotherFileFeignClient: AnotherFileFeignClient

    override fun update(multipartFile: MultipartFile) = kotlin.run {
        val storeFile = anotherFileFeignClient.storeFile(multipartFile).data
                ?: throw ApplicationException(CodeType.FEIGN_CONNECT_ERROR, "请求文件上传失败！")
        ImageUpdateResultVo(storeFile.fileUrl)
    }

}
