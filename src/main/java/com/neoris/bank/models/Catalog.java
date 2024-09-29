package com.neoris.bank.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "catalog", schema = "public")
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	@Column(name="description")
	private String description;
	
}
