package com.rafalazar.bootcamp.app.document;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection="banking_products")
public class BankingProduct {
	
	@Id
	private String id;
	@NotEmpty(message = "El campo banco no puede estar en blanco")
	private String bank;
	private String productName;
	private String clientType;
	private String numAccount;
	@NotEmpty(message = "El campo nameOwner no puede estar en blanco")
	private String nameOwner;
	private String numDoc;
	private Double amount;
	//Nuevo campo
	private Double amountAvailable;
	//private Double depositAmount;
	//private Double retiroAmount;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date jointAt;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateAt;

}
