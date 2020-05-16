package com.dkm.manyChat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.file.service.IFileService;
import com.dkm.file.utils.FileVo;
import com.dkm.manyChat.dao.ManyChatMapper;
import com.dkm.manyChat.entity.ManyChat;
import com.dkm.manyChat.entity.vo.ManyChatVo;
import com.dkm.manyChat.service.IManyChatService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/15
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ManyChatServiceImpl extends ServiceImpl<ManyChatMapper, ManyChat> implements IManyChatService {

   @Autowired
   private IdGenerator idGenerator;

   @Value("${file.qrCodeUrl}")
   private String qrCodeUrl;

   @Autowired
   private IFileService fileService;

   @Override
   public void insertManyChat(ManyChatVo vo) {
      ManyChat manyChat = new ManyChat();

      //群聊id
      Long manyChatId = idGenerator.getNumberId();
      manyChat.setId(manyChatId);

      manyChat.setCreateDate(LocalDateTime.now());
      manyChat.setManyName(vo.getManyName());
      //生成群聊二维码
      FileVo qrCode = fileService.getQrCode(qrCodeUrl + "?manyChatId=" + manyChatId);
      manyChat.setManyQrCode(qrCode.getFileUrl());

      int insert = baseMapper.insert(manyChat);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "建立群聊失败");
      }




   }
}
