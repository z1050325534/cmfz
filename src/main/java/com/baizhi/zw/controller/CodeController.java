package com.baizhi.zw.controller;

import com.baizhi.zw.code.CreateValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@ResponseBody
public class CodeController {

    @RequestMapping("/getCode")
    //后台登陆获取验证码  图片刷新
    public void test(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode code = new CreateValidateCode();
        String code1 = code.getCode();
        code.write(response.getOutputStream());
        session.setAttribute("code",code1);
    }
}
