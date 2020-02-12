package com.cos.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.ReturnCode;
import com.cos.blog.model.user.User;
import com.cos.blog.model.user.dto.ReqJoinDto;
import com.cos.blog.model.user.dto.ReqLoginDto;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// result = 0 비정상, 1 정상, -1 DB 오류, -2 아이디 중복
	@Transactional
	public int 회원가입(ReqJoinDto dto) {
		try {
			int result = userRepository.findByUsername(dto.getUsername());
			
			if(result == 1) {
				return ReturnCode.아이디중복;
			}else {
				// 패스워드 암호화 하기.!!
				// 이제 여기서 받아서 쓰면된다. BCrypt를.
				String encodePassword = passwordEncoder.encode(dto.getPassword());
				dto.setPassword(encodePassword);    
				//이제 토근이 알아서 비밀번호를 암호화 해줄거다.. 테스트해보기.
				
				return userRepository.save(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public User 로그인(ReqLoginDto dto) {
		return userRepository.findByUsernameAndPassword(dto);
	}
	
	public int 수정완료(int id, String password, String profile) {
		
		int result = userRepository.update(id, password, profile);
		
		if(result == 1) { // 수정 성공
			User user = userRepository.findById(id);
			session.setAttribute("principal", user);
			
			return 1;
		}else { // 수정 실패
			return -1;
		}
	}
	

	
}
