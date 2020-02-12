package com.cos.blog.repository;

import com.cos.blog.model.user.User;
import com.cos.blog.model.user.dto.ReqJoinDto;
import com.cos.blog.model.user.dto.ReqLoginDto;

public interface UserRepository {
	int save(ReqJoinDto dto);
	int findByUsername(String username);   //아이디 중복확인할 때 사용. 카운트만 해준다. 
	
	// User 버전으로 만들기.
	User authentication(String username);
	
	User findByUsernameAndPassword(ReqLoginDto dto);
	int update(int id, String password, String profile);
	User findById(int id);
}
