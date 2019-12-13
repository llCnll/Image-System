package cn.chennan.imagesystem.rabbitmq;

import cn.chennan.imagesystem.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenNan
 * @date 2019-12-13 下午5:33
 **/
@Slf4j
@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendAvatarMessage(AvatarMessage message){
        String msg = JSONUtil.beanToString(message);
        log.info("send message : {}", msg);
        amqpTemplate.convertAndSend(MQConfig.AVATAR_QUEUE, msg);
    }
}
