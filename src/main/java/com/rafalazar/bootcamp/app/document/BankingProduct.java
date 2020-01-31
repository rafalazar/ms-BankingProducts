package com.rafalazar.bootcamp.app.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection="banking_products")
public class BankingProduct {
	
	@Id
	private String id;
	private String bank;
	private String productName;
	private String clientType;
	private String numAccount;
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
