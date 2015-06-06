/**
 * 
 */
package com.newfi.nexera.vo;

/**
 * @author Utsav
 *
 */
public class ResponseVO {
	private String status;

	private String responseCode;

	private Object responseMessage;

	private String responseTime;

	private String errorCode;

	private String errorDescription;

	@Override
	public String toString() {
		return "Response status received " + status + "Response code recevied "
		        + responseCode + "Response Message received from lqb "
		        + responseMessage + "Response time generated " + responseTime
		        + "Error recevied if any from lqb " + errorDescription;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode
	 *            the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseMessage
	 */
	public Object getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage
	 *            the responseMessage to set
	 */
	public void setResponseMessage(Object responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return the responseTime
	 */
	public String getResponseTime() {
		return responseTime;
	}

	/**
	 * @param responseTime
	 *            the responseTime to set
	 */
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription
	 *            the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
