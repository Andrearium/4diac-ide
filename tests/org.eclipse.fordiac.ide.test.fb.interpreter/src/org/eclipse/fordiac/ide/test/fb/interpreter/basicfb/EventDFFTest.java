/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.basicfb;

import static org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory.addTransaction;
import static org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils.setVariable;

import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

public class EventDFFTest extends AbstractInterpreterTest {

	@Override
	public void test() {
		final BasicFBType fb = (BasicFBType) loadFBType("E_D_FF"); //$NON-NLS-1$
		ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "D", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK")); //$NON-NLS-1$

		runFBTest(fb, seq);

		seq = newServiceSequence(fb);
		setVariable(fb, "D", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK", "EO", "Q:=TRUE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		runFBTest(fb, seq);

		seq = newServiceSequence(fb);
		setVariable(fb, "D", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK", "EO", "Q:=FALSE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		runFBTest(fb, seq, "SET"); //$NON-NLS-1$

		seq = newServiceSequence(fb);
		setVariable(fb, "D", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK", "EO", "Q:=TRUE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		runFBTest(fb, seq, "RESET"); //$NON-NLS-1$

		seq = newServiceSequence(fb);
		setVariable(fb, "D", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK")); //$NON-NLS-1$

		runFBTest(fb, seq, "SET"); //$NON-NLS-1$

		seq = newServiceSequence(fb);
		setVariable(fb, "D", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK", "EO", "Q:=FALSE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		runFBTest(fb, seq, "SET"); //$NON-NLS-1$

		seq = newServiceSequence(fb);
		setVariable(fb, "D", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransactionBuilder("CLK")); //$NON-NLS-1$

		runFBTest(fb, seq, "RESET"); //$NON-NLS-1$
	}

	private static ServiceSequence newServiceSequence(final BasicFBType fb) {
		fb.getService().getServiceSequence().clear();
		return ServiceSequenceUtils.addServiceSequence(fb.getService());
	}

}
