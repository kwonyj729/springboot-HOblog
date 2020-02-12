package com.cos.blog.model.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// MaBatis에서 ResultType으로 담을 때 생성자 혹은 Setter중 무엇이 호출되는지 확인 후 Lombok 변경
@Data
@NoArgsConstructor
public class User implements UserDetails{
	private int id;
	private String username;
	private String password;
	private String email;
	private String profile;
	private String role;     // USER, MANAGER, ADMIN
	private Timestamp createDate;
	
	// 빌더 새로 만들어 줘야함.
	@Builder
	public User(String username, String password, String email, String profile, String role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.profile = profile;
		this.role = role;
	}

	
	
	
	//원래는 두개 더 있어야함.  username과 password에 대한 getter, setter 각각 있어야 함
	//근데 우리는 이미 User에   이미  필드명을 그렇게 만들었고 Lombok도 있어서 안만들어도 된다.	
	
	 
	// 여러개의 권한.  //계정이 갖고 잇는 권한목록을 리턴함.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {    // 우리 Role 한개만 리턴할 것이다.
		//getAuthorities  이 메소드가 자동으로 하나씩 꺼내면서, 권한 있는지 체크해준다.
		Collection<SimpleGrantedAuthority> collectors = new ArrayList<>();
		collectors.add(new SimpleGrantedAuthority("ROLE_"+role));  //우리는 기본으로 user 로 세팅됨.  "ROLE_"+role  이게 규칙이다.
		
		return collectors;
	}

	//계저잉 만료되었는지 체크하여 리턴함. (true : 만료안됨) 우리꺼는 무조건 true 이다.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정이 잠겨있는지 체크하여 리턴함.(true:잠기지 않음.)
	@Override
	public boolean isAccountNonLocked() {   // 비밀번호 입력이 여러번 틀리면 잠그는 방법.
		return true;
	}

	// 비밀번호가 만료되었는지 체크하여 리턴함. (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 해당 계정이 활성화 되었는지 체크하여 리턴함. (true:활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	
	
}
