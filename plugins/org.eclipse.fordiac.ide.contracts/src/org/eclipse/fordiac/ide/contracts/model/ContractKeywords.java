/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model;

public final class ContractKeywords {
	private ContractKeywords() {

	}

	static final String OFFSET = "offset"; //$NON-NLS-1$
	public static final String INTERVAL_DIVIDER = ","; //$NON-NLS-1$
	static final String INTERVAL_CLOSE = "]"; //$NON-NLS-1$
	static final String OCCURS = "occurs"; //$NON-NLS-1$
	static final String EVERY = "every"; //$NON-NLS-1$
	static final String INTERVAL_OPEN = "["; //$NON-NLS-1$
	public static final String ASSUMPTION = "ASSUMPTION"; //$NON-NLS-1$
	public static final String UNIT_OF_TIME = "ms"; //$NON-NLS-1$
	static final String WHENEVER = "Whenever"; //$NON-NLS-1$
	static final String EVENT = "event"; //$NON-NLS-1$
	static final String OCCUR = "occur"; //$NON-NLS-1$
	static final String WITHIN = "within"; //$NON-NLS-1$
	static final String EVENTS = "events"; //$NON-NLS-1$
	static final String THEN = "then"; //$NON-NLS-1$
	public static final String GUARANTEE = "GUARANTEE"; //$NON-NLS-1$
	static final String COMMA = ","; //$NON-NLS-1$
	static final String EVENTS_OPEN = "("; //$NON-NLS-1$
	static final String EVENTS_CLOSE = ")"; //$NON-NLS-1$
	static final String REACTION = "Reaction"; //$NON-NLS-1$
	public static final String CONTRACT = "_CONTRACT";
	public static final String CONSTRACT_STATE = "ConstractState";
	public static final String FALSE = " FALSE";
	public static final String TRUE = " TRUE";
	static final String WITH = "with";

}
