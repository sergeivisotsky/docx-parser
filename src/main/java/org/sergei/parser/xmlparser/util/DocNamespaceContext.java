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
