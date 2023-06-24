package com.server.ecommerce;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.server.ecommerce.model.Role;
import com.server.ecommerce.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role0 = new Role();
			role0.setId(5245);
			role0.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(7412);
			role1.setName("ROLE_NORMAL");

			Role role3 = new Role();
			role3.setId(9632);
			role3.setName("ROLE_STAFF");

			List<Role> role = new ArrayList<>();
			role.add(role0);
			role.add(role1);
			role.add(role3);
			roleRepository.saveAll(role);
		}catch(Exception e) {
			System.out.println("User already exist");
			e.printStackTrace();
		}
	}
}
