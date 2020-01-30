package com.rafalazar.bootcamp.app.dto;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class ClientDto {
	
	private String id;
	private String fullName;
	private String numDoc;
	private String address;
	private String bank;
	private String type;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joinAt;

}
