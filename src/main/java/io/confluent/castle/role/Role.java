/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.confluent.castle.role;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.confluent.castle.action.Action;
import io.confluent.castle.common.DynamicVariableProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * A role which a particular castle cluster node can have.
 *
 * A cluster node may have multiple roles.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
public interface Role {
    /**
     * Create the actions for this node.
     *
     * @param nodeName      The name of this node.
     */
    Collection<Action> createActions(String nodeName);

    /**
     * Get the dynamic variable providers implemented by this action.
     */
    default Map<String, DynamicVariableProvider> dynamicVariableProviders() {
        return Collections.emptyMap();
    }
};
