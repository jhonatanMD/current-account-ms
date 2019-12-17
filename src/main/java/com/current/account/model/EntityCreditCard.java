package com.current.account.model;

import org.springframework.data.annotation.Id;




public class EntityCreditCard {

	@Id
	private String codCur;
	
	private String numAcc;
	
	private Double cash;
	
	private String dniCli;
	



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

	public String getDniCli() {
		return dniCli;
	}

	public void setDniCli(String dniCli) {
		this.dniCli = dniCli;
	}


	


}
