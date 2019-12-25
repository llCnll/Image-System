package cn.chennan.imagesystem.controller;

import cn.chennan.blog.dubbo.UserServiceDubbo;
import cn.chennan.imagesystem.result.CodeMsg;
import cn.chennan.imagesystem.result.Result;
import cn.chennan.imagesystem.service.ImageService;
import cn.chennan.imagesystem.util.JSONUtil;
import cn.chennan.imagesystem.util.UUIDUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author ChenNan
 * @date 2019-12-13 下午3:30
 **/
@Controller
@RequestMapping("/")
@Slf4j
public class ImageController {

    @Value("${git.dir}")
    String imageRepository;

    @Autowired
    ImageService imageService;

    @Reference
    UserServiceDubbo userService;

    @PostMapping("blog")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
    public String blog(@RequestParam("img") MultipartFile file) {

        if(file.isEmpty()){
            return "图片为空";
        }
        String fileName = UUIDUtil.uuid();

        File dest = new File(imageRepository, "blog/"+fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return "服务端异常";
        }

        try {
            imageService.saveBlogImage(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return "服务端异常";
        }

        return "https://github.com/llCnll/image-repository/raw/master/blog/"+fileName;
    }

    @PostMapping("avatar")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
    public Result<String> avatar(@RequestParam("file")MultipartFile file, HttpServletRequest request){

        if(file.isEmpty()){
            return Result.error(CodeMsg.IMAGE_EMPTY);
        }

        String paramToken = request.getParameter(JSONUtil.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, JSONUtil.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            log.info(CodeMsg.NO_COOKIE.getMsg());
            return Result.error(CodeMsg.NO_COOKIE);
        }

        String token = StringUtils.isEmpty(cookieToken) ? paramToken : cookieToken;
        String id = userService.getByTokenReturnId(token);
        if(id == null){
            return Result.error(CodeMsg.NO_COOKIE);
        }
        String fileName = UUIDUtil.uuid();

        File dest = new File(imageRepository, "avatar/"+fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        try {
            imageService.saveAvatar(token, id, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        return Result.success("https://github.com/llCnll/image-repository/raw/master/avatar/"+fileName);
    }

    /**
     * 用来cropbox.html
     * @param file
     * @return
     */
    @PostMapping("avatar_demo")
    @ResponseBody
    @CrossOrigin
    public Result<String> avatarDemo(@RequestParam("file")MultipartFile file){

        if(file.isEmpty()){
            return Result.error(CodeMsg.IMAGE_EMPTY);
        }
        String fileName = UUIDUtil.uuid();

        File dest = new File(imageRepository, "avatar/"+fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        try {
            imageService.saveAvatar("123", "llCnll", fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        return Result.success("https://github.com/llCnll/image-repository/raw/master/avatar/"+fileName);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookieName.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
