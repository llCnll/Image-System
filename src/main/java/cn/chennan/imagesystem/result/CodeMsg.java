package cn.chennan.imagesystem.result;

/**
 * @author ChenNan
 * @date 2019/10/23
 **/
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");

    //登陆模块
    public static CodeMsg NO_COOKIE = new CodeMsg(500216, "请重新登录");

    //image
    public static CodeMsg IMAGE_EMPTY = new CodeMsg(500310, "图片为空");
    public static CodeMsg IMAGE_OVER_MAX = new CodeMsg(500311, "图片大小不得超高5MB");
    public static CodeMsg IMAGES_OVER_MAX = new CodeMsg(500312, "总图片大小不得超高5MB");

    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
