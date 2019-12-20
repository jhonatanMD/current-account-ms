package com.current.account.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CurrentAccount")
public class CurrentEntity {
	
	@Id
	private String codCur;
	
	private String numAcc;
	
	private Double cash;
	
	private String typeCli;
	
	private String profile;
	
	private Double cashEndMonth;
	
	private int numTran;
	
	private Double commi;
	
	private String bank;
	private String status;
	
	
	private List<HeadLineEntity> heads;
	
	private List<SignatoriesEntity> sigs;

	public String getCodCur() {
		return codCur;
	}

	public void setCodCur(String codCur) {
		this.codCur = codCur;
	}

	public String getNumAcc() {
		return numAcc;
	}

	public void setNumAcc(String numAcc) {
		this.numAcc = numAcc;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}
	
	
	public String getTypeCli() {
		return typeCli;
	}

	public void setTypeCli(String typeCli) {
		this.typeCli = typeCli;
	}

	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	
	public Double getCashEndMonth() {
		return cashEndMonth;
	}

	public void setCashEndMonth(Double cashEndMonth) {
		this.cashEndMonth = cashEndMonth;
	}

	public int getNumTran() {
		return numTran;
	}

	public void setNumTran(int numTran) {
		this.numTran = numTran;
	}

	public Double getCommi() {
		return commi;
	}

	public void setCommi(Double commi) {
		this.commi = commi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public List<HeadLineEntity> getHeads() {
		return heads;
	}

	public void setHeads(List<HeadLineEntity> heads) {
		this.heads = heads;
	}

	public List<SignatoriesEntity> getSigs() {
		return sigs;
	}

	public void setSigs(List<SignatoriesEntity> sigs) {
		this.sigs = sigs;
	}
	
	

}
