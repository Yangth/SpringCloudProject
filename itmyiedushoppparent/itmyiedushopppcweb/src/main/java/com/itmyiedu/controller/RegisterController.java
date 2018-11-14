package com.itmyiedu.controller;

import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.entity.UserEntity;
import com.itmyiedu.feign.MemberFeigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/11/9.
 */
@Controller
public class RegisterController {
    private static final String REGISTER="register";
    private static final String LOGIN="login";

    @Autowired
    private MemberFeigin memberFeigin;

    @RequestMapping("register")
    public String regist_page(){
        return REGISTER;
    }

    @RequestMapping(value = "regist_data",method = RequestMethod.POST)
    public String regist(UserEntity userEntity,HttpServletRequest reqest){
        ResponseBase responseRtn=memberFeigin.RgsMember(userEntity);
        if (!responseRtn.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
            reqest.setAttribute("error", "注册失败");
            return REGISTER;
        }
        return LOGIN;
    }
}
