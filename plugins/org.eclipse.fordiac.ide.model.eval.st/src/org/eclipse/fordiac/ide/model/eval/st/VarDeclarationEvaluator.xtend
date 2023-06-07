/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.Subrange
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorException
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.variable.VariableEvaluator
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

class VarDeclarationEvaluator extends StructuredTextEvaluator implements VariableEvaluator {
	final VarDeclaration varDeclaration

	INamedElement resultType
	STTypeDeclaration parseResultType
	STInitializerExpressionSource parseResult

	new(VarDeclaration varDeclaration, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(varDeclaration.name, context, variables, parent)
		this.varDeclaration = varDeclaration
	}

	override prepare() {
		prepareResultType
		prepareInitialValue
	}

	def protected prepareResultType() {
		if (parseResultType === null && varDeclaration.array) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			// use variable from FB type since the type declaration is in the context of the FB type and not an instance
			val typeVariable = varDeclaration.FBNetworkElement?.type?.interfaceList?.getVariable(varDeclaration.name) ?:
				varDeclaration
			parseResultType = typeVariable.parseType(
				errors,
				warnings,
				infos
			)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResultType === null) {
				throw new IllegalArgumentException(errors.join(", "))
			}
		}
	}

	def protected prepareInitialValue() {
		evaluateResultType
		if (parseResult === null && !varDeclaration.initialValue.nullOrEmpty) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = varDeclaration.initialValue.parse(
				varDeclaration?.eResource?.URI,
				resultType,
				null,
				null,
				errors,
				warnings,
				infos
			)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new IllegalArgumentException(errors.join(", "))
			}
		}
	}

	override evaluate() {
		prepareInitialValue
		val result = newVariable(varDeclaration.name, resultType)
		if (parseResult?.initializerExpression !== null) {
			result.evaluateInitializerExpression(parseResult.initializerExpression)
		}
		result.value
	}

	override evaluateVariable() throws EvaluatorException, InterruptedException {
		prepareInitialValue
		val result = newVariable(varDeclaration.name, resultType)
		if (parseResult?.initializerExpression !== null) {
			result.evaluateInitializerExpression(parseResult.initializerExpression)
		}
		result
	}

	override evaluateResultType() throws EvaluatorException, InterruptedException {
		if (resultType === null) {
			prepareResultType
			resultType = if (parseResultType !== null)
				parseResultType?.evaluateTypeDeclaration
			else
				varDeclaration.type
		}
		resultType
	}

	def protected INamedElement evaluateTypeDeclaration(STTypeDeclaration declaration) {
		val type = switch (type: declaration.type) {
			AnyStringType case declaration.maxLength !== null:
				type.newStringType(declaration.maxLength.evaluateExpression.asInteger)
			DataType:
				type
		}
		if (declaration.array)
			type.newArrayType(
				if (declaration.ranges.empty)
					declaration.count.map[DataFactory.eINSTANCE.createSubrange]
				else
					declaration.ranges.map[evaluateSubrange]
			)
		else
			type
	}

	def protected Subrange evaluateSubrange(STExpression expr) {
		switch (expr) {
			STBinaryExpression case expr.op === STBinaryOperator.RANGE:
				newSubrange(expr.left.evaluateExpression.asInteger, expr.right.evaluateExpression.asInteger)
			default:
				newSubrange(0, expr.evaluateExpression.asInteger)
		}
	}

	override getSourceElement() { varDeclaration }
}
