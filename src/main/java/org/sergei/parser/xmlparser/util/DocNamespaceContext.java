/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.parser.xmlparser.util;

import javax.xml.namespace.NamespaceContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is used to define xml namespaces that are not recognized by default
 *
 * @author Sergei Visotsky
 */
public class DocNamespaceContext implements NamespaceContext {

    private final Map<String, String> PREF_MAP = new HashMap<>();

    public DocNamespaceContext(final Map<String, String> prefMap) {
        PREF_MAP.putAll(prefMap);
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return PREF_MAP.get(prefix);
    }

    @Override
    public String getPrefix(String namespaceURI) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        throw new UnsupportedOperationException();
    }
}
