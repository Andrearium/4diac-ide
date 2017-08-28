/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.filter

import java.util.ArrayList
import java.util.List
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECC
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.With

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils.*
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent

class LuaConstants {

	static final int FB_STATE = 0;
	// 8 bit (32-25) to identify type (input/output/adapter/internal)
	// 8 bit (24-17) for adapter indexes, 
	// 16 bit (16-1) for input/output indexes
	static final int FB_DI_FLAG = 33554432; // 2^25
	static final int FB_DO_FLAG = 67108864; // 2^26
	static final int FB_AD_FLAG = 134217728; // 2^27
	static final int FB_IN_FLAG = 268435456; // 2^28

	def static luaTypeName(FBType type) '''FORTE_«type.name»'''

	def static luaStateVariable() '''STATE'''

	def static luaFBStateVarName() '''FB_STATE'''

	def static luaStateName(ECState state) '''ECC_«state.name»'''

	def static luaInputEventName(Event event) '''«IF event instanceof AdapterEvent»AEI_«event.name.replaceAll("\\.","_")»«ELSE»EI_«event.name»«ENDIF»'''

	def static luaOutputEventName(Event event) '''«IF event instanceof AdapterEvent»AEO_«event.name.replaceAll("\\.","_")»«ELSE»EO_«event.name»«ENDIF»'''
	
	def static luaAdapterInputEventName(Event event, String adapterName) '''AEI_«adapterName»_«event.name»'''

	def static luaAdapterOutputEventName(Event event, String adapterName) '''AEO_«adapterName»_«event.name»'''	

	def static luaFBInputVarName(VarDeclaration decl) '''DI_«decl.name»'''

	def static luaFBOutputVarName(VarDeclaration decl) '''DO_«decl.name»'''
	
	def static luaFBAdapterInputVarName(VarDeclaration decl, String adapterName) '''ADI_«adapterName»_«decl.name»'''

	def static luaFBAdapterOutputVarName(VarDeclaration decl, String adapterName) '''ADO_«adapterName»_«decl.name»'''

	def static luaFBInternalVarName(VarDeclaration decl) '''IN_«decl.name»'''

	def static luaVariable(VarDeclaration decl) '''VAR_«decl.name»'''

	def static luaAdapterVariable(String name, String adapterInstanceName) '''VAR_«adapterInstanceName»_«name»'''

	def static luaAlgorithmName(Algorithm alg) '''alg_«alg.name»'''

	def static luaFBStateConstant() '''local «luaFBStateVarName» = «FB_STATE»'''

	def static luaStateConstants(ECC ecc) '''
		«FOR state : ecc.ECState»
			local «state.luaStateName» = «ecc.ECState.indexOf(state)»
		«ENDFOR»
	'''

	def static luaEventConstants(InterfaceList ifl) '''
		«FOR event : ifl.eventInputs»
			local «event.luaInputEventName» = «ifl.eventInputs.indexOf(event)»
		«ENDFOR»
		«FOR event : ifl.eventOutputs»
			local «event.luaOutputEventName» = «ifl.eventOutputs.indexOf(event)»
		«ENDFOR»
	'''

	def static luaFBVariableConstants(InterfaceList ifl) '''
		«FOR decl : ifl.inputVars»
			local «decl.luaFBInputVarName» = «FB_DI_FLAG.bitwiseOr(ifl.inputVars.indexOf(decl))»
		«ENDFOR»
		«FOR decl : ifl.outputVars»
			local «decl.luaFBOutputVarName» = «FB_DO_FLAG.bitwiseOr(ifl.outputVars.indexOf(decl))»
		«ENDFOR»
	'''
	
	def static luaFBAdapterConstants(InterfaceList ifl) '''
		«FOR socket : ifl.sockets»
			«socket.luaFBAdapterInterfaceConstants(ifl.sockets)»
		«ENDFOR»
		«FOR plug : ifl.plugs»
			«plug.luaFBAdapterInterfaceConstants(ifl.plugs)»
		«ENDFOR»
	'''

	def static luaFBAdapterInterfaceConstants(AdapterDeclaration adapter, EList<?> ifl)'''
	«var aifl = (adapter.type as AdapterType).adapterFBType.interfaceList»
	«IF adapter.isInput»
		«FOR decl : aifl.eventOutputs»
		local «decl.luaAdapterInputEventName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.eventOutputs.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.eventInputs»
		local «decl.luaAdapterOutputEventName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.eventInputs.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.outputVars»
		local «decl.luaFBAdapterInputVarName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(FB_DI_FLAG).bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.outputVars.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.inputVars»
		local «decl.luaFBAdapterOutputVarName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(FB_DO_FLAG).bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.inputVars.indexOf(decl))»
		«ENDFOR»
	«ELSE»
		«FOR decl : aifl.eventInputs»
		local «decl.luaAdapterInputEventName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.eventInputs.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.eventOutputs»
		local «decl.luaAdapterOutputEventName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.eventOutputs.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.inputVars»
		local «decl.luaFBAdapterInputVarName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(FB_DI_FLAG).bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.inputVars.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.outputVars»
		local «decl.luaFBAdapterOutputVarName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(FB_DO_FLAG).bitwiseOr(ifl.indexOf(adapter) << 16).bitwiseOr(aifl.outputVars.indexOf(decl))»
		«ENDFOR»
	«ENDIF»
	'''

	def static luaInternalConstants(BasicFBType type) '''
		«FOR decl : type.internalVars»
			local «decl.luaFBInternalVarName» = «FB_IN_FLAG.bitwiseOr(type.internalVars.indexOf(decl))»
		«ENDFOR»
	'''

	def static luaConstants(BasicFBType type) '''
		«luaFBStateConstant»
		«type.ECC.luaStateConstants»
		«type.interfaceList.luaEventConstants»
		«type.interfaceList.luaFBVariableConstants»
		«type.interfaceList.luaFBAdapterConstants»
		«type.luaInternalConstants»
	'''

	def static luaFBStateVariable() '''fb[«luaFBStateVarName»]'''

	def static luaFBVariable(VarDeclaration decl) {
		val type = decl.rootContainer as FBType
		if (type.interfaceList.inputVars.contains(decl)) {
			'''fb[«decl.luaFBInputVarName»]'''
		} else if (type.interfaceList.outputVars.contains(decl)) {
			'''fb[«decl.luaFBOutputVarName»]'''
		} else if (type instanceof BasicFBType && (type as BasicFBType).internalVars.contains(decl)) {
			'''fb[«decl.luaFBInternalVarName»]'''
		} else {
			throw new IllegalArgumentException('''Unknown kind of variable «decl.name»''')
		}
	}

	def static luaFBVariablesPrefix(Iterable<VarDeclaration> variables) '''
		local «luaStateVariable» = «luaFBStateVariable»
		«FOR variable : variables»
			local «variable.luaVariable» = «variable.luaFBVariable»
		«ENDFOR»
	'''
	
	def static luaFBAdapterVariablesPrefix(Iterable<AdapterVariable> variables) '''
		«FOR av : variables»
			«var index = variables.toList.indexOf(av)»
			«var sublist = variables.toList.subList(0, index)»
			«IF !(sublist.map[it.^var].contains(av.^var) && sublist.map[it.adapter].contains(av.adapter))»
			local «av.^var.name.luaAdapterVariable(av.adapter.name)» = fb[«if(av.adapter.isInput) av.^var.luaFBAdapterInputVarName(av.adapter.name) else av.^var.luaFBAdapterOutputVarName(av.adapter.name)»]
			«ENDIF»
		«ENDFOR»
	'''

	def static luaFBVariablesSuffix(Iterable<VarDeclaration> variables) '''
		«FOR variable : variables.filter[!it.isIsInput]»
			«variable.luaFBVariable» = «variable.luaVariable»
		«ENDFOR»
	'''

	def static luaFBAdapterVariablesSuffix(Iterable<AdapterVariable> variables) '''
		«FOR variable : variables»
			«IF !variable.adapter.isInput»
			«var index = variables.toList.indexOf(variable)»
			«var sublist = variables.toList.subList(0, index)»
			«IF !(sublist.map[it.^var].contains(variable.^var) && sublist.map[it.adapter].contains(variable.adapter))»					
			fb[«variable.^var.luaFBAdapterOutputVarName(variable.adapter.name)»] = «variable.^var.name.luaAdapterVariable(variable.adapter.name)»
			«ENDIF»
			«ENDIF»
		«ENDFOR»
	'''

	def static luaSendOutputEvent(Event event) '''fb(«event.luaOutputEventName»)'''

	def static luaSendAdapterOutputEvent(Event event) '''fb(AEO_«event.name.replaceAll("\\.", "_")»)'''

	def public static getEventWith(Event event, List<Integer> with, List<VarDeclaration> vars) {
		if (event.with.empty) {
			return -1
		}
		val index = with.size
		for (With w : event.with) {
			with.add(vars.indexOf(w.variables))
		}
		with.add(255)
		return index
	}
	
	def public static getTypeList(List<VarDeclaration> vars) {
		val typeList = new ArrayList<Object>(vars.size)
		vars.forEach[
			if(it.isArray) {
				typeList.add("ARRAY")
				typeList.add(it.arraySize)
			}
			typeList.add(it.typeName)
		]
		return typeList
	}

	def static luaInterfaceSpec(InterfaceList ifl) {
		val inputWith = new ArrayList<Integer>
		val inputWithIndexes = new ArrayList<Integer>
		for (Event e : ifl.eventInputs) {
			inputWithIndexes.add(e.getEventWith(inputWith, ifl.inputVars))
		}
		val outputWith = new ArrayList<Integer>
		val outputWithIndexes = new ArrayList<Integer>
		for (Event e : ifl.eventOutputs) {
			outputWithIndexes.add(e.getEventWith(outputWith, ifl.outputVars))
		}
		'''
		local interfaceSpec = {
		  numEIs = «ifl.eventInputs.size»,
		  EINames = «ifl.eventInputs.map[it.name].luaStringList»,
		  EIWith = «inputWith.luaIntegerList»,
		  EIWithIndexes = «inputWithIndexes.luaIntegerList»,
		  numEOs = «ifl.eventOutputs.size»,
		  EONames = «ifl.eventOutputs.map[it.name].luaStringList»,
		  EOWith = «outputWith.luaIntegerList»,
		  EOWithIndexes = «outputWithIndexes.luaIntegerList»,
		  numDIs = «ifl.inputVars.size»,
		  DINames = «ifl.inputVars.map[it.name].luaStringList»,
		  DIDataTypeNames = «ifl.inputVars.typeList.luaValueList»,
		  numDOs = «ifl.outputVars.size»,
		  DONames = «ifl.outputVars.map[it.name].luaStringList»,
		  DODataTypeNames = «ifl.outputVars.typeList.luaValueList»,
		  numAdapters = «ifl.plugs.size + ifl.sockets.size»,
		  adapterInstanceDefinition = {
		    «ifl.plugs.map['''{adapterNameID = "«it.name»", adapterTypeNameID = "«it.typeName»", isPlug = true}'''].join(",\n")»«IF !ifl.sockets.isEmpty && !ifl.plugs.isEmpty»,«ENDIF»
		    «ifl.sockets.map['''{adapterNameID = "«it.name»", adapterTypeNameID = "«it.typeName»", isPlug = false}'''].join(",\n")»
		  }
		}'''
	}

	def static luaInternalVarsInformation(BasicFBType type) '''
	local internalVarsInformation = {
	  numIntVars = «type.internalVars.size»,
	  intVarsNames = «type.internalVars.map[it.name].luaStringList»,
	  intVarsDataTypeNames = «type.internalVars.typeList.luaValueList»
	}'''
	
}
