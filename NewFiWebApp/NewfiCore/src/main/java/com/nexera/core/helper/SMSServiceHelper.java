package com.nexera.core.helper;

public interface SMSServiceHelper {

	public String sendNotificationSMS(String carrierName, int phoneNumber,
	        String textMessage);
}