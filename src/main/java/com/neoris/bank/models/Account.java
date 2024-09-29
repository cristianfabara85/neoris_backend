package com.neoris.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account", schema = "public")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name="client_id")
	private Long clientId;

	@Column(name="number")
	private Long number;

	@Column(name="state")
	private Integer state;

	@NotNull(message = "El tipo no puede estar vacío")
	@Column(name="type")
	private Integer type;

	@Column(name="initial_balance")
	private BigDecimal initialBalance;

	@ManyToOne
	@JoinColumn(name="client_id",insertable = false,updatable = false, nullable=false)
	@JsonIgnore
	private Client client;

	@OneToMany(mappedBy = "account")
	private List<Movement> movements;

}
