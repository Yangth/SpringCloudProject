package com.itmyiedu.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.itmyiedu.base.BaseApiService;
import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.api.MemberServiceAPI;
import com.itmyiedu.constants.Constants;
import com.itmyiedu.mapper.MemberMapper;
import com.itmyiedu.entity.UserEntity;
import com.itmyiedu.mq.RegisterMailboxProducer;
import com.itmyiedu.utils.MD5Util;
import com.itmyiedu.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/11/3.
 */
@Slf4j
@RestController
public class MemberServiceImp extends BaseApiService implements MemberServiceAPI {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;
    @Value("${messages.queue}")
    private String MESSAGESQUEUE;

    @Override
    public ResponseBase testResponse() {
        return setResultSuccess();
    }

    @Override
    public ResponseBase RgsMember(@RequestBody UserEntity userEntity) {
        String pwd=userEntity.getPassword();
        if (StringUtils.isEmpty(pwd)){
            return setResultError("密码不能为空！");
        }
        String newPassword= MD5Util.MD5(pwd);
        userEntity.setPassword(newPassword);
        Integer rgsResult= memberMapper.insertUser(userEntity);
        if (rgsResult<=0){
            return setResultError("用户注册失败");
        }
        String emailJson=EmailJson(userEntity.getEmail());
        sendMessage(emailJson);
        return setResultSuccess("用户注册成功");
    }

    @Override
    public ResponseBase LoginMember(@RequestBody UserEntity userEntity) {
        String username=userEntity.getUsername();
        String password=userEntity.getPassword();
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
            return setResultError("请确认您输入的用户名和密码！");
        }
        String newpassword=MD5Util.MD5(password);
        UserEntity userEntity1 = memberMapper.login(username, newpassword);

        return setLogin(userEntity1);
    }

    @Override
    public ResponseBase FindTokenByMember(@RequestParam("memberToken")String memberToken) {
        if(StringUtils.isEmpty(memberToken)){
            return setResultError("MemberToken point nul!");
        }
        String userId= baseRedisService.getString(memberToken);
        if(StringUtils.isEmpty(userId)){
            return setResultError("Token不存在或者已经失效！");
        }
        UserEntity userEntity= memberMapper.findByID(userId);
        if(userEntity==null){
            return setResultError("未查找到该用户信息！");
        }
        userEntity.setPassword("******");
        return setResultSuccess(userEntity);
    }

    @Override
    public ResponseBase findByOpenIdUser(@RequestParam("openid") String openid) {
        // 1.验证参数
        if (StringUtils.isEmpty(openid)) {
            return setResultError("openid不能为空1");
        }
        // 2.使用openid 查询数据库 user表对应数据信息
        UserEntity userEntity = memberMapper.findByOpenIdUser(openid);
        if (userEntity == null) {
            return setResultError(Constants.HTTP_RES_CODE_201, "该openid没有关联");
        }
        // 3.自动登录
        return setLogin(userEntity);
    }

    @Override
    public ResponseBase qqLogin(@RequestBody UserEntity user) {
        // 1.验证参数
        String openid = user.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            return setResultError("openid不能为空!");
        }
        // 2.先进行账号登录
        ResponseBase setLogin = LoginMember(user);
        if (!setLogin.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return setLogin;
        }
        // 3.自动登录
        JSONObject jsonObjcet = (JSONObject) setLogin.getData();
        // 4. 获取token信息
        String memberToken = jsonObjcet.getString("memberToken");
        ResponseBase userToken = FindTokenByMember(memberToken);
        if (!userToken.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return userToken;
        }
        UserEntity userEntity = (UserEntity) userToken.getData();
        // 5.修改用户openid
        Integer userId = userEntity.getId();
        Integer updateByOpenIdUser = memberMapper.updateByOpenIdUser(openid, userId);
        if (updateByOpenIdUser <= 0) {
            return setResultError("QQ账号管理失败!");
        }
        return setLogin;
    }

    private ResponseBase setLogin(UserEntity userEntity) {
        if (userEntity == null) {
            return setResultError("账号或者密码不能正确");
        }
        // 3.如果账号密码正确，对应生成token
        String memberToken = TokenUtils.getMemberToken();
        // 4.存放在redis中，key为token value 为 userid
        Integer userId = userEntity.getId();
        log.info("####用户信息token存放在redis中... key为:{},value", memberToken, userId);
        baseRedisService.setString(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
        // 5.直接返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }


    private String EmailJson(String email){
        JSONObject root =new JSONObject();
        JSONObject hearder =new JSONObject();
        hearder.put("interfacetype", Constants.MSG_EMAIL);
        JSONObject content =new JSONObject();
        content.put("email",email);
        root.put("header",hearder);
        root.put("content",content);
        return root.toJSONString();
    }

    private void sendMessage(String message){
        ActiveMQQueue activeMQQueue=new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue,message);

    }

}
