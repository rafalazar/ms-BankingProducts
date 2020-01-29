package com.rafalazar.bootcamp.app.dto;

import java.util.Date;



import lombok.Data;

@Data
public class ClientDto {
	
	private String id;
	private String name;
	private String lasName;
	private String dni;
	private String ruc;
	private String socialName;
	private String address;
	private String type;
	private Date joinAt;

}
