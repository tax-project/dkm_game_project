package com.dkm.updateImage.service

import com.dkm.updateImage.entities.ImageUpdateResultVo
import org.springframework.web.multipart.MultipartFile

interface IPictureService {
    fun update(multipartFile: MultipartFile): ImageUpdateResultVo

}
