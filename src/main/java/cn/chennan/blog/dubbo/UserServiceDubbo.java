package cn.chennan.blog.dubbo;

/**
 * @author ChenNan
 * @date 2019-12-23 下午3:32
 **/
public interface UserServiceDubbo {
    public String getByTokenReturnId(String token);
}
