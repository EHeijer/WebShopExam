package com.edheijer.WebShopExam.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
	
	private User user;
	
	
	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	public void setPassword(String password) {
		user.setPassword(password);
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	public void setUsername(String username) {
		user.setUsername(username);
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public void setEmail(String email) {
		user.setEmail(email);
	}
	
	public List<Order> getUserOrders() {
		return user.getUserOrders();
	}
	
	public void setUserOrders(List<Order> userOrders) {
		user.setUserOrders(userOrders);
	}
	
	public Long getUserId() {
		return user.getId();
	}
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	

}
