package com.dkm.updateImage.controller

import com.dkm.config.annon.CheckAdminPermission
import com.dkm.updateImage.service.IPictureService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.data.repository.query.Param
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.annotation.Resource


@RestController("/image/")
class PictureController {
    @Resource
    private lateinit var pictureService: IPictureService

    @ApiOperation("上传图片")
    @ApiImplicitParams(ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"))
    @CheckAdminPermission
    @PostMapping("/update" ,consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun update(@RequestPart("file") multipartFile: MultipartFile) = pictureService.update(multipartFile)
}