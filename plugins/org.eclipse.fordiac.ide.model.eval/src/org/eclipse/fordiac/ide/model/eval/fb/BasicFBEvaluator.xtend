/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.fb

import java.util.Map
import java.util.Queue
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.Event

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.asBoolean

class BasicFBEvaluator extends BaseFBEvaluator<BasicFBType> {
	ECState state
	final Map<ECTransition, Evaluator> transitionEvaluators

	new(BasicFBType type, Variable context, Iterable<Variable> variables, Evaluator parent) {
		this(type, context, variables, null, parent)
	}

	new(BasicFBType type, Variable context, Iterable<Variable> variables, Queue<Event> queue, Evaluator parent) {
		super(type, context, variables, queue, parent)
		state = type.ECC.start
		transitionEvaluators = type.ECC.ECTransition.filter[!it.conditionExpression.nullOrEmpty].toInvertedMap [
			EvaluatorFactory.createEvaluator(it, eClass.instanceClass as Class<? extends ECTransition>, instance,
				emptySet, this)
		]
	}

	override evaluate(Event event) {
		for (var transition = state.evaluateTransitions(event); transition !== null; transition = state.
			evaluateTransitions(null)) {
			transition.destination.evaluateState
		}
	}

	def private evaluateTransitions(ECState state, Event event) {
		state.outTransitions.findFirst [ transition |
			(transition.conditionEvent === null || transition.conditionEvent == event) &&
				(transition.conditionExpression.nullOrEmpty || transitionEvaluators.get(transition).evaluate.asBoolean)
		]
	}

	def private evaluateState(ECState state) {
		this.state = state
		state.trap.ECAction.forEach [
			algorithmEvaluators.get(algorithm)?.evaluate
			if(output !== null) queue?.add(output)
		]
	}
}
