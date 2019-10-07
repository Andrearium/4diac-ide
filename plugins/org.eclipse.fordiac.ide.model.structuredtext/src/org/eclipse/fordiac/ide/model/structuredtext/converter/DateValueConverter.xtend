/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.converter

import java.text.DateFormat
import java.util.Date
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode
import java.text.ParseException

class DateValueConverter extends AbstractNullSafeConverter<Date> {

	final extension DateFormat format;
	
	new(DateFormat format) {
		this.format = format
	}

	override protected internalToString(Date value) {
		value.format
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		if (string.nullOrEmpty) {
			throw new ValueConverterException("Couldn't convert empty string to a date value.", node, null)
		}
		try {
			string.parse
		} catch (ParseException e) {
			throw new ValueConverterException("Couldn't convert '" + string + "' to a date value.", node, e);
		}
	}

}
