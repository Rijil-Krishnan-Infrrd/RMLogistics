package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.Milestones;
import com.nexera.core.service.LoanService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LMDecisionManager extends NexeraWorkflowTask implements
		IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
			.getLogger(LMDecisionManager.class);
	@Autowired
	private LoanService loanService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private IWorkflowService iWorkflowService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String status = iWorkflowService.getNexeraMilestoneComments(loanId,
				Milestones.LM_DECISION);
		return status == null ? "" : status;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		String status = null;
		int loanId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String decision = inputMap.get(
				WorkflowDisplayConstants.WORKFLOW_LM_DECISION).toString();
		String comment = inputMap.get(
				WorkflowDisplayConstants.WORKFLOW_LM_DECISION_COMMENT)
				.toString();
		iWorkflowService.updateNexeraMilestone(loanId,
				Milestones.LM_DECISION.getMilestoneID(), decision);
		int workflowItemExecId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
				WorkItemStatus.COMPLETED.getStatus());
		status = WorkItemStatus.COMPLETED.getStatus();
		int userId = Integer.parseInt(inputMap.get(
				WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		User user = new User();
		user.setId(userId);
		makeANote(loanId, comment, user);
		return status;
	}

	private void makeANote(int loanId, String message, User createdBy) {
		messageServiceHelper.generatePrivateMessage(loanId, message, createdBy,
				false);
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
