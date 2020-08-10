package com.dkm.updateImage.services

import com.dkm.updateImage.entities.ImageUpdateResultVo
import org.springframework.web.multipart.MultipartFile

interface IPictureService {
    fun update(multipartFile: MultipartFile, token: String): ImageUpdateResultVo

}
