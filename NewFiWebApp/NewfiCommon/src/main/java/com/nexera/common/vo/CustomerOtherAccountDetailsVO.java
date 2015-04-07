
package com.nexera.common.vo;

import java.io.Serializable;

public class CustomerOtherAccountDetailsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String accountSubType;
	private String   currentAccountBalance;
	private String   amountForNewHome;
	private CustomerOtherAccountDetailsVO customerOtherAccountDetails;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountSubType() {
		return accountSubType;
	}
	public void setAccountSubType(String accountSubType) {
		this.accountSubType = accountSubType;
	}
	public String getCurrentAccountBalance() {
		return currentAccountBalance;
	}
	public void setCurrentAccountBalance(String currentAccountBalance) {
		this.currentAccountBalance = currentAccountBalance;
	}
	public String getAmountForNewHome() {
		return amountForNewHome;
	}
	public void setAmountForNewHome(String amountForNewHome) {
		this.amountForNewHome = amountForNewHome;
	}
	public CustomerOtherAccountDetailsVO getCustomerOtherAccountDetails() {
		return customerOtherAccountDetails;
	}
	public void setCustomerOtherAccountDetails(
			CustomerOtherAccountDetailsVO customerOtherAccountDetails) {
		this.customerOtherAccountDetails = customerOtherAccountDetails;
	}
	
	
}