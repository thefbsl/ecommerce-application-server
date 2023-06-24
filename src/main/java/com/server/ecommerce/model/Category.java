package com.server.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	private String title;
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "category")
	private Set<Product> product = new HashSet();
}
