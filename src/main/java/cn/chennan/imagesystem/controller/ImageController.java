package cn.chennan.imagesystem.controller;

import cn.chennan.imagesystem.result.CodeMsg;
import cn.chennan.imagesystem.result.Result;
import cn.chennan.imagesystem.service.ImageService;
import cn.chennan.imagesystem.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author ChenNan
 * @date 2019-12-13 下午3:30
 **/
@Controller
@RequestMapping("/")
public class ImageController {

    @Value("${git.dir}")
    String imageRepository;

    @Autowired
    ImageService imageService;

    @RequestMapping("avatar")
    @ResponseBody
    public Result<String> main(@RequestParam("file")MultipartFile file){

        if(file.isEmpty()){
            return Result.error(CodeMsg.IMAGE_EMPTY);
        }
        String fileName = UUIDUtil.uuid();

        File dest = new File(imageRepository+"/avatar", fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        try {
            imageService.saveAvatar(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }


        return Result.success("<img src=\"https://github.com/llCnll/image-repository/raw/master/avatar/"+fileName+"\">");
    }
}
