package cn.chennan.imagesystem.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChenNan
 * @date 2019-10-31 下午3:33
 **/
@Configuration
public class MQConfig {

    public final static String AVATAR_QUEUE = "avatar.queue";

    /**
     * Direct模式 交换机模式
     */
    @Bean
    public Queue avatarQueue(){
        return new Queue(AVATAR_QUEUE,true);
    }
}
