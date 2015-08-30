/**
 * 
 */
package com.glebow.error;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Philip Glebow
 *
 */
@Slf4j
public class CustomErrorHandler implements ErrorHandler {

    /**
     * Default
     */
    public CustomErrorHandler() {        
    }

    /* (non-Javadoc)
     * @see org.springframework.util.ErrorHandler#handleError(java.lang.Throwable)
     */
    @Override
    public void handleError(Throwable t) {
        if ( t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException e = (ListenerExecutionFailedException)t;
            Message failedMessage = e.getFailedMessage();
            Throwable cause = e.getCause();                        
            log.error(cause.getMessage());
            
        }
    }

}
