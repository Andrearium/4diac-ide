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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.exceptions.GuaranteeExeption;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class Guarantee extends ContractElement {

	private static final int POS_MS = 10;
	private static final int POS_WITHIN = 9;
	private static final int POS_OCCUR = 8;
	private static final int POS_EVENT_STRING = 6;
	private static final int POS_THEN = 5;
	private static final int POS_OCCURS = 4;
	private static final int POS_EVENT = 2;
	private static final int POS_WHENEVER = 1;
	private static final int GUARANTEE_LENGTH = 11;
	private static final int POS_OUTPUT_EVENT = 7;
	private static final int POS_INPUT_EVENT = 3;
	private static final int POSITION_NO = 10;

	private String outputEvent;

	Guarantee() {

	}

	public String getOutputEvent() {
		return outputEvent;
	}

	void setOutputEvent(final String outputEvent) {
		this.outputEvent = outputEvent;
	}

	public static Guarantee createGuarantee(final String line) {
		if (line.contains(ContractKeywords.REACTION)) {
			return Reaction.createReaction(line);
		}
		if (line.contains(ContractKeywords.EVENTS)) {
			return GuaranteeTwoEvents.createGuaranteeTwoEvents(line);
		}
		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectGuarantee(parts)) {
			throw new GuaranteeExeption("Error with Guarantee: " + line); //$NON-NLS-1$

		}
		final Guarantee guarantee = new Guarantee();
		guarantee.setInputEvent(parts[POS_INPUT_EVENT]);
		guarantee.setOutputEvent(parts[POS_OUTPUT_EVENT]);
		if (ContractUtils.isInterval(parts, POSITION_NO, ContractKeywords.INTERVAL_DIVIDER)) {
			guarantee.setRangeFromInterval(parts, POSITION_NO);
			return guarantee;
		}
		guarantee.setMax(Integer.parseInt(
				parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - ContractKeywords.UNIT_OF_TIME.length())));
		guarantee.setMin(0);
		return guarantee;
	}

	private static boolean isCorrectGuarantee(final String[] parts) {
		if (parts.length != GUARANTEE_LENGTH) {
			return false;
		}
		if (!ContractKeywords.WHENEVER.equals(parts[POS_WHENEVER])) {
			return false;
		}
		if (!ContractKeywords.EVENT.equals(parts[POS_EVENT])) {
			return false;
		}
		if (!(ContractKeywords.OCCURS + ContractKeywords.COMMA).equals(parts[POS_OCCURS])) {
			return false;
		}
		if (!ContractKeywords.THEN.equals(parts[POS_THEN])) {
			return false;
		}
		if (!ContractKeywords.EVENT.equals(parts[POS_EVENT_STRING])) {
			return false;
		}
		if (!ContractKeywords.OCCUR.equals(parts[POS_OCCUR])) {
			return false;
		}
		if (!ContractKeywords.WITHIN.equals(parts[POS_WITHIN])) {
			return false;
		}
		return ContractKeywords.UNIT_OF_TIME.equals(parts[POS_MS]
				.subSequence(parts[POS_MS].length() - ContractKeywords.UNIT_OF_TIME.length(), parts[POS_MS].length()));
	}

	@Override
	boolean isValid() {
		if (this instanceof final GuaranteeTwoEvents guaranteeTwoEvents) {
			return guaranteeTwoEvents.isValid();
		}
		if (!hasValidOwner()) {
			return false;
		}
		final EList<FBNetworkElement> fBNetworkElements = ((SubApp) getContract().getOwner()).getSubAppNetwork()
				.getNetworkElements();
		final List<SubApp> containedSubapps = fBNetworkElements.parallelStream().filter(ContractUtils::isContractSubapp)
				.map(el -> (SubApp) el).toList();
		final List<FB> containedfBs = fBNetworkElements.parallelStream().filter(FB.class::isInstance)
				.map(FB.class::cast).toList();
		return hasMatchingEvent(((SubApp) getContract().getOwner()), containedSubapps, containedfBs);
	}

	private boolean hasMatchingEvent(final SubApp subapp, final List<SubApp> containedSubapps,
			final List<FB> containedfBs) {
		if (!containedSubapps.isEmpty()) {
			final EList<Event> inputEvents = subapp.getInterface().getEventInputs();
			final EList<Event> outputEvents = subapp.getInterface().getEventOutputs();
			final List<Event> inputNames = inputEvents.parallelStream().filter(e -> e.getName().equals(getInputEvent()))
					.toList();
			final List<Event> outputNames = outputEvents.parallelStream().filter(e -> e.getName().equals(outputEvent))
					.toList();
			if (inputNames.size() == 1 && outputNames.size() == 1) {
				return true;
			}
		}
		if (containedfBs.size() == 1) {
			final EList<Event> inputFBEvents = containedfBs.get(0).getInterface().getEventInputs();
			final EList<Event> outputFBEvents = containedfBs.get(0).getInterface().getEventOutputs();
			final List<Event> inputNames = inputFBEvents.parallelStream()
					.filter(e -> e.getName().equals(getInputEvent())).toList();
			final List<Event> outputNames = outputFBEvents.parallelStream().filter(e -> e.getName().equals(outputEvent))
					.toList();
			if (inputNames.size() == 1 && outputNames.size() == 1) {
				return true;
			}
		}
		return false;
	}

	private static void sortHelper(final Map<String, EList<Guarantee>> mapGuarantees,
			final Map<String, EList<Reaction>> mapReactions,
			final Map<String, EList<GuaranteeTwoEvents>> mapGuaranteeTwoEvents, final Guarantee guarantee) {
		if (guarantee instanceof final GuaranteeTwoEvents guaranteeTwoEvents) {
			if (mapGuaranteeTwoEvents.containsKey(guaranteeTwoEvents.getOutputEvent())) {
				mapGuaranteeTwoEvents.get(guaranteeTwoEvents.getOutputEvent()).add(guaranteeTwoEvents);
			} else {
				final EList<GuaranteeTwoEvents> toAdd = new BasicEList<>();
				toAdd.add(guaranteeTwoEvents);
				mapGuaranteeTwoEvents.put(guaranteeTwoEvents.getOutputEvent(), toAdd);
			}
		} else if (guarantee instanceof final Reaction reaction) {
			if (mapReactions.containsKey(reaction.getOutputEvent())) {
				mapReactions.get(reaction.getOutputEvent()).add(reaction);
			} else {
				final EList<Reaction> toAdd = new BasicEList<>();
				toAdd.add(reaction);
				mapReactions.put(reaction.getOutputEvent(), toAdd);
			}
		} else if (mapGuarantees.containsKey(guarantee.getOutputEvent())) {
			mapGuarantees.get(guarantee.getOutputEvent()).add(guarantee);
		} else {
			final EList<Guarantee> toAdd = new BasicEList<>();
			toAdd.add(guarantee);
			mapGuarantees.put(guarantee.getOutputEvent(), toAdd);
		}
	}

	public static boolean isCompatibleWith(final EList<Guarantee> guarantees) {
		final Map<String, EList<Guarantee>> mapGuarantees = new HashMap<>();
		final Map<String, EList<Reaction>> mapReactions = new HashMap<>();
		final Map<String, EList<GuaranteeTwoEvents>> mapGuaranteeTwoEvents = new HashMap<>();
		final Consumer<Guarantee> sort = guarantee -> sortHelper(mapGuarantees, mapReactions, mapGuaranteeTwoEvents,
				guarantee);
		guarantees.parallelStream().forEach(sort);
		if (mapGuaranteeTwoEvents.size() > 0) {
			return GuaranteeTwoEvents.isCompatibleWith(mapGuarantees, mapReactions, mapGuaranteeTwoEvents);
		}
		if (mapReactions.size() > 0) {
			return Reaction.isCompatibleWith(mapGuarantees, mapReactions);
		}
		for (final Map.Entry<String, EList<Guarantee>> entry : mapGuarantees.entrySet()) {
			if (entry.getValue().size() != 1 && !ContractElement.isTimeConsistent(entry.getValue())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String createComment() {
		if (this instanceof final Reaction reaction) {
			return reaction.createComment();
		}
		if (this instanceof final GuaranteeTwoEvents twoEvents) {
			return twoEvents.createComment();
		}
		final StringBuilder comment = new StringBuilder();
		comment.append(ContractKeywords.GUARANTEE);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.WHENEVER);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.EVENT);
		comment.append(" "); //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.OCCURS);
		comment.append(ContractKeywords.COMMA);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.THEN);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.EVENT);
		comment.append(" "); //$NON-NLS-1$
		comment.append(outputEvent);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.OCCUR);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.WITHIN);
		comment.append(" "); //$NON-NLS-1$
		if (getMin() == 0 || getMin() == getMax()) {
			comment.append(getMax());
		} else {
			comment.append(ContractUtils.createInterval(this));
		}
		comment.append(ContractKeywords.UNIT_OF_TIME);
		comment.append(System.lineSeparator());
		return comment.toString();
	}

}
