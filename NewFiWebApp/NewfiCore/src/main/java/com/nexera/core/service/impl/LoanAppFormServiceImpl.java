package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.CustomerEmploymentIncome;
import com.nexera.common.entity.CustomerSpouseBankAccountDetails;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.CustomerSpouseEmploymentIncome;
import com.nexera.common.entity.CustomerSpouseOtherAccountDetails;
import com.nexera.common.entity.CustomerSpouseRetirementAccountDetails;
import com.nexera.common.entity.GovernmentQuestion;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.PropertyTypeMaster;
import com.nexera.common.entity.PurchaseDetails;
import com.nexera.common.entity.RefinanceDetails;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CustomerEmploymentIncomeVO;
import com.nexera.common.vo.CustomerSpouseBankAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseDetailVO;
import com.nexera.common.vo.CustomerSpouseEmploymentIncomeVO;
import com.nexera.common.vo.CustomerSpouseOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseRetirementAccountDetailsVO;
import com.nexera.common.vo.GovernmentQuestionVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.PropertyTypeMasterVO;
import com.nexera.common.vo.PurchaseDetailsVO;
import com.nexera.common.vo.RefinanceVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;

@Component
public class LoanAppFormServiceImpl implements LoanAppFormService {
	@Autowired
	private LoanAppFormDao loanAppFormDao;
	
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private LoanService loanService;
	
	@Override
	@Transactional
	public void save(LoanAppFormVO loaAppFormVO) {
		System.out.println("Inside 5");
		loanAppFormDao.saveOrUpdate(loaAppFormVO.convertToEntity());
	}

	/*@Override
	@Transactional
	public void create(LoanAppFormVO loaAppFormVO) {
		System.out.println("Inside 6");
		loanAppFormDao.save(loaAppFormVO.convertToEntity());

	}*/
	
	@Override
	@Transactional
	public LoanAppForm create(LoanAppFormVO loaAppFormVO) {
		LoanAppForm loanAppForm = loanAppFormDao.saveLoanAppFormWithDetails(loaAppFormVO.convertToEntity());
		
		
		return loanAppForm;
		/*LoanAppForm loanAppForm = null;
		if (loanAppFormID != null && loanAppFormID > 0)
			loanAppForm = loanAppFormDao.findLoanAppForm(loanAppFormID);
*/
		//return this.buildLoanAppFormVO(loanAppForm);
	}

	
	@Override
	@Transactional
	public LoanAppFormVO find(LoanAppFormVO loaAppFormVO) {
		
		
		LoanAppForm loanAppForm = loanAppFormDao.find(parseLoanAppFormVO(loaAppFormVO));
				
		LoanAppFormVO  loanAppFormVO = convertToLoanAppFormVO(loanAppForm);
				
		return loanAppFormVO;
		
	}
	
	 private LoanAppForm parseLoanAppFormVO(LoanAppFormVO loaAppFormVO){
		
		 if(loaAppFormVO == null)
			 return null;
		 
		 LoanAppForm loanAppForm = new LoanAppForm ();
		loanAppForm.setUser(User.convertFromVOToEntity(loaAppFormVO.getUser()));
		 loanAppForm.setLoan(loanService.parseLoanModel(loaAppFormVO.getLoan()));
		 
		 return loanAppForm;
		 
	 }
	 
	 private LoanAppFormVO convertToLoanAppFormVO(LoanAppForm loanAppForm){
			
		 if(loanAppForm == null)
			 return null;
		 
		 LoanAppFormVO loanAppFormVO = convertTOLoanAppFormVOCore(loanAppForm);
		 
		 
		loanAppFormVO
		        .setUser(User.convertFromEntityToVO(loanAppForm.getUser()));
		loanAppFormVO
		        .setLoan(Loan.convertFromEntityToVO(loanAppForm.getLoan()));
		 loanAppFormVO.setPropertyTypeMaster(convertTOPropertyTypeMasterVO(loanAppForm.getPropertyTypeMaster()));
		 
		 loanAppFormVO.setGovernmentquestion(convertTOGovernmentquestionVO(loanAppForm.getGovernmentquestion()));
		 loanAppFormVO.setRefinancedetails(convertTORefinancedetailsVO(loanAppForm.getRefinancedetails()));
		 loanAppFormVO.setLoanType(convertTOLoanTypeVO(loanAppForm.getLoanTypeMaster()));
		 loanAppFormVO.setPurchaseDetails(convertTOPurchaseDetails(loanAppForm.getPurchaseDetails()));
		 loanAppFormVO.setCustomerEmploymentIncome(convertTOCustomerEmploymentIncomeList(loanAppForm.getCustomerEmploymentIncome()));
		 
		 loanAppFormVO.setCustomerSpouseEmploymentIncome(convertTOCustomerSpouseEmploymentIncomeList(loanAppForm.getCustomerSpouseEmploymentIncome()));
		 loanAppFormVO.setCustomerSpouseDetail(convertTOCustomerSpouseDetail(loanAppForm.getCustomerspousedetail()));
		 
		 // spouse Bank detials setting in VO Start
		 
		 loanAppFormVO.setCustomerSpouseBankAccountDetails(convertTOSpouseBankAccountList(loanAppForm.getCustomerSpouseBankAccountDetails()));
		 loanAppFormVO.setCustomerSpouseRetirementAccountDetails(convertTOSpouseRetirementAccountList(loanAppForm.getCustomerSpouseRetirementAccountDetails()));
		 loanAppFormVO.setCustomerSpouseOtherAccountDetails(convertTOSpouseOtherAccountList(loanAppForm.getCustomerSpouseOtherAccountDetails()));
		 
		 // Ends
		 
		 return loanAppFormVO;
		 
	 }


	private List<CustomerSpouseOtherAccountDetailsVO> convertTOSpouseOtherAccountList(
            List<CustomerSpouseOtherAccountDetails> spouseOtherAccountDetailsList) {
	    
		if(spouseOtherAccountDetailsList ==  null || spouseOtherAccountDetailsList.size() == 0)
			return null;
		
		List<CustomerSpouseOtherAccountDetailsVO> spouseRetirementAccountDetailsVOList = new ArrayList<CustomerSpouseOtherAccountDetailsVO>();
		
		CustomerSpouseOtherAccountDetailsVO spouseOtherAccountDetailsVO = null;
		CustomerSpouseOtherAccountDetails spouseOtherAccountDetails = null;
		for (int i = 0; i < spouseOtherAccountDetailsList.size(); i++) {
			
			spouseOtherAccountDetails = spouseOtherAccountDetailsList.get(i);
			
			spouseOtherAccountDetailsVO = convertTOSpouseOtherAccountDetailsVO(spouseOtherAccountDetails);
			spouseRetirementAccountDetailsVOList.add(spouseOtherAccountDetailsVO);
		}
		
	    return spouseRetirementAccountDetailsVOList;
    }

	private CustomerSpouseOtherAccountDetailsVO convertTOSpouseOtherAccountDetailsVO(
            CustomerSpouseOtherAccountDetails spouseOtherAccountDetails) {
	    
		if(null ==spouseOtherAccountDetails)
			return null;
		
		CustomerSpouseOtherAccountDetailsVO spouseOtherAccountDetailsVO = new CustomerSpouseOtherAccountDetailsVO();
		
		spouseOtherAccountDetailsVO.setId(spouseOtherAccountDetails.getId());
		spouseOtherAccountDetailsVO.setAccountSubType(spouseOtherAccountDetails.getAccountSubType());
		spouseOtherAccountDetailsVO.setAmountForNewHome(spouseOtherAccountDetails.getAmountfornewhome());
		spouseOtherAccountDetailsVO.setCurrentAccountBalance(spouseOtherAccountDetails.getCurrentaccountbalance());
		
	    return spouseOtherAccountDetailsVO;
    }

	private List<CustomerSpouseRetirementAccountDetailsVO> convertTOSpouseRetirementAccountList(
            List<CustomerSpouseRetirementAccountDetails> spouseRetirementAccountDetailsList) {
	    
		if(spouseRetirementAccountDetailsList ==  null || spouseRetirementAccountDetailsList.size() == 0)
			return null;
		
		List<CustomerSpouseRetirementAccountDetailsVO> spouseRetirementAccountDetailsVOList = new ArrayList<CustomerSpouseRetirementAccountDetailsVO>();
		
		CustomerSpouseRetirementAccountDetailsVO customerSpouseBankAccountDetailsVO = null;
		CustomerSpouseRetirementAccountDetails spouseRetirementAccountDetails = null;
		for (int i = 0; i < spouseRetirementAccountDetailsList.size(); i++) {
			
			spouseRetirementAccountDetails = spouseRetirementAccountDetailsList.get(i);
			
			customerSpouseBankAccountDetailsVO = convertTOSpouseRetirementAccountVO(spouseRetirementAccountDetails);
			spouseRetirementAccountDetailsVOList.add(customerSpouseBankAccountDetailsVO);
		}
		
	    return spouseRetirementAccountDetailsVOList;
    }

	private CustomerSpouseRetirementAccountDetailsVO convertTOSpouseRetirementAccountVO(
            CustomerSpouseRetirementAccountDetails spouseRetirementAccountDetails) {
	  
		if(null == spouseRetirementAccountDetails)
			return null;
		
		CustomerSpouseRetirementAccountDetailsVO spouseRetirementAccountDetailsVO = new CustomerSpouseRetirementAccountDetailsVO();
		
		spouseRetirementAccountDetailsVO.setId(spouseRetirementAccountDetails.getId());
		spouseRetirementAccountDetailsVO.setAccountSubType(spouseRetirementAccountDetails.getAccountSubType());
		spouseRetirementAccountDetailsVO.setAmountForNewHome(spouseRetirementAccountDetails.getAmountfornewhome());
		spouseRetirementAccountDetailsVO.setCurrentAccountBalance(spouseRetirementAccountDetails.getCurrentaccountbalance());
		
	    return spouseRetirementAccountDetailsVO;
    }

	private List<CustomerSpouseBankAccountDetailsVO> convertTOSpouseBankAccountList(
            List<CustomerSpouseBankAccountDetails> customerSpouseBankAccountDetailslist) {
	   
		if(customerSpouseBankAccountDetailslist ==  null || customerSpouseBankAccountDetailslist.size() == 0)
			return null;
		
		List<CustomerSpouseBankAccountDetailsVO> customerSpouseBankAccountDetailsVOList = new ArrayList<CustomerSpouseBankAccountDetailsVO>();
		CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO = null;
		CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails = null;
		for (int i = 0; i < customerSpouseBankAccountDetailslist.size(); i++) {
			
			customerSpouseBankAccountDetails = customerSpouseBankAccountDetailslist.get(i);
			
			customerSpouseBankAccountDetailsVO = convertTOCustomerEmploymentIncomeVO(customerSpouseBankAccountDetails);
			customerSpouseBankAccountDetailsVOList.add(customerSpouseBankAccountDetailsVO);
		}
		
	    return customerSpouseBankAccountDetailsVOList;
    }

	private CustomerSpouseBankAccountDetailsVO convertTOCustomerEmploymentIncomeVO(
            CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails) {
	   
		
		if(null == customerSpouseBankAccountDetails)
			return null;
		
		CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO = new CustomerSpouseBankAccountDetailsVO();
		customerSpouseBankAccountDetailsVO.setId(customerSpouseBankAccountDetails.getId());
		customerSpouseBankAccountDetailsVO.setAccountSubType(customerSpouseBankAccountDetails.getAccountSubType());
		customerSpouseBankAccountDetailsVO.setAmountForNewHome(customerSpouseBankAccountDetails.getAmountfornewhome());
		customerSpouseBankAccountDetailsVO.setCurrentAccountBalance(customerSpouseBankAccountDetails.getCurrentaccountbalance());
	    return customerSpouseBankAccountDetailsVO;
    }

	private CustomerSpouseDetailVO convertTOCustomerSpouseDetail(
            CustomerSpouseDetail customerspousedetail) {
		if (null == customerspousedetail)
			return null;
		CustomerSpouseDetailVO customerSpouseDetailVO = new CustomerSpouseDetailVO();
		customerSpouseDetailVO.setId(customerspousedetail.getId());
		if(customerspousedetail.getSpouseDateOfBirth()!=null)
			customerSpouseDetailVO.setSpouseDateOfBirth(customerspousedetail.getSpouseDateOfBirth().getTime());
		customerSpouseDetailVO.setSpouseSsn(customerspousedetail.getSpouseSsn());
		customerSpouseDetailVO.setSpouseSecPhoneNumber(customerspousedetail.getSpouseSecPhoneNumber());
		customerSpouseDetailVO.setSpouseName(customerspousedetail.getSpouseName());
		customerSpouseDetailVO.setSelfEmployed(customerspousedetail.isSelfEmployed());
		customerSpouseDetailVO.setIsssIncomeOrDisability(customerspousedetail.isIsssIncomeOrDisability());
		customerSpouseDetailVO.setIspensionOrRetirement(customerspousedetail.isIs_pension_or_retirement());
		customerSpouseDetailVO.setSelfEmployedIncome(customerspousedetail.getSelfEmployedIncome());
		customerSpouseDetailVO.setSsDisabilityIncome(customerspousedetail.getSsDisabilityIncome());
		customerSpouseDetailVO.setMonthlyPension(customerspousedetail.getMonthlyPension());
		customerSpouseDetailVO.setExperianScore(customerspousedetail.getExperianScore());
		customerSpouseDetailVO.setEquifaxScore(customerspousedetail.getEquifaxScore());
		customerSpouseDetailVO.setTransunionScore(customerspousedetail.getTransunionScore());
	    return customerSpouseDetailVO;
    }

	private List<CustomerSpouseEmploymentIncomeVO> convertTOCustomerSpouseEmploymentIncomeList(
            List<CustomerSpouseEmploymentIncome> customerSpouseEmploymentIncomelist) {
		
		if(customerSpouseEmploymentIncomelist ==  null || customerSpouseEmploymentIncomelist.size() == 0)
			return null;
		
		List<CustomerSpouseEmploymentIncomeVO> customerSpouseEmploymentIncomeVOlist = new ArrayList<CustomerSpouseEmploymentIncomeVO>();
		
		CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentIncomeVO = null;
		CustomerSpouseEmploymentIncome customerSpouseEmploymentIncome = null;
		for (int i = 0; i < customerSpouseEmploymentIncomelist.size(); i++) {
			
			customerSpouseEmploymentIncome = customerSpouseEmploymentIncomelist.get(i);
			
			customerSpouseEmploymentIncomeVO = convertTOCustomerSpouseEmploymentIncomeVO(customerSpouseEmploymentIncome);
			customerSpouseEmploymentIncomeVOlist.add(customerSpouseEmploymentIncomeVO);
		}
		
	    return customerSpouseEmploymentIncomeVOlist;
    }

	private CustomerSpouseEmploymentIncomeVO convertTOCustomerSpouseEmploymentIncomeVO(
            CustomerSpouseEmploymentIncome customerSpouseEmploymentIncome) {
		
		if(null == customerSpouseEmploymentIncome)
			return null;
		
		CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentIncomeVO = new CustomerSpouseEmploymentIncomeVO();
		CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentIncomeVOTemp = new CustomerSpouseEmploymentIncomeVO();
		

		customerSpouseEmploymentIncomeVOTemp.setId(customerSpouseEmploymentIncome.getId());
		customerSpouseEmploymentIncomeVOTemp.setEmployedSince(customerSpouseEmploymentIncome.getEmployedSince());
		customerSpouseEmploymentIncomeVOTemp.setEmployedAt(customerSpouseEmploymentIncome.getEmployedAt());		
		customerSpouseEmploymentIncomeVOTemp.setEmployedIncomePreTax(customerSpouseEmploymentIncome.getEmployedIncomePreTax());
		
		customerSpouseEmploymentIncomeVO.setCustomerSpouseEmploymentIncome(customerSpouseEmploymentIncomeVOTemp);
	    return customerSpouseEmploymentIncomeVO;
    }

	private List<CustomerEmploymentIncomeVO> convertTOCustomerEmploymentIncomeList(
            List<CustomerEmploymentIncome> customerEmploymentIncomelist) {
	  
		if(customerEmploymentIncomelist ==  null || customerEmploymentIncomelist.size() == 0)
			return null;
		
		List<CustomerEmploymentIncomeVO> customerEmploymentIncomeVOlist = new ArrayList<CustomerEmploymentIncomeVO>();
		CustomerEmploymentIncomeVO customerEmploymentIncomeVO = null;
		CustomerEmploymentIncome customerEmploymentIncome = null;
		for (int i = 0; i < customerEmploymentIncomelist.size(); i++) {
			
			customerEmploymentIncome = customerEmploymentIncomelist.get(i);
			
			customerEmploymentIncomeVO = convertTOCustomerEmploymentIncomeVO(customerEmploymentIncome);
			customerEmploymentIncomeVOlist.add(customerEmploymentIncomeVO);
		}
		
	    return customerEmploymentIncomeVOlist;
    }
	

	private CustomerEmploymentIncomeVO convertTOCustomerEmploymentIncomeVO(
            CustomerEmploymentIncome customerEmploymentIncome) {
	   
		if(null == customerEmploymentIncome)
			return null;
		
		CustomerEmploymentIncomeVO customerEmploymentIncomeVO = new CustomerEmploymentIncomeVO();
		CustomerEmploymentIncomeVO customerEmploymentITemp = new CustomerEmploymentIncomeVO();
		

		customerEmploymentITemp.setId(customerEmploymentIncome.getId());
		customerEmploymentITemp.setEmployedSince(customerEmploymentIncome.getEmployedSince());
		customerEmploymentITemp.setEmployedAt(customerEmploymentIncome.getEmployedAt());		
		customerEmploymentITemp.setEmployedIncomePreTax(customerEmploymentIncome.getEmployedIncomePreTax());
		customerEmploymentIncomeVO.setCustomerEmploymentIncome(customerEmploymentITemp);
	    return customerEmploymentIncomeVO;
    }

	private PurchaseDetailsVO convertTOPurchaseDetails( PurchaseDetails purchaseDetails) {
	
		if(purchaseDetails == null){
			 return null;
		 }
		PurchaseDetailsVO  purchaseDetailsVO= new PurchaseDetailsVO ();
		purchaseDetailsVO.setId(purchaseDetails.getId());
		purchaseDetailsVO.setBuyhomeZipPri(purchaseDetails.getBuyhomeZipPri());
		purchaseDetailsVO.setBuyhomeZipSec(purchaseDetails.getBuyhomeZipSec());
		purchaseDetailsVO.setBuyhomeZipTri(purchaseDetails.getBuyhomeZipTri());
		purchaseDetailsVO.setEstimatedPrice(purchaseDetails.getEstimatedPrice());
		purchaseDetailsVO.setHousePrice(purchaseDetails.getHousePrice());
		purchaseDetailsVO.setLivingSituation(purchaseDetails.getLivingSituation());
		purchaseDetailsVO.setLoanAmount(purchaseDetails.getLoanAmount());
		purchaseDetailsVO.setTaxAndInsuranceInLoanAmt(purchaseDetails.isTaxAndInsuranceInLoanAmt());
		
		
	    return purchaseDetailsVO;
    }

	private LoanAppFormVO convertTOLoanAppFormVOCore(LoanAppForm loanAppForm){
		 
		if(null == loanAppForm)
			return null;
		
		 LoanAppFormVO loanAppFormVO = new  LoanAppFormVO ();
		 
		 loanAppFormVO.setId(loanAppForm.getId());
		 loanAppFormVO.setIsEmployed(loanAppForm.getIsEmployed());
		 loanAppFormVO.setEmployedIncomePreTax(loanAppForm.getEmployedIncomePreTax());
		 loanAppFormVO.setEmployedAt(loanAppForm.getEmployedAt());
		 loanAppFormVO.setEmployedSince(loanAppForm.getEmployedSince());
		 loanAppFormVO.setHoaDues(loanAppForm.getHoaDues());
		 loanAppFormVO.setHomeRecentlySold(loanAppForm.getHomeRecentlySold());
		 loanAppFormVO.setHomeToSell(loanAppForm.getHomeToSell());
		 loanAppFormVO.setMaritalStatus(loanAppForm.getMaritalStatus());
		 loanAppFormVO.setOwnsOtherProperty(loanAppForm.getOwnsOtherProperty());
		 loanAppFormVO.setIspensionOrRetirement(loanAppForm.getIspensionOrRetirement());
		 loanAppFormVO.setMonthlyPension(loanAppForm.getMonthlyPension());
		 loanAppFormVO.setReceiveAlimonyChildSupport(loanAppForm.getReceiveAlimonyChildSupport());
		 loanAppFormVO.setRentedOtherProperty(loanAppForm.getRentedOtherProperty());
		 loanAppFormVO.setSecondMortgage(loanAppForm.getSecondMortgage());
		 loanAppFormVO.setIsselfEmployed(loanAppForm.getIsselfEmployed());
		 loanAppFormVO.setSelfEmployedIncome(loanAppForm.getSelfEmployedIncome());
		 loanAppFormVO.setIsssIncomeOrDisability(loanAppForm.getIsssIncomeOrDisability());
		 loanAppFormVO.setSsDisabilityIncome(loanAppForm.getSsDisabilityIncome());
		 loanAppFormVO.setIsSpouseOnLoan(loanAppForm.getIsSpouseOnLoan());
		 loanAppFormVO.setSpouseName(loanAppForm.getSpouseName());
		 loanAppFormVO.setPaySecondMortgage(loanAppForm.getPaySecondMortgage());
		 loanAppFormVO.setLoanAppFormCompletionStatus(loanAppForm.getLoanAppFormCompletionStatus());
		loanAppFormVO.setMonthlyRent(loanAppForm.getMonthlyRent());
		 return loanAppFormVO;
		 
	 }

	 
	 private PropertyTypeMasterVO convertTOPropertyTypeMasterVO(PropertyTypeMaster propertyTypeMaster){
		 
		 if(propertyTypeMaster == null){
			 return null;
		 }
		 
		 PropertyTypeMasterVO propertyTypeMasterVO = new PropertyTypeMasterVO();
		 propertyTypeMasterVO.setId(propertyTypeMaster.getId());
		 propertyTypeMasterVO.setDescription(propertyTypeMaster.getDescription());
		 propertyTypeMasterVO.setModifiedDate(propertyTypeMaster.getModifiedDate());
		 propertyTypeMasterVO.setPropertyTypeCd(propertyTypeMaster.getPropertyTypeCd());
		 propertyTypeMasterVO.setResidenceTypeCd(propertyTypeMaster.getResidenceTypeCd());
		 propertyTypeMasterVO.setPropertyTaxesPaid(propertyTypeMaster.getPropertyTaxesPaid());
		 propertyTypeMasterVO.setPropertyInsuranceProvider(propertyTypeMaster.getPropertyInsuranceProvider());
		 propertyTypeMasterVO.setPropertyInsuranceCost(propertyTypeMaster.getPropertyInsuranceCost());
		 propertyTypeMasterVO.setPropertyPurchaseYear(propertyTypeMaster.getPropertyPurchaseYear());
		 propertyTypeMasterVO.setHomeWorthToday(propertyTypeMaster.getHomeWorthToday());
		 
		 return propertyTypeMasterVO;
		 
	 }

	 
	 private GovernmentQuestionVO convertTOGovernmentquestionVO(GovernmentQuestion governmentquestion) {
	  
		 if(null == governmentquestion)
			 return null;
		 
		 GovernmentQuestionVO governmentQuestionVO = new GovernmentQuestionVO();
		 
		 governmentQuestionVO.setId(governmentquestion.getId());
		 governmentQuestionVO.setOutstandingJudgments(governmentquestion.isOutstandingJudgments());
		 governmentQuestionVO.setBankrupt(governmentquestion.isBankrupt());
		 governmentQuestionVO.setPropertyForeclosed(governmentquestion.isPropertyForeclosed());
		 governmentQuestionVO.setLawsuit(governmentquestion.isLawsuit());
		 governmentQuestionVO.setObligatedLoan(governmentquestion.isObligatedLoan());
		 governmentQuestionVO.setFederalDebt(governmentquestion.isFederalDebt());
		 governmentQuestionVO.setEndorser(governmentquestion.isEndorser());
		 governmentQuestionVO.setUSCitizen(governmentquestion.isUSCitizen());
		 governmentQuestionVO.setOccupyPrimaryResidence(governmentquestion.isOccupyPrimaryResidence());
		 governmentQuestionVO.setOwnershipInterestInProperty(governmentquestion.isOwnershipInterestInProperty());
		 governmentQuestionVO.setEthnicity(governmentquestion.getEthnicity());
		 governmentQuestionVO.setRace(governmentquestion.getRace());
		 governmentQuestionVO.setSex(governmentquestion.getSex());
		 	 
	    return governmentQuestionVO;
    }

      
	 private RefinanceVO convertTORefinancedetailsVO( RefinanceDetails refinancedetails) {
		   
		 if (refinancedetails == null)
			 return null;
		 RefinanceVO refinanceVO = new RefinanceVO();
		 refinanceVO.setId(refinancedetails.getId());
		 refinanceVO.setRefinanceOption(refinancedetails.getRefinanceOption());
		 refinanceVO.setCurrentMortgageBalance(refinancedetails.getCurrentMortgageBalance());
		 refinanceVO.setCurrentMortgagePayment(refinancedetails.getCurrentMortgagePayment());
		 refinanceVO.setIncludeTaxes(refinancedetails.isIncludeTaxes());
		 refinanceVO.setSecondMortageBalance(refinancedetails.getSecondMortageBalance());
		 refinanceVO.setMortgageyearsleft(refinancedetails.getMortgageyearsleft());
		 refinanceVO.setCashTakeOut(refinancedetails.getCashTakeOut());
		  
		 return refinanceVO;
	    }

	 

		private LoanTypeMasterVO convertTOLoanTypeVO(LoanTypeMaster loanTypeMaster) {
		    
			if (loanTypeMaster == null)
				return null;
			LoanTypeMasterVO loanTypeMasterVO = new LoanTypeMasterVO();
			loanTypeMasterVO.setId(loanTypeMaster.getId());
			loanTypeMasterVO.setDescription(loanTypeMaster.getDescription());
			loanTypeMasterVO.setLoanTypeCd(loanTypeMaster.getLoanTypeCd());
			loanTypeMasterVO.setModifiedDate(loanTypeMaster.getModifiedDate());
			
		    return loanTypeMasterVO;
	    }

	@Override
	@Transactional
	public LoanAppForm findByLoan(Loan loan){
		return loanAppFormDao.findByLoan(loan);
	}

	@Override
	@Transactional
	public LoanAppForm findByuserID(int userid) {
		return loanAppFormDao.findByuserID(userid);
	}

}


