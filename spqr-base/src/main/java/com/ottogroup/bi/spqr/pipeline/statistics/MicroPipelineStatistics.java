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
package com.ottogroup.bi.spqr.pipeline.statistics;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds statistical information about a single pipeline. Values tracked are:
 * <ul>
 *   <li>pipeline identifier</li>
 *   <li>number of messages processed for specified time frame</li>
 *   <li>min. duration required for processing a single message (provided in milliseconds)</li>
 *   <li>max. duration required for processing a single message (provided in milliseconds)</li>
 *   <li>avg. duration required for processing a single message (provided in milliseconds)</li>
 *   <li>min. message size found in specified time frame</li>
 *   <li>max. message size found in specified time frame</li>
 *   <li>avg. message size found in specified time frame</li>
 * </ul>
 * @author mnxfst
 * @since Apr 14, 2015
 */
public class MicroPipelineStatistics implements Serializable {

	private static final long serialVersionUID = -2458412374912750561L;

	/** identifier of host running the pipeline the stats belong to */
	@JsonProperty(value="hid", required=true)
	private String processingNodeId = null;
	/** identifier of pipeline which generated the stats */ 
	@JsonProperty(value="pid", required=true)
	private String pipelineId = null;
	/** number of messages processed since last event */
	@JsonProperty(value="numMsg", required=true)
	private int numOfMessages = 0;	
	/** min. duration required for processing a single message */
	@JsonProperty(value="minDur")
	private int minDuration = 0;
	/** max. duration required for processing a single message */
	@JsonProperty(value="maxDur")
	private int maxDuration = 0;
	/** avg. duration required for processing a single message */
	@JsonProperty(value="avgDur")
	private int avgDuration = 0;
	/** min. message size */
	@JsonProperty(value="minSize")
	private int minSize = 0;
	/** max. message size */
	@JsonProperty(value="maxSize")
	private int maxSize = 0;
	/** avg. message size */
	@JsonProperty(value="avgSize")
	private int avgSize = 0;
		
	public MicroPipelineStatistics() {		
	}
	
	public MicroPipelineStatistics(final String processingNodeId, final String pipelineId, final int numOfMessages,
			final int minDuration, final int maxDuration, final int avgDuration,
			final int minSize, final int maxSize, final int avgSize) {
		this.processingNodeId = processingNodeId;
		this.pipelineId = pipelineId;
		this.numOfMessages = numOfMessages;
		this.minDuration = minDuration;
		this.maxDuration = maxDuration;
		this.avgDuration = avgDuration;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.avgSize = avgSize;
	}

	public void incNumOfMessages() {
		this.numOfMessages++;
	}

	public void incNumOfMessages(int v) {
		this.numOfMessages = this.numOfMessages + v;
	}
	
	public String getProcessingNodeId() {
		return processingNodeId;
	}

	public void setProcessingNodeId(String processingNodeId) {
		this.processingNodeId = processingNodeId;
	}

	public String getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}

	public int getNumOfMessages() {
		return numOfMessages;
	}

	public void setNumOfMessages(int numOfMessages) {
		this.numOfMessages = numOfMessages;
	}

	public int getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(int minDuration) {
		this.minDuration = minDuration;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}

	public int getAvgDuration() {
		return avgDuration;
	}

	public void setAvgDuration(int avgDuration) {
		this.avgDuration = avgDuration;
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getAvgSize() {
		return avgSize;
	}

	public void setAvgSize(int avgSize) {
		this.avgSize = avgSize;
	}
	
	
	
}
