package cn.chennan.imagesystem.service;

import cn.chennan.imagesystem.rabbitmq.AvatarMessage;
import cn.chennan.imagesystem.rabbitmq.MQSender;
import cn.chennan.imagesystem.rabbitmq.WeChatMessage;
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

    public boolean saveAvatar(String token, String id, String fileName) throws Exception{

        GitUtil.commitAndPush("avatar", fileName, "add : "+id+" : "+fileName);

        AvatarMessage message = new AvatarMessage();
        message.setId(id);
        message.setToken(token);
        message.setAvatarName("avatar/"+fileName);

        sender.sendAvatarMessage(message);

        return true;
    }

    public void saveBlogImage(String fileName) throws Exception{
        GitUtil.commitAndPush("blog", fileName, "add : "+fileName);
    }

    public boolean saveWeChat(String id, String fileName) throws Exception{

        GitUtil.commitAndPush("wechat", fileName, "add : "+id+" : "+fileName);

        WeChatMessage message = new WeChatMessage();
        message.setId(id);
        message.setWeChat("wechat/"+fileName);

        sender.sendWeChatMessage(message);

        return true;
    }
}
