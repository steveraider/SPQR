/**
 * Copyright 2015 Otto (GmbH & Co KG)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ottogroup.bi.spqr.pipeline.component.operator;

import org.apache.log4j.Logger;

import com.ottogroup.bi.spqr.exception.RequiredInputMissingException;
import com.ottogroup.bi.spqr.pipeline.message.StreamingDataMessage;
import com.ottogroup.bi.spqr.pipeline.queue.StreamingMessageQueueConsumer;
import com.ottogroup.bi.spqr.pipeline.queue.StreamingMessageQueueProducer;

/**
 * Provides a runtime environment for {@link DirectResponseOperator} instances. The environment polls
 * messages from the assigned {@link StreamingMessageQueueConsumer}, forwards them for further processing
 * to the {@link DirectResponseOperator} and inserts all generated {@link StreamingDataMessage response messages}
 * into the {@link StreamingMessageQueueProducer}. The message order as received from the operator is 
 * preserved when handing over the messages to the queue producer.
 * @author mnxfst
 * @since Mar 5, 2015
 */
public class DirectResponseOperatorRuntimeEnvironment implements Runnable {

	/** our faithful logging facility ... ;-) */ 
	private static final Logger logger = Logger.getLogger(DirectResponseOperatorRuntimeEnvironment.class);

	/** operator instance executed by this runtime environment */
	private final DirectResponseOperator directResponseOperator;
	/** provides read access to assigned source queue */
	private final StreamingMessageQueueConsumer queueConsumer;
	/** provides write access to assigned destination queue */
	private final StreamingMessageQueueProducer queueProducer;	
	/** indicates whether the operator runtime is still running or not */
	private boolean running = false;

	/**
	 * Initializes the operator runtime environment using the provided input
	 * @param directResponseOperator
	 * @param queueConsumer
	 * @param queueProducer
	 */
	public DirectResponseOperatorRuntimeEnvironment(final DirectResponseOperator directResponseOperator, final StreamingMessageQueueConsumer queueConsumer, 
			final StreamingMessageQueueProducer queueProducer) throws RequiredInputMissingException {
		
		/////////////////////////////////////////////////////////////
		// input validation
		if(directResponseOperator == null)
			throw new RequiredInputMissingException("Missing required direct response operator");
		if(queueConsumer == null)
			throw new RequiredInputMissingException("Missing required queue consumer");
		if(queueProducer == null)
			throw new RequiredInputMissingException("Missing required queue producer");
		//
		/////////////////////////////////////////////////////////////
		
		this.directResponseOperator = directResponseOperator;
		this.queueConsumer = queueConsumer;
		this.queueProducer = queueProducer;
		this.running = true;
		
		if(logger.isDebugEnabled())
			logger.debug("direct response operator runtime environment initialized [id="+directResponseOperator.getId()+"]");
	}
	
	
	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		while(running) {			
			StreamingDataMessage message = this.queueConsumer.next();
			if(message != null) {
				StreamingDataMessage[] responseMessages = null;
				try {
					responseMessages = this.directResponseOperator.onMessage(message);
				} catch(Exception e) {
					logger.error("Failed to process message with direct response operator. Error: " + e.getMessage());
				}
				
				if(responseMessages != null && responseMessages.length > 0) {
					for(StreamingDataMessage rm : responseMessages)
						this.queueProducer.insert(rm);
				}
			} else {
				// TODO implement wait strategy
			}
		}		
	}
	
	/**
	 * Shuts down the runtime environment as well as the attached {@link Operator}
	 */
	public void shutdown() {
		this.running = false;
		try {
			this.directResponseOperator.shutdown();
		} catch(Exception e) {
			logger.error("Failed to shut down direct response operator. Error: " + e.getMessage());
		}

		if(logger.isDebugEnabled())
			logger.debug("direct response operator runtime environment shutdown [id="+this.directResponseOperator.getId()+"]");

	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}
	
}
