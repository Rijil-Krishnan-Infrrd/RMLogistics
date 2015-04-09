package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserVO implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(UserVO.class);
	private static final long serialVersionUID = 1L;
	private int id;
	private int defaultLoanId;
	private Boolean status;
	private String emailId;
	private String firstName;
	private String lastName;
	private String displayName;
	private String password;
	private String phoneNumber;
	private String photoImageUrl;
	private String username;
	private UserRoleVO userRole;
	private CustomerDetailVO customerDetail;
	private InternalUserDetailVO internalUserDetail;
	private RealtorDetailVO realtorDetail;
	private List<LoanVO> loans;
	private List<LoanAppFormVO> loanAppForms;
	private List<NotificationVO> loanNotifications;
	private List<LoanTeamVO> loanTeams;
	private List<UserEmailVO> userEmails;
	private CustomerEnagagement customerEnagagement;
	private List<InternalUserStateMappingVO> internalUserStateMappingVOs;

	public UserVO() {

	}

	public UserVO(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getDisplayName() {
		if (displayName == null || displayName.isEmpty()) {
			return this.firstName + " " + this.lastName;
		}
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhotoImageUrl() {
		return photoImageUrl;
	}

	public void setPhotoImageUrl(String photoImageUrl) {
		this.photoImageUrl = photoImageUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRoleVO getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleVO userRole) {
		this.userRole = userRole;
	}

	public CustomerDetailVO getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(CustomerDetailVO customerDetail) {
		this.customerDetail = customerDetail;
	}

	public InternalUserDetailVO getInternalUserDetail() {
		return internalUserDetail;
	}

	public void setInternalUserDetail(InternalUserDetailVO internalUserDetail) {
		this.internalUserDetail = internalUserDetail;
	}

	public RealtorDetailVO getRealtorDetail() {
		return realtorDetail;
	}

	public void setRealtorDetail(RealtorDetailVO realtorDetail) {
		this.realtorDetail = realtorDetail;
	}

	public List<LoanVO> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanVO> loans) {
		this.loans = loans;
	}

	public List<LoanAppFormVO> getLoanAppForms() {
		return loanAppForms;
	}

	public void setLoanAppForms(List<LoanAppFormVO> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	public List<NotificationVO> getLoanNotifications() {
		return loanNotifications;
	}

	public void setLoanNotifications(List<NotificationVO> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}

	public List<LoanTeamVO> getLoanTeams() {
		return loanTeams;
	}

	public void setLoanTeams(List<LoanTeamVO> loanTeams) {
		this.loanTeams = loanTeams;
	}

	public List<UserEmailVO> getUserEmails() {
		return userEmails;
	}

	public void setUserEmails(List<UserEmailVO> userEmails) {
		this.userEmails = userEmails;
	}

	public CustomerEnagagement getCustomerEnagagement() {
		return customerEnagagement;
	}

	public void setCustomerEnagagement(CustomerEnagagement customerEnagagement) {
		this.customerEnagagement = customerEnagagement;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", status=" + status + ", emailId="
		        + emailId + ", firstName=" + firstName + ", lastName="
		        + lastName + ", password=" + password + ", phoneNumber="
		        + phoneNumber + ", photoImageUrl=" + photoImageUrl
		        + ", username=" + username + ", userRole=" + userRole
		        + ", customerDetail=" + customerDetail
		        + ", internalUserDetail=" + internalUserDetail
		        + ", realtorDetail=" + realtorDetail + ", loans=" + loans
		        + ", loanAppForms=" + loanAppForms + ", loanNotifications="
		        + loanNotifications + ", loanTeams=" + loanTeams
		        + ", userEmails=" + userEmails + "]";
	}

	public int getDefaultLoanId() {
		return defaultLoanId;
	}

	public void setDefaultLoanId(int defaultLoanId) {
		this.defaultLoanId = defaultLoanId;
	}

	public List<InternalUserStateMappingVO> getInternalUserStateMappingVOs() {
		return internalUserStateMappingVOs;
	}

	public void setInternalUserStateMappingVOs(
	        List<InternalUserStateMappingVO> internalUserStateMappingVOs) {
		this.internalUserStateMappingVOs = internalUserStateMappingVOs;
	}

}