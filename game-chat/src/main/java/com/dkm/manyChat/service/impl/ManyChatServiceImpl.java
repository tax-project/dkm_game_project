package com.dkm.manyChat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.vo.FileVo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.FileFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.manyChat.dao.ManyChatMapper;
import com.dkm.manyChat.entity.ManyChat;
import com.dkm.manyChat.entity.vo.ManyChatInfoVo;
import com.dkm.manyChat.entity.vo.ManyChatVo;
import com.dkm.manyChat.service.IManyChatInfoService;
import com.dkm.manyChat.service.IManyChatService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

   @Autowired
   private FileFeignClient fileFeignClient;

   @Value("${file.qrCodeUrl}")
   private String qrCodeUrl;

   @Autowired
   private LocalUser localUser;

   @Autowired
   private IManyChatInfoService manyChatInfoService;

   @Override
   public void insertManyChat(ManyChatVo vo) {
      ManyChat manyChat = new ManyChat();

      //群聊id
      Long manyChatId = idGenerator.getNumberId();
      manyChat.setId(manyChatId);

      manyChat.setCreateDate(LocalDateTime.now());
      manyChat.setManyName(vo.getManyName());
      //生成群聊二维码
      Result<FileVo> qrCode = fileFeignClient.getQrCode(qrCodeUrl + "?manyChatId=" + manyChatId);

      if (qrCode.getCode() != 0) {
         throw new ApplicationException(CodeType.FEIGN_CONNECT_ERROR, "调用fileFeign失败");
      }

      manyChat.setManyQrCode(qrCode.getData().getFileUrl());

      int insert = baseMapper.insert(manyChat);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "建立群聊失败");
      }

      List<ManyChatInfoVo> list = new ArrayList<>();

      for (Long userId : vo.getList()) {
         ManyChatInfoVo infoVo = new ManyChatInfoVo();
         infoVo.setId(idGenerator.getNumberId());
         infoVo.setManyChatId(manyChatId);
         infoVo.setUserId(userId);
         infoVo.setRoleStatus(2);
         list.add(infoVo);
      }

      UserLoginQuery user = localUser.getUser();

      ManyChatInfoVo infoVo = new ManyChatInfoVo();
      infoVo.setId(idGenerator.getNumberId());
      infoVo.setManyChatId(manyChatId);
      infoVo.setUserId(user.getId());
      infoVo.setRoleStatus(0);
      list.add(infoVo);

      manyChatInfoService.insertAllUser(list);

   }
}
