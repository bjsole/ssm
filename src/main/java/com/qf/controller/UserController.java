package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.util.SendSMSUtil;
import com.qf.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.qf.constant.SSMConstant.*;


/**
 * 用户模块的controller层
 *
 * @author bbj 2019/7/15 10:28
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendSMSUtil sendSMSUtil;

    //执行登录
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(String username, String password, HttpSession session) {
        //1.校验参数是否合法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //不合法
            return new ResultVO(-1, "用户名和密码为必填项", null);
        }
        //2.调用service执行登录
        User user = userService.login(username, password);
        //3.根据返回结果判断登录是否成功
        if (user != null) {
            //4.如果成功,将用户的信息放到session域中
            session.setAttribute(USER, user);
            return new ResultVO(0, "成功", null);
        } else {
            //5.如果失败,响应错误信息给ajax引擎
            return new ResultVO(-1, "用户名或密码错误", null);
        }
    }


    //跳转登录页面  GET  /user/login-ui
    @GetMapping("/login-ui")
    public String loginUI() {
//        int i=1/0;
        return "user/login";
    }


    //执行注册 /user/register
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {

        //1.校验参数是否合法
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            redirectAttributes.addAttribute("registerInfo", msg);
            return REDIRECT + REGISTER_UI;
        }

        //2.校验验证码是否正确
        String code = (String) session.getAttribute(CODE);
        System.out.println(code);
        if (StringUtils.isEmpty(code)) {
            redirectAttributes.addAttribute("registerInfo", "验证码发送失败");
            return REDIRECT + REGISTER_UI;
        }
        if (!user.getRegisterCode().equals("123")) {
            redirectAttributes.addAttribute("registerInfo", "验证码错误");
            return REDIRECT + REGISTER_UI;
        }

        //3.调用service执行注册功能
        Integer count = userService.register(user);

        //4.根据service返回的结果,跳转到指定页面
        if (count == 1) {
            //注册成功
            return REDIRECT + LOGIN_UI;
        } else {
            redirectAttributes.addAttribute("registerInfo", "服务器爆炸了");
            return REDIRECT + REGISTER_UI;
        }

    }


    //发送手机短信 POST http://localhost/user/send-sms
    @PostMapping(value = "/send-sms", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String sendSms(@RequestParam String phone, HttpSession session) {
        return sendSMSUtil.sendSMS(phone, session);
    }


    //检查姓名  post    /user/check-username
    @PostMapping("/check-username")
    @ResponseBody //绕过视图解析器,直接响应,,,//如果返回结果是map或pojo类,自动序列化成json
    public ResultVO checkUsername(@RequestBody User user) {
        //调用service,判断用户名是否可用
        Integer count = userService.findCountByUsername(user.getUsername());

        /*Map<String,Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("data", count);
        return map;*/

        return new ResultVO(0, "成功", count);
    }

    //跳转注册页面
    @GetMapping("/register-ui")
    public String registerUI() {

        return "user/register";
    }

}
