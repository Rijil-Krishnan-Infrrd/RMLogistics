package com.nexera.core.service.mongo.impl;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.mongo.MongoMessageHierarchyVO;
import com.nexera.common.vo.mongo.MongoMessagesVO;
import com.nexera.common.vo.mongo.MongoQueryVO;
import com.nexera.core.service.mongo.MessageLogMongoService;

public class MessageLogMongoServiceImpl implements MessageLogMongoService {

	@Override
	public String saveMessage(MongoMessagesVO messagesVO)
			throws FatalException, NonFatalException {
		
		
		/*
		 * This method is expected to save the messageVO oject and return the mongo message ID. 
		 */
		return null;
	}

	@Override
	public MongoMessageHierarchyVO getMessages(MongoQueryVO mongoQueryVO)
			throws FatalException, NonFatalException {
		// TODO Auto-generated method stub
		/*
		 * This method should construct the hierarchy object based on the queryVO.
		 */
		return null;
	}

}