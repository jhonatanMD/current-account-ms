package com.current.account.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class EntityTransaction {	
	@NotEmpty(message = "should not be empty")
	private Double cashO;
	
	@NotEmpty(message = "should not be empty")
	private Double cashA;
	
	@NotEmpty(message = "should not be empty")
	private Double cashT;
	
	@NotEmpty(message = "should not be empty")
	private String type;
	
	private Double commi;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING)
	@NotNull
	private Date dateTra;

	


	public Double getCashO() {
		return cashO;
	}

	public void setCashO(Double cashO) {
		this.cashO = cashO;
	}

	public Double getCashA() {
		return cashA;
	}

	public void setCashA(Double cashA) {
		this.cashA = cashA;
	}

	public Double getCashT() {
		return cashT;
	}

	public void setCashT(Double cashT) {
		this.cashT = cashT;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getCommi() {
		return commi;
	}

	public void setCommi(Double commi) {
		this.commi = commi;
	}

	public Date getDateTra() {
		return dateTra;
	}

	public void setDateTra(Date dateTra) {
		this.dateTra = dateTra;
	}


	
}
