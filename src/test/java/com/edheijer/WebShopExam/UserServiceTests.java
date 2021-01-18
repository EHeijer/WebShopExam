package com.edheijer.WebShopExam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.edheijer.WebShopExam.dto.UserDTO;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.repositories.UserRepository;
import com.edheijer.WebShopExam.services.UserMapper;
import com.edheijer.WebShopExam.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void getByUsernameTest() {
		String username = "customer1";
		User user = new User(username, "customer1@gmail.com", "123456");
		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
		
		assertEquals(Optional.of(user), userService.getByUsername(username));
	}
	
	@Test
	public void getByIdTest() {
		UserDTO userDto = new UserDTO();
		userDto.setId(1L);
		when(userRepository.findById(1L)).thenReturn(Optional.of(userMapper.toEntity(userDto)));
		
		assertEquals(Optional.of(userDto), userService.getById(1L));
	}
	
	@Test
	public void registerUserTest() {
		UserDTO user = new UserDTO();
		user.setPassword("123456");
		userService.registerUser(user);
		verify(userRepository, times(1)).saveAndFlush(userMapper.toEntity(user));
	}
	
	@Test
	public void getAllUsersTest() {
		User user1 = new User("customer1", "customer1@gmail.com", "123456");
		User user2 = new User("admin1", "admin1@gmail.com", "123456");
		when(userRepository.findAll())
			.thenReturn(Stream.of(user1, user2).collect(Collectors.toList()));
		
		assertEquals(2, userService.getAllUsers().size());
	}
}
