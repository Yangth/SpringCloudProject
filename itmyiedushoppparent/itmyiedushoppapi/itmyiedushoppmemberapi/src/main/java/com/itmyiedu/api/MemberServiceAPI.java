package com.itmyiedu.api;

import com.itmyiedu.base.ResponseBase;
import com.itmyiedu.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/11/3.
 */
@RequestMapping("/member")
public interface MemberServiceAPI {

    @RequestMapping("/testResponse")
    public ResponseBase testResponse();

    @RequestMapping("registerMember")
    public ResponseBase RgsMember(@RequestBody UserEntity userEntity);

    @RequestMapping("loginMember")
    public ResponseBase LoginMember(@RequestBody UserEntity userEntity);

    @RequestMapping("findTokenByMember")
    public ResponseBase FindTokenByMember(@RequestParam("memberToken") String memberToken);

    //使用openid查找用户信息
    @RequestMapping("/findByOpenIdUser")
    ResponseBase findByOpenIdUser(@RequestParam("openid") String openid);
    // 用户登录
    @RequestMapping("/qqLogin")
    ResponseBase qqLogin(@RequestBody UserEntity user);

}
