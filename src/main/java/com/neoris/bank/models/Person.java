package com.neoris.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "person", schema = "public")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long personId;

	@Column(name = "name")
	private String name;

	@Column(name = "gender")
	private Integer gender;
	
	@Column(name = "age")
	private Integer age;

	@NotBlank(message = "El número de documento no puede estar vacío")
	@Column(name = "document_id")
	private String documentId;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
	@JsonIgnore
    private Client client;
	

}
