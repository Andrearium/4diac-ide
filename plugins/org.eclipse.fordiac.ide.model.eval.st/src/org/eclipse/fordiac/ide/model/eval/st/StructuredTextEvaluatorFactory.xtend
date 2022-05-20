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
package org.eclipse.fordiac.ide.model.eval.st

import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction

class StructuredTextEvaluatorFactory implements EvaluatorFactory {
	override createEvaluator(Object source, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		if (source instanceof STAlgorithm) {
			new STAlgorithmEvaluator(source, context, variables, parent)
		} else if (source instanceof ECTransition) {
			new ECTransitionEvaluator(source, context, variables, parent)
		} else if (source instanceof STMethod) {
			new STMethodEvaluator(source, context, variables, parent)
		} else if (source instanceof STFunction) {
			new STFunctionEvaluator(source, context, variables, parent)
		}
	}

	def static void register() {
		val factory = new StructuredTextEvaluatorFactory
		EvaluatorFactory.Registry.INSTANCE.classToFactoryMap.putIfAbsent(STAlgorithm, factory)
		EvaluatorFactory.Registry.INSTANCE.classToFactoryMap.putIfAbsent(ECTransition, factory)
		EvaluatorFactory.Registry.INSTANCE.classToFactoryMap.putIfAbsent(STMethod, factory)
		EvaluatorFactory.Registry.INSTANCE.classToFactoryMap.putIfAbsent(STFunction, factory)
	}
}
