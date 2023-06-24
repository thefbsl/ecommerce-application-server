package com.server.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int userId;
	@Column(nullable = false)
	private String name;
	@Column(unique = true)
	private String email;
	private String password;
	private String address;
	private String about;
	private String gender;
	private String phone;
	@Column(name="CreateAt")
    private Date date;
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles=new HashSet<>();
    @OneToOne(mappedBy = "user")
    private  Cart cart;

	// importance method for providing giving authority
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		// GrantedAuthority is interface so we Can't obejct GantedAuthority Api directly  fog role.stream 
		//SimpleGantedAuthority is child implements  GrantedAuthority
		
		List<SimpleGrantedAuthority> collect = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return collect;
	}
	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



}
