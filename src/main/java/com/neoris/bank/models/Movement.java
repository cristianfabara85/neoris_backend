package com.neoris.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "movements", schema = "public")
public class Movement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "account_id")
	private Long accountId;

	@Column(name = "type")
	private Integer type;

	@NotNull(message = "El monto no puede estar vacío")
	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "transaction_date")
	private Instant transactionDate;

	@ManyToOne
	@JoinColumn(name = "account_id", insertable = false, updatable = false, nullable = false)
	@JsonIgnore
	private Account account;
}
