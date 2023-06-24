package com.server.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private int userId;
	@NotEmpty
	@Size(min=4 ,max=50 ,message="name must should be 4 and 50")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$",message = "Invalid username !!")
	private String name;
	@Email(message="Enter Vaild Email")
	private String email;
	@NotEmpty
	@Size(min=6,max=10,message="password should be 6 to 10")
	private String password;
	private String address;
	private String about;
	private String gender;
	private Date   date; 
    private boolean  active;
    private Set<RoleDto> roles=new HashSet<>();
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	@NotBlank
	private String phone;
}
