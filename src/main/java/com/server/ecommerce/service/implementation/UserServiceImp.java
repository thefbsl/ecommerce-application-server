package com.server.ecommerce.service.implementation;

import com.server.ecommerce.dto.UserDto;
import com.server.ecommerce.exception.ResourceNotFoundException;
import com.server.ecommerce.model.Role;
import com.server.ecommerce.model.User;
import com.server.ecommerce.repository.RoleRepository;
import com.server.ecommerce.repository.UserRepository;
import com.server.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDto create(UserDto userDto) {
		User user = toEntity(userDto);
		        
		Role role = roleRepository.findById(7412).get();
		user.getRoles().add(role);
		User userCreate = userRepository.save(user);
		return toDto(userCreate);
	}

	@Override
	public UserDto update(UserDto t, int userId) {
		User u = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found by this id"));
		u.setPhone(t.getPhone());
		u.setPassword(t.getPassword());
		u.setName(t.getName());
		u.setGender(t.getGender());
		u.setEmail(t.getEmail());
		u.setDate(t.getDate());
		u.setAddress(t.getAddress());
		u.setActive(t.isActive());
		u.setAbout(t.getAbout());
		User Updateuser=userRepository.save(u);
		return toDto(Updateuser);
	}

	@Override
	public void delete(int userId) {
		User u= userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("UserId not Found"));
		        userRepository.delete(u);
	
	}

	@Override
	public List<UserDto> getAll() {
		List<User> allUsers= userRepository.findAll();
		return allUsers.stream().map(user -> toDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserDto getByUserId(int userId) {
		User finduser= userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"+userId));
		return toDto(finduser);
	}
	@Override
	public UserDto getByEmailId(String emailId) {
		       User findemail= userRepository.findByEmail(emailId).orElseThrow(() -> new ResourceNotFoundException("User Email Is is Not Exit"+emailId));
		return toDto(findemail);
	}
	@Autowired
	private ModelMapper mapper;
	public UserDto toDto(User u) {
		return mapper.map(u,UserDto.class);
	}
	public User toEntity(UserDto dto) {
		return mapper.map(dto,User.class);
	}
}
