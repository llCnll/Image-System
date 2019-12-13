package cn.chennan.imagesystem.service;

import cn.chennan.imagesystem.rabbitmq.AvatarMessage;
import cn.chennan.imagesystem.rabbitmq.MQSender;
import cn.chennan.imagesystem.util.GitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenNan
 * @date 2019-12-13 下午4:27
 **/
@Service
public class ImageService {

    @Autowired
    MQSender sender;

    public boolean saveAvatar(String fileName) throws Exception{

        GitUtil.commitAndPush("avatar", fileName, "add : llCnll : "+fileName);

        AvatarMessage message = new AvatarMessage();
        message.setId("llCnll");
        message.setAvatarName(fileName);

        sender.sendAvatarMessage(message);

        return true;
    }
}