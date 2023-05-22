/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
 *               2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - updated exporter to correctly handle CHAR/WCHAR
 *     - update to preserve values of non specified FB call parameters
 * 	   - adds export for global constants
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.st

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.CharType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.data.WcharType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.util.GlobalConstantsUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.util.STFunctionUtil.*
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.findActualNodeFor
import static extension org.eclipse.xtext.util.Strings.convertToJavaString

abstract class StructuredTextSupport implements ILanguageSupport {
	@Accessors final List<String> errors = newArrayList
	@Accessors final List<String> warnings = newArrayList
	@Accessors final List<String> infos = newArrayList
	final Map<VarDeclaration, ILanguageSupport> variableLanguageSupport = newHashMap
	int uniqueVariableIndex = 0;

	def protected CharSequence generateVariables(Iterable<? extends STVarDeclarationBlock> blocks, boolean decl) '''
		«FOR block : blocks»
			«block.generateVariableBlock(decl)»
		«ENDFOR»
	'''

	def protected CharSequence generateVariableBlock(STVarDeclarationBlock block, boolean decl) '''
		«FOR variable : block.varDeclarations.filter(STVarDeclaration)»
			«variable.generateVariable(decl, block.constant)»
		«ENDFOR»
	'''

	def protected CharSequence generateVariable(STVarDeclaration variable, boolean decl, boolean const) {
		if (variable.locatedAt !== null)
			'''«IF decl»«IF const»const «ENDIF»«variable.generateTypeName» «IF !variable.array»&«ENDIF»«ENDIF»«variable.generateFeatureName» = «variable.locatedAt.generateFeatureName»;'''
		else
			'''«IF decl»«IF const»const «ENDIF»«variable.generateTypeName» «ENDIF»«variable.generateFeatureName» = «variable.generateVariableDefaultValue»;'''
	}

	def protected dispatch CharSequence generateInitializerExpression(STElementaryInitializerExpression expr) {
		expr.value.generateExpression
	}

	def protected dispatch CharSequence generateInitializerExpression(STArrayInitializerExpression expr) //
	'''«(expr.expectedType as DataType).generateTypeName»{«FOR elem : expr.values SEPARATOR ", "»«elem.generateArrayInitElement»«ENDFOR»}'''

	def protected CharSequence generateArrayInitElement(STArrayInitElement elem) //
	'''«IF elem.initExpressions.empty»«elem.indexOrInitExpression.generateInitializerExpression»«ELSE»«elem.generateMultiArrayInitElement»«ENDIF»'''

	def protected CharSequence generateMultiArrayInitElement(STArrayInitElement elem) //
	'''«FOR i : 0..<(elem.indexOrInitExpression as STElementaryInitializerExpression).value.integerFromConstantExpression SEPARATOR ", "»«FOR initExpression : elem.initExpressions SEPARATOR ", "»«initExpression.generateInitializerExpression»«ENDFOR»«ENDFOR»'''

	def protected dispatch CharSequence generateInitializerExpression(STStructInitializerExpression expr) //
	'''«(expr.expectedType as DataType).generateTypeName»(«FOR elem : expr.generateStructInitElements SEPARATOR ", "»«elem»«ENDFOR»)'''

	def protected Iterable<CharSequence> generateStructInitElements(STStructInitializerExpression expr) {
		expr.mappedStructInitElements.entrySet.map[key.generateStructInitElement(value)]
	}

	def protected CharSequence generateStructInitElement(INamedElement variable, STInitializerExpression value) {
		if(value === null) variable.generateVariableDefaultValue else value.generateInitializerExpression
	}

	def protected CharSequence generateStatementList(List<STStatement> statements) '''
		«FOR statement : statements»
			«statement.generateLineDirective»«statement.generateStatement»
		«ENDFOR»
	'''

	def protected dispatch CharSequence generateStatement(STStatement stmt) {
		errors.add('''The statement «stmt.eClass.name» is not supported''')
		'''#error "The statement «stmt.eClass.name» is not supported"'''
	}

	def protected dispatch CharSequence generateStatement(STNop stmt) {
		"" // nop
	}

	def protected dispatch CharSequence generateStatement(STAssignmentStatement stmt) //
	'''«stmt.left.generateExpression» = «stmt.right.generateExpression»;'''

	def protected dispatch CharSequence generateStatement(STIfStatement stmt) '''
		if («stmt.condition.generateExpression») {
		  «stmt.statements.generateStatementList»
		}
		«FOR elseif : stmt.elseifs»
			else if («elseif.condition.generateExpression») {
			  «elseif.statements.generateStatementList»
			}
		«ENDFOR»
		«IF stmt.^else !== null»
			else {
			  «stmt.^else.statements.generateStatementList»
			}
		«ENDIF»
	'''

	def protected dispatch CharSequence generateStatement(STCaseStatement stmt) '''
		«val variable = generateUniqueVariableName»
		if (auto «variable» = «stmt.selector.generateExpression»; false) {
		«FOR clause : stmt.cases»
			} else if («FOR value : clause.conditions SEPARATOR ' || '»«value.generateCaseCondition(variable)»«ENDFOR») {
			  «clause.statements.generateStatementList»
		«ENDFOR»
		«IF stmt.^else !== null»
			} else {
			  «stmt.^else.statements.generateStatementList»
		«ENDIF»
		}
	'''

	def protected CharSequence generateCaseCondition(STExpression expr, CharSequence variable) {
		switch (expr) {
			STBinaryExpression case expr.op.range: //
			'''func_AND(func_GE(«variable», «expr.left.generateExpression»), func_LE(«variable», «expr.right.generateExpression»))'''
			default: '''func_EQ(«variable», «expr.generateExpression»)'''
		}
	}

	def protected dispatch generateStatement(STForStatement stmt) '''
		for (auto «generateUniqueVariableName» : ST_FOR_ITER<«stmt.variable.generateTypeName»«IF stmt.by !== null», «(stmt.by.resultType as DataType).generateTypeName»«ENDIF»>(«stmt.variable.generateFeatureName», «stmt.from.generateExpression», «stmt.to.generateExpression»«IF stmt.by !== null», «stmt.by.generateExpression»«ENDIF»)) {
		  «stmt.statements.generateStatementList»
		}
	'''

	def protected dispatch generateStatement(STWhileStatement stmt) '''
		while («stmt.condition.generateExpression») {
		  «stmt.statements.generateStatementList»
		}
	'''

	def protected dispatch generateStatement(STRepeatStatement stmt) '''
		do {
		  «stmt.statements.generateStatementList»
		} while (!((«stmt.condition.generateExpression»)));
	'''

	def protected dispatch CharSequence generateStatement(STContinue stmt) '''continue;'''

	def protected dispatch CharSequence generateStatement(STReturn stmt) '''return;'''

	def protected dispatch CharSequence generateStatement(STExit stmt) '''break;'''

	def protected dispatch CharSequence generateStatement(STCallStatement stmt) '''«stmt.call.generateExpression»;'''

	def protected dispatch CharSequence generateExpression(STExpression expr) {
		errors.add('''The expression «expr.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateExpression(STBinaryExpression expr) //
	'''«switch (expr.op) {
			case AMPERSAND: "func_AND"
			case POWER: "func_EXPT"
			default: '''func_«expr.op.getName»'''
		}»«IF expr.op.arithmetic || expr.op.logical»<«(expr.resultType as DataType).generateTypeName»>«ENDIF»(«expr.left.generateExpression», «expr.right.generateExpression»)'''

	def protected dispatch CharSequence generateExpression(STUnaryExpression expr) //
	'''func_«expr.op.getName»<«(expr.resultType as DataType).generateTypeName»>(«expr.expression.generateExpression»)'''

	def protected dispatch CharSequence generateExpression(STMemberAccessExpression expr) //
	'''«expr.receiver.generateExpression».«expr.member.generateExpression»'''

	def protected dispatch CharSequence generateExpression(STArrayAccessExpression expr) //
	'''«expr.receiver.generateExpression»«FOR index : expr.index»[«index.generateExpression»]«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(STFeatureExpression expr) //
	'''«expr.feature.generateFeatureName»«IF expr.call»(«FOR arg : expr.generateCallArguments SEPARATOR ", "»«arg»«ENDFOR»)«ENDIF»'''

	def protected Iterable<CharSequence> generateCallArguments(STFeatureExpression expr) {
		try {
			expr.mappedInputArguments.entrySet.map[key.generateInputCallArgument(value, expr)] +
				expr.mappedInOutArguments.entrySet.map[key.generateInOutCallArgument(value)] +
				expr.mappedOutputArguments.entrySet.map[key.generateOutputCallArgument(value)]
		} catch (IndexOutOfBoundsException e) {
			errors.add('''Not enough arguments for «expr.feature.name»''')
			emptyList
		} catch (ClassCastException e) {
			errors.add('''Mixing named and unnamed arguments is not allowed''')
			emptyList
		}
	}

	def protected dispatch CharSequence generateExpression(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS: '''(*this)«IF expr.call»(«FOR arg : expr.generateCallArguments SEPARATOR ", "»«arg»«ENDFOR»)«ENDIF»'''
		}
	}

	def protected Iterable<CharSequence> generateCallArguments(STBuiltinFeatureExpression expr) {
		try {
			expr.mappedInputArguments.entrySet.map[key.generateInputCallArgument(value)] +
				expr.mappedInOutArguments.entrySet.map[key.generateInOutCallArgument(value)] +
				expr.mappedOutputArguments.entrySet.map[key.generateOutputCallArgument(value)]
		} catch (IndexOutOfBoundsException e) {
			errors.add('''Not enough arguments for «expr.feature.getName»''')
			emptyList
		} catch (ClassCastException e) {
			errors.add('''Mixing named and unnamed arguments is not allowed''')
			emptyList
		}
	}

	def protected CharSequence generateInputCallArgument(INamedElement parameter, STCallArgument argument,
		STFeatureExpression expr) {
		switch (expr.feature) {
			FB case argument === null: '''«expr.feature.generateFeatureName».«parameter.generateFeatureName»'''
			Event case argument ===
				null: '''«(expr.eContainer as STMemberAccessExpression).receiver.generateExpression».«parameter.generateFeatureName»'''
			default:
				parameter.generateInputCallArgument(argument)
		}
	}

	def protected CharSequence generateInputCallArgument(INamedElement parameter, STCallArgument argument) {
		if(argument === null) parameter.generateVariableDefaultValue else argument.argument.generateExpression
	}

	def protected CharSequence generateInOutCallArgument(INamedElement parameter, STCallArgument argument) {
		switch (argument) {
			case null: '''ST_IGNORE_OUT_PARAM(«parameter.generateVariableDefaultValue»)'''
			case (argument.argument instanceof STMemberAccessExpression) &&
				(argument.argument as STMemberAccessExpression).
					member instanceof STMultibitPartialExpression: '''ST_EXTEND_LIFETIME(«argument.argument.generateExpression»)'''
			default:
				argument.argument.generateExpression
		}
	}

	def protected CharSequence generateOutputCallArgument(INamedElement parameter, STCallArgument argument) {
		switch (argument) {
			case null: '''ST_IGNORE_OUT_PARAM(«parameter.generateVariableDefaultValue»)'''
			STCallNamedOutputArgument case argument.
				not: '''ST_EXTEND_LIFETIME(CIEC_ANY_BIT_NOT(«argument.argument.generateExpression»))'''
			case (argument.argument instanceof STMemberAccessExpression) &&
				(argument.argument as STMemberAccessExpression).
					member instanceof STMultibitPartialExpression: '''ST_EXTEND_LIFETIME(«argument.argument.generateExpression»)'''
			default:
				argument.argument.generateExpression
		}
	}

	def protected dispatch CharSequence generateExpression(STMultibitPartialExpression expr) //
	'''partial<«expr.specifier.generateMultiBitAccessSpecifier»>(«IF expr.expression !== null»«expr.expression.generateExpression»«ELSE»«expr.index»«ENDIF»)'''

	def protected CharSequence generateMultiBitAccessSpecifier(STMultiBitAccessSpecifier spec) {
		switch (spec) {
			case null,
			case X: "CIEC_BOOL"
			case B: "CIEC_BYTE"
			case W: "CIEC_WORD"
			case D: "CIEC_DWORD"
			case L: "CIEC_LWORD"
		}
	}

	def protected dispatch CharSequence generateExpression(STNumericLiteral expr) //
	'''«(expr.resultType as DataType).generateTypeName»(«expr.value»)'''

	def protected dispatch CharSequence generateExpression(STStringLiteral expr) {
		val type = expr.resultType as DataType
		'''«type.generateTypeName»''' + switch (type) {
			AnyStringType: '''("«expr.value.toString.convertToJavaString»")'''
			CharType: '''(«String.format("0x%02x",  expr.value.toString.getBytes(StandardCharsets.UTF_8).get(0))»)'''
			WcharType: '''(u'«expr.value.toString.convertToJavaString»')'''
		}
	}

	def protected dispatch CharSequence generateExpression(STDateLiteral expr) {
		val type = expr.resultType as DataType
		'''«type.generateTypeName»(«expr.value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L»)'''
	}

	def protected dispatch CharSequence generateExpression(STTimeLiteral expr) {
		val type = expr.resultType as DataType
		'''«type.generateTypeName»(«expr.value.toNanos»)'''
	}

	def protected dispatch CharSequence generateExpression(STTimeOfDayLiteral expr) {
		val type = expr.resultType as DataType
		'''«type.generateTypeName»(«expr.value.toNanoOfDay»)'''
	}

	def protected dispatch CharSequence generateExpression(STDateAndTimeLiteral expr) {
		val type = expr.resultType as DataType
		'''«type.generateTypeName»(«LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(expr.value, ChronoUnit.NANOS)»)'''
	}

	def protected dispatch CharSequence generateTemplateExpression(STBinaryExpression expr) {
		switch (expr.op) {
			case RANGE: '''«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»'''
			case AMPERSAND: '''AND(«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»)'''
			default: '''«expr.op.getName»(«expr.left.generateTemplateExpression», «expr.right.generateTemplateExpression»)'''
		}
	}

	def protected dispatch CharSequence generateTemplateExpression(STUnaryExpression expr) //
	'''«expr.op.getName»(«expr.expression.generateTemplateExpression»)'''

	def protected dispatch CharSequence generateTemplateExpression(STNumericLiteral expr) { expr.value.toString }

	def protected dispatch CharSequence generateVariableDefaultValue(INamedElement feature) {
		errors.add('''The variable «feature.eClass.name» is not supported''')
		"0"
	}

	def protected dispatch CharSequence generateVariableDefaultValue(VarDeclaration variable) {
		val support = variableLanguageSupport.computeIfAbsent(variable)[new VarDeclarationSupport(it)]
		val result = support.generate(emptyMap)
		errors.addAll(support.getErrors)
		warnings.addAll(support.getWarnings)
		infos.addAll(support.getInfos)
		result
	}

	def protected dispatch CharSequence generateVariableDefaultValue(STVarDeclaration variable) {
		if (variable.defaultValue !== null)
			variable.defaultValue.generateInitializerExpression
		else
			variable.featureType.generateTypeDefaultValue
	}

	def protected dispatch CharSequence generateFeatureName(INamedElement feature) {
		errors.add('''The feature «feature.eClass.name» is not supported''')
		""
	}

	def protected dispatch CharSequence generateFeatureName(VarDeclaration feature) {
		if (feature.rootContainer instanceof BaseFBType) {
			val fbType = feature.rootContainer as BaseFBType
			if (fbType.internalConstVars.contains(feature))
				'''st_const_«feature.name»'''
			else
				'''st_«feature.name»()'''
		} else {
			'''«feature.name»()'''
		}
	}

	def protected dispatch CharSequence generateFeatureName(STVarDeclaration feature) {
		switch (feature.eContainer) {
			STVarGlobalDeclarationBlock: '''st_global_«feature.name»'''
			default: '''st_lv_«feature.name»'''
		}
	}

	def protected dispatch CharSequence generateFeatureName(STFunction feature) '''func_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STStandardFunction feature) '''func_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(STMethod feature) '''method_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(FB feature) '''fb_«feature.name»()'''

	def protected dispatch CharSequence generateFeatureName(Event feature) '''evt_«feature.name»'''

	def protected dispatch CharSequence generateFeatureName(AdapterDeclaration feature) '''st_«feature.name»()'''

	def protected CharSequence generateTypeName(STVarDeclaration variable) { variable.generateTypeName(false) }

	def protected CharSequence generateTypeName(STVarDeclaration variable, boolean output) {
		variable.count.reverseView.fold(
			variable.ranges.reverseView.fold((variable.type as DataType).generateTypeName) [ type, range |
				'''«IF output»CIEC_ARRAY_COMMON«ELSE»CIEC_ARRAY_FIXED«ENDIF»<«type»«IF !output», «range.generateTemplateExpression»«ENDIF»>'''
			].toString
		) [ type, count |
			'''«IF output»CIEC_ARRAY_COMMON«ELSE»CIEC_ARRAY_VARIABLE«ENDIF»<«type»>'''
		]
	}

	def protected CharSequence generateTypeName(DataType type) {
		switch (type) {
			ArrayType:
				type.subranges.reverseView.fold(type.baseType.generateTypeName) [ result, subrange |
					val fixed = subrange.setLowerLimit && subrange.setUpperLimit
					'''«IF fixed»CIEC_ARRAY_FIXED«ELSE»CIEC_ARRAY_VARIABLE«ENDIF»<«result»«IF fixed», «subrange.lowerLimit», «subrange.upperLimit»«ENDIF»>'''
				].toString
			default:
				ForteNgExportUtil.generateTypeName(type)
		}
	}

	def protected CharSequence generateTypeDefaultValue(INamedElement type) {
		switch (type) {
			AnyStringType: '''«type.generateTypeName»("")'''
			AnyElementaryType: '''«type.generateTypeName»(0)'''
			ArrayType: '''«type.generateTypeName»{}'''
			StructuredType: '''«type.generateTypeName»()'''
			default:
				"0"
		}
	}

	def protected int getIntegerFromConstantExpression(STExpression expr) {
		try {
			((expr as STNumericLiteral).value as BigInteger).intValueExact
		} catch (Exception e) {
			errors.add("Not a constant integer expression")
			1
		}
	}

	/**
	 * Get contained dependencies of an object defined in the current source
	 * @param object An object defined/contained <b>in the current source</b>
	 * @return The list of objects <b>from other sources/headers</b> on which <t>object</t> (recursively) depends
	 */
	def protected Set<INamedElement> getContainedDependencies(EObject object) {
		object.<EObject>getAllProperContents(true).toIterable.flatMap[dependencies].toSet
	}

	/**
	 * Get dependencies of an object defined in the current source
	 * @param object An object defined/contained <b>in the current source</b>
	 * @return The list of objects <b>from other sources/headers</b> on which <t>object</t> depends
	 */
	def protected Iterable<INamedElement> getDependencies(EObject object) {
		switch (object) {
			STVarDeclaration:
				#[object.type]
			STStructInitializerExpression:
				// need dependencies of default values generated in initializer
				object.mappedStructInitElements.entrySet.filter[value === null].flatMap[key.defaultDependencies]
			STNumericLiteral:
				#[object.resultType]
			STStringLiteral:
				#[object.resultType]
			STDateLiteral:
				#[object.type]
			STTimeLiteral:
				#[object.type]
			STTimeOfDayLiteral:
				#[object.type]
			STDateAndTimeLiteral:
				#[object.type]
			STFeatureExpression: // feature expressions may refer to definitions contained in other sources
				object.feature.featureDependencies + object.argumentDependencies
			STFunction:
				object.returnType !== null ? #[object.returnType] : emptySet
			default:
				emptySet
		}
	}

	def protected Iterable<INamedElement> getFeatureDependencies(INamedElement feature) {
		switch (feature) {
			VarDeclaration:
				variableLanguageSupport.computeIfAbsent(feature)[new VarDeclarationSupport(it)].getDependencies(
					#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE})
			STVarDeclaration case feature.eContainer instanceof STVarGlobalDeclarationBlock:
				#[LibraryElementFactory.eINSTANCE.createLibraryElement => [
					name = feature.sourceName
				]]
			STVarDeclaration:
				#[feature.type]
			STFunction:
				#[LibraryElementFactory.eINSTANCE.createLibraryElement => [
					name = feature.sourceName
				]] + feature.parameterDependencies
			ICallable:
				feature.parameterDependencies
			default:
				emptySet
		}
	}

	def protected Iterable<INamedElement> getParameterDependencies(ICallable feature) {
		(feature.inputParameters + feature.outputParameters + feature.inOutParameters).flatMap [
			defaultDependencies // need dependencies of default values possibly generated in call
		]
	}

	def protected Iterable<INamedElement> getDefaultDependencies(INamedElement feature) {
		switch (feature) {
			VarDeclaration:
				variableLanguageSupport.computeIfAbsent(feature)[new VarDeclarationSupport(it)].
					getDependencies(emptyMap)
			STVarDeclaration:
				#[feature.type] + feature.containedDependencies
			default:
				emptySet
		}
	}

	def protected Iterable<INamedElement> getArgumentDependencies(STFeatureExpression expression) {
		if (expression.parameters.filter(STCallNamedOutputArgument).exists[not])
			#[LibraryElementFactory.eINSTANCE.createLibraryElement => [
				name = "forte_any_bit_not_decorator"
			]]
		else
			emptySet
	}

	def protected generateLineDirective(EObject element) {
		val node = element.findActualNodeFor
		val sourceName = element.eResource?.URI?.lastSegment
		if (node !== null && sourceName !== null)
			'''#line «node.startLine» "«sourceName»"
			'''
		else if (node !== null)
			'''#line «node.startLine» "unknown"
			'''
		else
			""
	}

	def protected generateUniqueVariableName() '''st_lv_synthetic_«uniqueVariableIndex++»'''
}
