package com.saninco.framework.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class LoggingInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 5125186370912086894L;

	private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	private static final String START_MESSAGE = " Entering method ";
	private static final String FINISH_MESSAGE = " Exiting method ";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logMessage(invocation, START_MESSAGE);
		String result = invocation.invoke();
		logMessage(invocation, FINISH_MESSAGE);
		return result;
	}

	private void logMessage(ActionInvocation invocation, String baseMessage) {
		if (LOG.isInfoEnabled()) {
			
			if (START_MESSAGE.equals(baseMessage)) {
				LOG.info("Execute action : "+ invocation.getProxy().getActionName());
			}
			
			
			StringBuilder message = new StringBuilder();
			String actionName = invocation.getAction().getClass().getName();
			String method = invocation.getProxy().getMethod();
			message.append("[");
			message.append(actionName);
			message.append("] :");
			message.append(baseMessage);
			message.append(method);

			LOG.info(message.toString());
		}
	}

}
