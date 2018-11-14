package com.itmyiedu.controller;

import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.feign.MemberFeigin;
import com.itmyiedu.utils.CookieUtil;
import org.apache.commons.lang.StringUtils;
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
public class IndexController {
    private static final String INDEX="index";
    @Autowired
    private MemberFeigin memberFeigin;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String to_index(HttpServletRequest request){
        String cookie_member_token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);
        if (!StringUtils.isEmpty(cookie_member_token)){
            ResponseBase responseBase = memberFeigin.FindTokenByMember(cookie_member_token);
            if (responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
                LinkedHashMap responseData= (LinkedHashMap) responseBase.getData();
                String username= (String) responseData.get("username");
                request.setAttribute("username",username);
            }
        }
        return INDEX;
    }
}
