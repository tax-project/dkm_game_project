package com.dkm.updateImage.service.impl

import com.dkm.admin.mapper.vo.UserLoginVo
import com.dkm.constanct.CodeType
import com.dkm.exception.ApplicationException
import com.dkm.feign.PictureFeignClient
import com.dkm.feign.SimpleUserFeignClient
import com.dkm.updateImage.entities.ImageUpdateResultVo
import com.dkm.updateImage.service.IPictureService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.annotation.Resource

@Service
class PictureServiceImpl : IPictureService {
    @Resource
    private lateinit var pictureFeignClient: PictureFeignClient

    override fun update(multipartFile: MultipartFile) = kotlin.run {
        val storeFile = pictureFeignClient.storeFile(multipartFile).data
                ?: throw ApplicationException(CodeType.FEIGN_CONNECT_ERROR, "请求文件上传失败！")
        ImageUpdateResultVo(storeFile.fileUrl)
    }

}
