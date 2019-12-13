package cn.chennan.imagesystem.service;

import cn.chennan.imagesystem.util.GitUtil;
import org.springframework.stereotype.Service;

/**
 * @author ChenNan
 * @date 2019-12-13 下午4:27
 **/
@Service
public class ImageService {

    public boolean saveAvatar(String fileName) throws Exception{

        GitUtil.commitAndPush("avatar", fileName, fileName);

        return true;
    }
}
