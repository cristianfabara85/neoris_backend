package com.neoris.bank.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

	private Integer code;
	private String message;
	private Object data;
	
}
