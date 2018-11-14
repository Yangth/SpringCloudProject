package com.itmyiedu.mapper;

import com.itmyiedu.entity.UserEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

	@Select("select  id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
	UserEntity findByID(@Param("userId") String userId);

	@Insert("INSERT  INTO `mb_user`  (username,password,phone,email) VALUES (#{username}, #{password},#{phone},#{email});")
	Integer insertUser(UserEntity userEntity);

	@Select("select  id,username,password,phone,email,created,updated from mb_user where username=#{username} and password=#{password}")
	UserEntity login(@Param("username") String username, @Param("password") String password);

	@Select("select  id,username,password,phone,email,created,updated ,openid from mb_user where openid =#{openid}")
	UserEntity findByOpenIdUser(@Param("openid") String openid);

	@Update("update mb_user set openid=#{openid} where id=#{userId}")
	Integer updateByOpenIdUser(@Param("openid") String openid,@Param("userId") Integer userId);

}
