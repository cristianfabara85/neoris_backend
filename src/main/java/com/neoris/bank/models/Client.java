package com.neoris.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client", schema = "public")
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	

	@Column(name="password")
	private String password;
	
	@Column(name="state")
	private Integer state;

	@NotNull(message = "La persona no puede ser vacía")
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL )
	@JoinColumn(name="person_id")
	private Person person;

	@OneToMany(mappedBy = "client")
	private List<Account> accounts;
	
}
