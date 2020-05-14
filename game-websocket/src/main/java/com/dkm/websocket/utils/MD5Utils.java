package com.dkm.websocket.utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author qf
 * @date 2020/4/8
 * @vesion 1.0
 **/
@Component
public class MD5Utils {

   public String md5(File file){
      BigInteger bigInteger = null;
      try {
         MessageDigest md5 = MessageDigest.getInstance("MD5");
         FileInputStream fileInputStream = new FileInputStream(file);
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         byte [] bytes = new byte[1024];
         int read = 0;
         while ((read=(fileInputStream.read(bytes)))!=-1){
            stream.write(bytes,0,read);
         }
         fileInputStream.close();
         stream.close();
         byte[] byteArray = stream.toByteArray();
         md5.update(byteArray);
         byte[] digest = md5.digest();
         bigInteger = new BigInteger(1,digest);
         //生成MD5的进制数
      } catch (NoSuchAlgorithmException | IOException e) {
         e.printStackTrace();
      }
      return bigInteger.toString(16);
   }


}
