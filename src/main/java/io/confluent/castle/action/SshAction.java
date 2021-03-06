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

package io.confluent.castle.action;

import io.confluent.castle.cluster.CastleCluster;
import io.confluent.castle.cluster.CastleNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static io.confluent.castle.tool.CastleReturnCode.CLUSTER_FAILED;

/**
 * Running ssh.
 */
public final class SshAction extends Action {
    public final static String TYPE = "ssh";

    private final List<String> command;

    public SshAction(String scope, Collection<String> command) {
        super(new ActionId(TYPE, scope),
            new TargetId[] {},
            new String[] {},
            0);
        this.command = Collections.unmodifiableList(new ArrayList<>(command));
    }

    @Override
    public void call(final CastleCluster cluster, final CastleNode node) throws Throwable {
        if (!node.uplink().canLogin()) {
            node.log().printf("*** Skipping %s, because the node is not accessible.%n", TYPE);
            return;
        }
        int status = node.uplink().command().argList(command).run();
        if (status != 0) {
            cluster.shutdownManager().changeReturnCode(CLUSTER_FAILED);
        }
    }
}
