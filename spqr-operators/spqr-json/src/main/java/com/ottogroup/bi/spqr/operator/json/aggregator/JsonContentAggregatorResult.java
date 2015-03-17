/**
 * Copyright 2014 Otto (GmbH & Co KG)
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

package com.ottogroup.bi.spqr.operator.json.aggregator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.ottogroup.bi.spqr.exception.RequiredInputMissingException;

/**
 * Document as generated by the json content aggregator 
 * @author mnxfst
 * @since Oct 15, 2014
 *
 */
@JsonRootName ( value = "webtrendsClickStreamAggregator" )
public class JsonContentAggregatorResult implements Serializable {

	private static final long serialVersionUID = 3429006919269984435L;
	/** pipeline identifier - if provided */
	@JsonProperty(value="pid", required=false)
	private String pipelineId = null;
	/** document type - assigned via settings */
	@JsonProperty (value="typ", required=false)	
	private String type = null;
	/** aggregated values computed from contents of raw data */
	@JsonProperty (value="agr", required=true) 
	private Map<String, Map<String, Long>> aggregatedValues = new HashMap<>();
	/** raw input used to compute aggregated values */
	@JsonProperty (value="raw", required=true)
	private List<Map<String, Object>> rawData = new ArrayList<>();
	
	/**
	 * Default constructor
	 */
	public JsonContentAggregatorResult() {		
	}
	
	/**
	 * Initializes the result document using the provided input
	 * @param pipelineId
	 * @param type
	 */
	public JsonContentAggregatorResult(final String pipelineId, final String type) {
		this.pipelineId = pipelineId;
		this.type = type;
	}
	
	/**
	 * Adds the aggregated value for a given field value to the map holding all aggregation values for that specific field. Example: (field='cs-host', fieldValue='www.otto.de', aggregationValue=5)
	 * @param field
	 * @param fieldValue
	 * @param aggregationValue
	 * @throws RequiredInputMissingException
	 */
	public void addAggregatedValue(final String field, final String fieldValue, final long aggregationValue) throws RequiredInputMissingException {
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// validate provided input
		if(StringUtils.isBlank(field))
			throw new RequiredInputMissingException("Missing required input for parameter 'field'");
		if(StringUtils.isBlank(fieldValue))
			throw new RequiredInputMissingException("Missing required input for parameter 'fieldValue'");
		//
		///////////////////////////////////////////////////////////////////////////////////////////
		
		String fieldKey = StringUtils.lowerCase(StringUtils.trim(field));
		Map<String, Long> fieldAggregationValues = this.aggregatedValues.get(fieldKey);
		if(fieldAggregationValues == null)
			fieldAggregationValues = new HashMap<>();
		fieldAggregationValues.put(fieldValue, aggregationValue);
		this.aggregatedValues.put(fieldKey, fieldAggregationValues);
	}
	
	/**
	 * Increment aggregated value by provided <i>inc</i> value
	 * @param field
	 * @param fieldValue
	 * @param inc
	 * @throws RequiredInputMissingException
	 */
	public void incAggregatedValue(final String field, final String fieldValue, final long inc) throws RequiredInputMissingException {
		///////////////////////////////////////////////////////////////////////////////////////////
		// validate provided input
		if(StringUtils.isBlank(field))
			throw new RequiredInputMissingException("Missing required input for parameter 'field'");
		if(StringUtils.isBlank(fieldValue))
			throw new RequiredInputMissingException("Missing required input for parameter 'fieldValue'");
		//
		///////////////////////////////////////////////////////////////////////////////////////////
		
		String fieldKey = StringUtils.lowerCase(StringUtils.trim(field));
		String fieldValueKey = StringUtils.lowerCase(StringUtils.trim(fieldValue));
		Map<String, Long> fieldAggregationValues = this.aggregatedValues.get(fieldKey);
		if(fieldAggregationValues == null)
			fieldAggregationValues = new HashMap<>();
		long aggregationValue = (fieldAggregationValues.containsKey(fieldValueKey) ? fieldAggregationValues.get(fieldValueKey) : 0);
		aggregationValue = aggregationValue + inc;		
		fieldAggregationValues.put(fieldValueKey, aggregationValue);
		this.aggregatedValues.put(fieldKey, fieldAggregationValues);
	}
	
	/**
	 * Evaluates the referenced aggregated value against the provided value and saves the smaller one
	 * @param field
	 * @param fieldValue
	 * @param value
	 * @throws RequiredInputMissingException
	 */
	public void evalMinAggregatedValue(final String field, final String fieldValue, final long value) throws RequiredInputMissingException {
		///////////////////////////////////////////////////////////////////////////////////////////
		// validate provided input
		if(StringUtils.isBlank(field))
			throw new RequiredInputMissingException("Missing required input for parameter 'field'");
		if(StringUtils.isBlank(fieldValue))
			throw new RequiredInputMissingException("Missing required input for parameter 'fieldValue'");
		//
		///////////////////////////////////////////////////////////////////////////////////////////
		
		String fieldKey = StringUtils.lowerCase(StringUtils.trim(field));
		String fieldValueKey = StringUtils.lowerCase(StringUtils.trim(fieldValue));
		Map<String, Long> fieldAggregationValues = this.aggregatedValues.get(fieldKey);
		if(fieldAggregationValues == null)
			fieldAggregationValues = new HashMap<>();
		long aggregationValue = (fieldAggregationValues.containsKey(fieldValueKey) ? fieldAggregationValues.get(fieldValueKey) : Integer.MAX_VALUE);
		if(value < aggregationValue) {
			fieldAggregationValues.put(fieldValueKey, value);
			this.aggregatedValues.put(fieldKey, fieldAggregationValues);
		}
	}
	
	/**
	 * Evaluates the referenced aggregated value against the provided value and saves the larger one
	 * @param field
	 * @param fieldValue
	 * @param value
	 * @throws RequiredInputMissingException
	 */
	public void evalMaxAggregatedValue(final String field, final String fieldValue, final long value) throws RequiredInputMissingException {
		///////////////////////////////////////////////////////////////////////////////////////////
		// validate provided input
		if(StringUtils.isBlank(field))
			throw new RequiredInputMissingException("Missing required input for parameter 'field'");
		if(StringUtils.isBlank(fieldValue))
			throw new RequiredInputMissingException("Missing required input for parameter 'fieldValue'");
		//
		///////////////////////////////////////////////////////////////////////////////////////////
		
		String fieldKey = StringUtils.lowerCase(StringUtils.trim(field));
		String fieldValueKey = StringUtils.lowerCase(StringUtils.trim(fieldValue));
		Map<String, Long> fieldAggregationValues = this.aggregatedValues.get(fieldKey);
		if(fieldAggregationValues == null)
			fieldAggregationValues = new HashMap<>();
		long aggregationValue = (fieldAggregationValues.containsKey(fieldValueKey) ? fieldAggregationValues.get(fieldValueKey) : Integer.MIN_VALUE);
		if(value > aggregationValue) {
			fieldAggregationValues.put(fieldValue, value);
			this.aggregatedValues.put(fieldKey, fieldAggregationValues);
		}
	}
	
	/**
	 * Returns the aggregated value for a given field and value: (field='cs-host', value='www.otto.de', result: 5);
	 * @param field
	 * @param fieldValue
	 * @return
	 */
	public long getAggregatedValue(final String field, final String fieldValue) {
		String fieldKey = StringUtils.lowerCase(StringUtils.trim(field));
		Map<String, Long> fieldAggregationValues = this.aggregatedValues.get(fieldKey);
		return (fieldAggregationValues != null ? fieldAggregationValues.get(fieldValue) : 0);
	}
	
	/**
	 * Adds the provided map of raw data
	 * @param rawData
	 */
	public void addRawData(Map<String, Object> rawData) {
		if(rawData != null && !rawData.isEmpty())
			this.rawData.add(rawData);
	}

	public String getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Map<String, Long>> getAggregatedValues() {
		return aggregatedValues;
	}

	public void setAggregatedValues(
			Map<String, Map<String, Long>> aggregatedValues) {
		this.aggregatedValues = aggregatedValues;
	}

	public List<Map<String, Object>> getRawData() {
		return rawData;
	}

	public void setRawData(List<Map<String, Object>> rawData) {
		this.rawData = rawData;
	}
	
}
