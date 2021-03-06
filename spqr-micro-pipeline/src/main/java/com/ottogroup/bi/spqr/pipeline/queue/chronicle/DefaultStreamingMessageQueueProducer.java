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
package com.ottogroup.bi.spqr.pipeline.queue.chronicle;

import net.openhft.chronicle.ExcerptAppender;

import com.ottogroup.bi.spqr.pipeline.message.StreamingDataMessage;
import com.ottogroup.bi.spqr.pipeline.queue.StreamingMessageQueueProducer;
import com.ottogroup.bi.spqr.pipeline.queue.strategy.StreamingMessageQueueWaitStrategy;

/**
 * Default {@link StreamingMessageQueueProducer} implementation accessing {@link DefaultStreamingMessageQueue}
 * @author mnxfst
 * @since Mar 5, 2015
 * TODO async implementation required
 */
public class DefaultStreamingMessageQueueProducer implements
		StreamingMessageQueueProducer {

	private final ExcerptAppender queueProducer;
	private final StreamingMessageQueueWaitStrategy waitStrategy;
	
	/**
	 * Initializes the producer using the provided input
	 * @param queueProducer
	 * @param waitStrategy
	 */
	public DefaultStreamingMessageQueueProducer(final ExcerptAppender queueProducer, final StreamingMessageQueueWaitStrategy waitStrategy) {
		this.queueProducer = queueProducer;
		this.waitStrategy = waitStrategy;
	}
	
	/**
	 * @see com.ottogroup.bi.spqr.pipeline.queue.StreamingMessageQueueProducer#insert(com.ottogroup.bi.spqr.pipeline.message.StreamingDataMessage)
	 */
	public boolean insert(StreamingDataMessage message) {
		
		if(message != null) {
			queueProducer.startExcerpt();
			queueProducer.writeLong(message.getTimestamp());
			queueProducer.writeInt(message.getBody().length);
			queueProducer.write(message.getBody());
			queueProducer.finish();
			return true;
		}
		
		return false;
	}

	/**
	 * @see com.ottogroup.bi.spqr.pipeline.queue.StreamingMessageQueueProducer#getWaitStrategy()
	 */
	public StreamingMessageQueueWaitStrategy getWaitStrategy() {
		return this.waitStrategy;
	}

}
