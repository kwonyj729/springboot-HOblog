package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cos.blog.model.user.User;
import com.cos.blog.repository.UserRepository;

@Service    //메모리에 떠있다.
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 전 단계에서 만들어진 토근을 여기에 던진다. 토근중에, username 을 가져와서 디비에 있는지없는지 셀렉트 함
		User user = userRepository.authentication(username); // 아예 패스워드에 대한 접근을 개발자도 못하게 함. 보안이 잘됨.

		if (user == null) {
			throw new UsernameNotFoundException("해당 유저가 없습니다.");
		}
		return user;
	}
}