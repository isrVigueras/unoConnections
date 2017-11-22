/**
 * 
 */
package com.tikal.cacao.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tikal
 *
 */
public class Switcher<K,V> {
	
	private Map<K,V> caseStatements;
	
	private V defaultCase;
	
	private V getCaseStatementByCaseId(K caseId) {
        if (caseStatements.containsKey(caseId)) {
            return caseStatements.get(caseId);
        } else {
            return defaultCase;
        }
    }

    public Switcher() {
        caseStatements = new HashMap<K, V>();
        setDefaultCaseCommand(null);
    }

    public void addCaseCommand(K caseId, V caseStatement) {
        caseStatements.put(caseId, caseStatement);
    }

    public void setDefaultCaseCommand(V defaultCase) {
        if (defaultCase != null) {
            this.defaultCase = defaultCase;
        }
    }

    public V getCase(K caseId) {
        return getCaseStatementByCaseId(caseId);

    }
}
