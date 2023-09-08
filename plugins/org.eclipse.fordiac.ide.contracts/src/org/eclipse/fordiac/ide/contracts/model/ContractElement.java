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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class ContractElement {

	private int min;
	private int max;
	private String inputEvent;
	private Contract owner;

	public String getInputEvent() {
		return inputEvent;
	}

	void setInputEvent(final String inputEvent) {
		this.inputEvent = inputEvent;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public Contract getContract() {
		return owner;
	}

	void setMin(final int min) {
		this.min = min;
	}

	void setMax(final int max) {
		this.max = max;
	}

	void setContract(final Contract owner) {
		this.owner = owner;
	}

	boolean hasValidOwner() {
		return getContract().getOwner() instanceof final SubApp;
	}

	@SuppressWarnings("static-method")
	boolean isValid() {
		return false;
	}

	@SuppressWarnings("static-method")
	public String createComment() {
		return "This is a ContractElement"; //$NON-NLS-1$
	}

	static boolean isTimeConsistent(final EList<? extends ContractElement> contractElement) {
		if (contractElement.get(0).getMax() == -1) {
			return isSingelAssumtion(contractElement, 0);
		}
		int maximum = contractElement.get(0).getMax();
		int minimum = contractElement.get(0).getMin();
		for (int i = 1; contractElement.size() > i; i++) {
			if (contractElement.get(i).getMax() == -1) {
				return isSingelAssumtion(contractElement, i);
			}
			if (minimum < contractElement.get(i).getMin()) {
				minimum = contractElement.get(i).getMin();
			}
			if (maximum > contractElement.get(i).getMax()) {
				maximum = contractElement.get(i).getMax();
			}
			if (maximum < minimum) {
				return false;
			}

		}

		simplifyContract(contractElement, minimum, maximum);
		return true;
	}

	// checks if there is only one assumtion of the type: x occures every y ms
	// if true simplifys Assumtion and returns true
	private static boolean isSingelAssumtion(final EList<? extends ContractElement> contractElement, final int pos) {
		for (int i = pos + 1; i < contractElement.size(); i++) {
			if (contractElement.get(i).getMax() == -1) {
				return false;
			}
		}
		simplifyAssumption(contractElement.get(pos).getMin(), -1, contractElement.get(pos).getContract(),
				(Assumption) contractElement.get(pos));
		return true;
	}

	private static void simplifyContract(final EList<? extends ContractElement> contractElement, final int minimum,
			final int maximum) {
		final Contract contract = contractElement.get(0).getContract();
		if (contractElement.get(0) instanceof final Assumption toRemove) {
			simplifyAssumption(minimum, maximum, contract, toRemove);
		} else if (contractElement.get(0) instanceof final Guarantee toRemove) {
			simplifyGuarantee(minimum, maximum, contract, toRemove);
		}

	}

	private static void simplifyGuarantee(final int minimum, final int maximum, final Contract contract,
			final Guarantee toRemove) {
		contract.getGuarantees().removeIf(g -> (g.getInputEvent().equals(toRemove.getInputEvent())));
		final Guarantee toAdd = new Guarantee();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setOutputEvent(toRemove.getOutputEvent());
		toAdd.setMin(minimum);
		toAdd.setMax(maximum);
		contract.add(toAdd, contract);
	}

	private static void simplifyAssumption(final int minimum, final int maximum, final Contract contract,
			final Assumption toRemove) {
		contract.getAssumptions().removeIf(a -> (a.getInputEvent().equals(toRemove.getInputEvent())));
		final Assumption toAdd = new Assumption();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setMin(minimum);
		toAdd.setMax(maximum);
		contract.add(toAdd, contract);
	}

}
