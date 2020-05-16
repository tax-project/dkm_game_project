package com.dkm.file.service.impl;

import com.dkm.file.service.IFileService;
import com.dkm.file.utils.FileUtils;
import com.dkm.file.utils.FileVo;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.QrCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author qf
 * @date 2020/5/15
 * @vesion 1.0
 **/
@Service
public class FileServiceImpl implements IFileService {


   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private FileUtils fileUtils;

   @Value("${file.url}")
   private String fileUrl;

   @Autowired
   private QrCodeUtils qrCodeService;

   private final Object createFileLock = new Object();


   /**
    * 生成二维码并上传服务器
    * @param content
    * @return
    */
   @Override
   public FileVo getQrCode(String content) {

      String extraName = ".png";
      String newPath;
      FileVo vo = new FileVo();
      synchronized (createFileLock) {
         //文件保存路径
         Path path;
         //图片名称
         String fileName;
         do {
            //图片名称赋值
            fileName = idGenerator.getOrderCode();
            //保存路径赋值
            path = fileUtils.name2Path(fileName);

            //循环条件就是 如果文件地址不存在的情况下
         } while (fileUtils.checkPathConflict(path));
            //创建文件夹
         try {
            Files.createDirectories(path.getParent());
         } catch (IOException e) {
            e.printStackTrace();
         }
         newPath = path.toString() + extraName;

         String url = fileUrl + "/" + fileName.substring(0, 8) + "/"  + fileName + extraName;
         vo.setFileUrl(url);

      }

      try {
         //生成二维码
         qrCodeService.generateQrCodeImage (content,newPath);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return vo;
   }
}
