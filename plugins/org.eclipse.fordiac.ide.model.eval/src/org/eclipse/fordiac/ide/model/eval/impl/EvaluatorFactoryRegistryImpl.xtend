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
package org.eclipse.fordiac.ide.model.eval.impl

import java.util.Map
import java.util.concurrent.ConcurrentHashMap
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.xtend.lib.annotations.Accessors

class EvaluatorFactoryRegistryImpl implements EvaluatorFactory.Registry{
	@Accessors
	final Map<Class<?>, EvaluatorFactory> classToFactoryMap = new ConcurrentHashMap
	
	override getFactory(Class<?> sourceClass) {
		classToFactoryMap.get(sourceClass)
	}
}
