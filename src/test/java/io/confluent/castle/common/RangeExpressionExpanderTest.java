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

package io.confluent.castle.common;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class RangeExpressionExpanderTest {
    @Rule
    final public Timeout globalTimeout = Timeout.millis(120000);

    @Test
    public void testNoExpansionNeeded() throws Exception {
        assertEquals(Collections.singleton("foo"), RangeExpressionExpander.expand("foo"));
        assertEquals(Collections.singleton("bar"), RangeExpressionExpander.expand("bar"));
        assertEquals(Collections.singleton(""), RangeExpressionExpander.expand(""));
    }

    @Test
    public void testExpansions() throws Exception {
        HashSet<String> expected1 = new HashSet<>(Arrays.asList(
            "foo1",
            "foo2",
            "foo3"
        ));
        assertEquals(expected1, RangeExpressionExpander.expand("foo[1-3]"));

        HashSet<String> expected2 = new HashSet<>(Arrays.asList(
            "foo bar baz 0"
        ));
        assertEquals(expected2, RangeExpressionExpander.expand("foo bar baz [0-0]"));

        HashSet<String> expected3 = new HashSet<>(Arrays.asList(
            "[[ wow50 ]]",
            "[[ wow51 ]]",
            "[[ wow52 ]]"
        ));
        assertEquals(expected3, RangeExpressionExpander.expand("[[ wow[50-52] ]]"));
    }
}
