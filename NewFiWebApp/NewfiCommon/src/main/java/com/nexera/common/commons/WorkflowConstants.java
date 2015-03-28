/**
 * 
 */
package com.nexera.common.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.nexera.common.enums.LOSLoanStatus;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.WorkItemMilestoneInfo;

/**
 * @author Utsav T
 */
public class WorkflowConstants {
	public static final String PROPERTY_FILE_NAME = "application";

	public static final String POOL_SIZE = "pool.size";

	public static final String SUCCESS = "success";

	public static final String FAILURE = "failure";

	public static final String PENDING = "pending";

	public static final String RENDER_STATE_INFO_METHOD = "renderStateInfo";

	public static final String CHECK_STATUS_METHOD = "checkStatus";

	public static final String INVOKE_ACTION_METHOD = "invokeAction";

	public static final String EXECUTE_METHOD = "execute";

	public static final String LOAN_MANAGER_WORKFLOW_TYPE = "LM_WF_ALL";

	public static final String CUSTOMER_WORKFLOW_TYPE = "CUST_WF_ALL";

	public static final String WORKFLOW_ITEM_INITIAL_CONTACT = "INITIAL_CONTACT";
	public static final String WORKFLOW_ITEM_UW_STATUS = "UW_STATUS";
	public static final String WORKFLOW_ITEM_VIEW_UW = "VIEW_UW";

	public static final String WORKFLOW_ITEM_TEAM_STATUS = "TEAM_STATUS";
	public static final String WORKFLOW_ITEM_SYSTEM_EDU = "SYSTEM_EDU";
	public static final String WORKFLOW_ITEM_RATES_EDU = "RATES_EDU";
	public static final String WORKFLOW_ITEM_QC_STATUS = "QC_STATUS";
	public static final String WORKFLOW_ITEM_PROFILE_INFO = "PROFILE_INFO";
	public static final String WORKFLOW_ITEM_NEEDS_STATUS = "NEEDS_STATUS";
	public static final String WORKFLOW_ITEM_NEEDS_EDU = "NEEDS_EDU";

	public static final String WORKFLOW_ITEM_LOCK_RATE = "LOCK_RATE";
	public static final String WORKFLOW_ITEM_LOAN_PROGRESS = "LOAN_PROGRESS";
	public static final String WORKFLOW_ITEM_DISCLOSURE_STATUS = "DISCLOSURE_STATUS";
	public static final String WORKFLOW_ITEM_DISCLOSURE_DISPLAY = "DISCLOSURE_DISPLAY";
	public static final String WORKFLOW_ITEM_CREDIT_SCORE = "CREDIT_SCORE";
	public static final String WORKFLOW_ITEM_CREDIT_BUREAU = "CREDIT_BUREAU";
	public static final String WORKFLOW_ITEM_COMM_EDU = "COMM_EDU";
	public static final String WORKFLOW_ITEM_CLOSURE_STATUS = "CLOSURE_STATUS";
	public static final String WORKFLOW_ITEM_CLOSURE_DISPLAY = "VIEW_CLOSING";
	public static final String WORKFLOW_ITEM_AUS_STATUS = "AUS_STATUS";

	public static final String WORKFLOW_ITEM_APPRAISAL_STATUS = "APPRAISAL_STATUS";
	public static final String WORKFLOW_ITEM_VIEW_APPRAISAL = "VIEW_APPRAISAL";

	public static final String WORKFLOW_ITEM_APP_FEE = "APP_FEE";
	public static final String WORKFLOW_ITEM_APP_EDU = "APP_EDU";

	public static final String WORKFLOW_ITEM_1003_COMPLETE = "1003_COMPLETE";
	public static final String WORKFLOW_ITEM_1003_DISPLAY = "1003_DISPLAY";
	public static final HashMap<LOSLoanStatus, WorkItemMilestoneInfo> LQB_STATUS_MILESTONE_LOOKUP = new HashMap<LOSLoanStatus, WorkItemMilestoneInfo>();;

	public static final HashMap<Milestones, List<String>> MILESTONE_WF_ITEM_LOOKUP = new HashMap<Milestones, List<String>>();;

	static {

		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.App1003,
		        new ArrayList<String>(Arrays
		                .asList(WORKFLOW_ITEM_1003_COMPLETE,
		                        WORKFLOW_ITEM_1003_DISPLAY)));

		MILESTONE_WF_ITEM_LOOKUP.put(Milestones.AUSUW, null);

		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.DISCLOSURE,
		        new ArrayList<String>(Arrays.asList(
		                WORKFLOW_ITEM_DISCLOSURE_STATUS,
		                WORKFLOW_ITEM_DISCLOSURE_DISPLAY)));

		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.APPRAISAL,
		        new ArrayList<String>(Arrays.asList(
		                WORKFLOW_ITEM_APPRAISAL_STATUS,
		                WORKFLOW_ITEM_VIEW_APPRAISAL)));
		MILESTONE_WF_ITEM_LOOKUP.put(Milestones.UW, new ArrayList<String>(
		        Arrays.asList(WORKFLOW_ITEM_UW_STATUS, WORKFLOW_ITEM_VIEW_UW)));
		MILESTONE_WF_ITEM_LOOKUP.put(
		        Milestones.LOAN_CLOSURE,
		        new ArrayList<String>(Arrays.asList(
		                WORKFLOW_ITEM_CLOSURE_STATUS,
		                WORKFLOW_ITEM_CLOSURE_DISPLAY)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK_FAILED,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_DOCUMENT_CHECK_FAILED,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_UNDERWRITING,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_IN_UNDERWRITING,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_APPROVED,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_CONDITION_REVIEW,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_FINAL_UNDER_WRITING,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_FINAL_DOCS,
		        new WorkItemMilestoneInfo(Milestones.UW,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.UW)));

		// For Appraisal

		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_ORDERED,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_DRAWN,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_OUT,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_DOCS_BACK,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_SUBMITTED_FOR_PURCHASE_REVIEW,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_IN_PURCHASE_REVIEW,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_PRE_PURCHASE_CONDITIONS,
		        new WorkItemMilestoneInfo(Milestones.APPRAISAL,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.APPRAISAL)));
		// QC

		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_PRE_DOC_QC,
		        new WorkItemMilestoneInfo(Milestones.QC,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.QC)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_CLEAR_TO_CLOSE,
		        new WorkItemMilestoneInfo(Milestones.QC,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.QC)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_CLEAR_TO_PURCHASE,
		        new WorkItemMilestoneInfo(Milestones.QC,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.QC)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_PURCHASED,
		        new WorkItemMilestoneInfo(Milestones.QC,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.QC)));

		// Loan Closure

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_SUSPENDED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_DENIED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE)));
		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_WITHDRAWN,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_ARCHIVED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE)));
		LQB_STATUS_MILESTONE_LOOKUP.put(LOSLoanStatus.LQB_STATUS_LOAN_CLOSED,
		        new WorkItemMilestoneInfo(Milestones.LOAN_CLOSURE,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.LOAN_CLOSURE)));

		// App1003

		LQB_STATUS_MILESTONE_LOOKUP.put(
		        LOSLoanStatus.LQB_STATUS_LOAN_SUBMITTED,
		        new WorkItemMilestoneInfo(Milestones.App1003,
		                MILESTONE_WF_ITEM_LOOKUP.get(Milestones.App1003)));
	}

}