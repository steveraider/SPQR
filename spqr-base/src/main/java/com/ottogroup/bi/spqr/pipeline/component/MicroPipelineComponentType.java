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
package com.ottogroup.bi.spqr.pipeline.component;

import java.io.Serializable;

/**
 * Names all known types of {@link MicroPipelineComponent} 
 * @author mnxfst
 * @since Mar 6, 2015
 */
public enum MicroPipelineComponentType implements Serializable {
	SOURCE, DIRECT_RESPONSE_OPERATOR, DELAYED_RESPONSE_OPERATOR, EMITTER	
}
