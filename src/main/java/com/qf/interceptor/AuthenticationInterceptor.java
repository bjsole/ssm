package com.qf.interceptor;

import com.qf.constant.SSMConstant;
import com.qf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bbj 2019/7/17 15:01
 * @version 1.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    //前处理,执行controller方法之前
    //可以选择对请求进行拦截或者放行   false(拦截)  true(放行)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //实现权限校验
        User user = (User) request.getSession().getAttribute(SSMConstant.USER);

        if (user == null) {
            response.sendRedirect(request.getContextPath()+"/user/login-ui");
            return false;
        }

        return true;
    }

    //执行完controller,返回ModelAndView之后执行
    //拦截器的post处理中,可以修改mv
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    //拦截器处理器,响应数据之前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
