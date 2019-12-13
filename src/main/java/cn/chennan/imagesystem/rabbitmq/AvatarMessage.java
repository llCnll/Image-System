package cn.chennan.imagesystem.rabbitmq;

import lombok.Data;

/**
 * @author ChenNan
 * @date 2019-12-13 下午5:51
 **/
@Data
public class AvatarMessage {
    private String id;
    private String avatarName;
}
