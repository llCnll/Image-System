package cn.chennan.imagesystem.handler;

import cn.chennan.imagesystem.result.CodeMsg;
import cn.chennan.imagesystem.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ChenNan
 * @date 2019-11-14 下午1:44
 **/
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public Result exception(HttpServletRequest request, MultipartException e) {
        if(e.getCause().getCause() instanceof FileUploadBase.SizeLimitExceededException){
            log.warn(CodeMsg.IMAGE_OVER_MAX.getMsg());
            return Result.error(CodeMsg.IMAGE_OVER_MAX);
        }else if(e.getCause().getCause() instanceof FileUploadBase.FileSizeLimitExceededException){
            log.warn(CodeMsg.IMAGE_OVER_MAX.getMsg());
            return Result.error(CodeMsg.IMAGES_OVER_MAX);
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
