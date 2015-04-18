package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.enums.Milestones;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class AppraisalManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {

	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private WorkflowService workflowService;

	@Override
	public String execute(HashMap<String, Object> objectMap) {

		String status = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME).toString();
		boolean flag = false;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());

		String returnStatus = "";
		String mileStoneStatus = null;
		if (status.equals(LoanStatus.appraisalAvailable)) {
			flag = true;
			returnStatus = WorkItemStatus.COMPLETED.getStatus();
			mileStoneStatus = LoanStatus.appraisalAvailable;
			makeANote(Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
			        LoanStatus.appraisalReceivedMessage);
			objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,LoanStatus.appraisalReceivedMessage);
			sendEmail(objectMap);
		}
		if (mileStoneStatus != null) {
			iWorkflowService.updateNexeraMilestone(loanId,
			        Milestones.APPRAISAL.getMilestoneID(), mileStoneStatus);
		}

		return returnStatus;

	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		return iWorkflowService.getRenderInfoForAppraisal(loanId);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
