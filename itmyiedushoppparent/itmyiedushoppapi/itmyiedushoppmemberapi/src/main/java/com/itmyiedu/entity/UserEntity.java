package com.itmyiedu.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
public class UserEntity {
	private Integer id;
	private String username;
	private String password;
	private String phone;
	private String email;
	private Date created;
	private Date updated;
	private String openid;

}
