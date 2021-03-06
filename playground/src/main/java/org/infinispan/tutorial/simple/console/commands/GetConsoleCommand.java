/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infinispan.tutorial.simple.console.commands;

import org.infinispan.Cache;
import org.infinispan.tutorial.simple.configuration.PlaygroundConfiguration;
import org.infinispan.tutorial.simple.console.UI;
import org.infinispan.tutorial.simple.console.support.IllegalParametersException;
import org.infinispan.tutorial.simple.domain.Value;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GetConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "get";
    private final Cache<Long, Value> cache;

    public GetConsoleCommand(PlaygroundConfiguration conf) {
        this.cache = conf.getCache();
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(UI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Long id = Long.parseLong(args.next());
            Value v = cache.get(id);
            if (v != null) {
                console.println(v);
            } else {
                console.println("Not found");
            }
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: get <key>\nValue for key has to be a number. Example:\n get 10");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: get <key>");
        }
        return true;
    }

    @Override
    public void usage(UI console) {
        console.println(COMMAND_NAME + " <key>");
        console.println("\t\tGet an object from the grid.");
    }
}
