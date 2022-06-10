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
package org.eclipse.fordiac.ide.structuredtextcore.scoping

import java.lang.reflect.Method
import java.text.MessageFormat
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.eval.function.Comment
import org.eclipse.fordiac.ide.model.eval.function.Functions
import org.eclipse.fordiac.ide.model.eval.function.OnlySupportedBy
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*

class STStandardFunctionProvider {
	public static final URI STANDARD_FUNCTIONS_URI = URI.createURI("__st_standard_functions.stfunc")

	static final List<Class<? extends Functions>> DEFAULT_FUNCTIONS = #[StandardFunctions]
	final List<Class<? extends Functions>> functions
	final Resource functionResource

	/**
	 * Create a new instance using the default set of standard functions
	 */
	new() {
		this(DEFAULT_FUNCTIONS)
	}

	/**
	 * Create a new instance using the given set of standard functions
	 */
	new(List<Class<? extends Functions>> functions) {
		this.functions = functions
		this.functionResource = new ResourceImpl
		this.functionResource.URI = STANDARD_FUNCTIONS_URI
	}

	/**
	 * Get a list of all standard functions known to this provider
	 */
	def Iterable<STStandardFunction> get() {
		return functions.flatMap[Functions.getMethods(it)].map[toStandardFunction(emptyList)].toList
	}

	/**
	 * Get a list of all standard functions matching the given argument types
	 */
	def Iterable<STStandardFunction> get(List<DataType> argumentTypes) {
		return functions.flatMap[findMethodsFromDataTypes(argumentTypes)].map[toStandardFunction(argumentTypes)].toList
	}

	/**
	 * Convert a method to a standard function with concrete return and parameter types
	 */
	def protected create STCoreFactory.eINSTANCE.createSTStandardFunction toStandardFunction(Method method,
		List<DataType> argumentTypes) {
		name = method.name
		comment = method.getAnnotation(Comment)?.value ?: ""
		returnType = method.inferReturnTypeFromDataTypes(argumentTypes)
		inputParameters.addAll(method.inferParameterVariables(argumentTypes, true))
		outputParameters.addAll(method.inferParameterVariables(argumentTypes, false))
		onlySupportedBy.addAll(method.getAnnotationsByType(OnlySupportedBy).flatMap[value.toList])
		signature = '''«name»(«(inputParameters.filter(STVarDeclaration).map[type.name] + outputParameters.filter(STVarDeclaration).map['''&«type.name»''']).join(",")»)'''
		functionResource.contents.add(it)
	}

	/**
	 * Infer concrete parameter variable declarations based on a given method and argument types
	 */
	def protected Iterable<STVarDeclaration> inferParameterVariables(Method method, List<DataType> argumentTypes,
		boolean input) {
		val ptypes = method.inferParameterTypesFromDataTypes(argumentTypes)
		(0 ..< ptypes.size).map [ index |
			if (input.xor(method.getParameterType(index) == Variable)) {
				STCoreFactory.eINSTANCE.createSTVarDeclaration => [
					name = '''«IF input»IN«ELSE»OUT«ENDIF»«index»'''
					comment = MessageFormat.format(method.getParameter(index).getAnnotation(Comment)?.value ?: "", index)
					type = ptypes.get(index)
				]
			}
		].filterNull
	}
}
