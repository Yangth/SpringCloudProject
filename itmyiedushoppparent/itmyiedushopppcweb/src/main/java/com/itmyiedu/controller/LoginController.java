package com.itmyiedu.controller;

import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.entity.UserEntity;
import com.itmyiedu.feign.MemberFeigin;
import com.itmyiedu.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.plugin.com.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/11/9.
 */
@Controller
public class LoginController {
    private static final String LOGIN="login";
    private static final String INDEX="redirect:/";
    private static final String qqrelation = "qqrelation";
    @Autowired
    private MemberFeigin memberFeigin;

    //跳转登录页面
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login_page(){
        return LOGIN;
    }

    //登录数据验证
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login_chek(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response){

        ResponseBase responseBase=memberFeigin.LoginMember(userEntity);
        if (!responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
            request.setAttribute("error",responseBase.getMsg());
            return LOGIN;
        }
        LinkedHashMap reponseData= (LinkedHashMap) responseBase.getData();
        String memberToken= (String) reponseData.get("memberToken");
        if(StringUtils.isEmpty(memberToken)){
            request.setAttribute("error","会话已经失效");
            return LOGIN;
        }
        setCookie(memberToken,response);
        return INDEX;
    }

    //cookie设置
    private void setCookie(String memberToken, HttpServletResponse response){
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken,-1);
    }

    //QQ登录验证页面
    @RequestMapping("locaQQLogin")
    public String locaQQLogin(HttpServletRequest request) throws QQConnectException {
        String authorizeURL = new Oauth().getAuthorizeURL(request);
        return "redirect:" + authorizeURL;
    }

    //QQ登录回调函数
    @RequestMapping("qqLoginCallback")
    public String qqLoginCallback(HttpServletRequest reqest, HttpServletResponse response,HttpSession httpSession) throws QQConnectException {
        // 1.获取授权码COde
        // 2.使用授权码Code获取accessToken
        AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(reqest);
        if (accessTokenOj == null) {
            reqest.setAttribute("error", "QQ授权失败");
            return "error";
        }
        String accessToken = accessTokenOj.getAccessToken();
        if (accessToken == null) {
            reqest.setAttribute("error", "accessToken为null");
            return "error";
        }
        // 3.使用accessToken获取openid
        OpenID openidOj = new OpenID(accessToken);
        String userOpenId = openidOj.getUserOpenID();
        // 4.调用会员服务接口 使用userOpenId 查找是否已经关联过账号
        ResponseBase openUserBase = memberFeigin.findByOpenIdUser(userOpenId);
        if(openUserBase.getRtnCode().equals(Constants.HTTP_RES_CODE_201)){
            // 5.如果没有关联账号，跳转到关联账号页面
            httpSession.setAttribute("qqOpenid", userOpenId);
            return qqrelation;
        }
        //6.已经绑定账号  自动登录 将用户token信息存放在cookie中
        LinkedHashMap dataTokenMap = (LinkedHashMap) openUserBase.getData();
        String memberToken=(String) dataTokenMap.get("memberToken");
        setCookie(memberToken, response);
        return INDEX;
    }
}
