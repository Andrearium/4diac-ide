/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - add clauses for handling error markers
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.edit.helper;

import org.eclipse.fordiac.ide.model.StructManipulation;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public final class InitialValueHelper {

	private InitialValueHelper() {
		throw new UnsupportedOperationException("Helper class InitialValueHelper should not be instantiated!"); //$NON-NLS-1$
	}

	public static String getInitialOrDefaultValue(final Object element) {
		if (element instanceof final VarDeclaration varDec) {
			if (hasInitalValue(element)) {
				return varDec.getValue().getValue();
			}
			if (hasDataTypeInitialValue(varDec)) {
				return StructManipulation.getMemberVarValue(varDec);
			}
			return getDefaultValue(element);
		}
		if (element instanceof final ErrorMarkerInterface marker) {
			if (hasInitalValue(element)) {
				return marker.getValue().getValue();
			}
			// no default value for error markers
		}
		return ""; //$NON-NLS-1$
	}

	private static boolean hasDataTypeInitialValue(final VarDeclaration varDec) {
		if (varDec.getFBNetworkElement() instanceof final StructManipulator) {
			return !StructManipulation.getMemberVarValue(varDec).isBlank();
		}
		return false;
	}

	public static String getDefaultValue(final Object element) {
		if (element instanceof final VarDeclaration varDec) {
			if ((varDec.getType() instanceof AnyType) && !IecTypes.GenericTypes.isAnyType(varDec.getType())) {
				try {
					return VariableOperations.newVariable(varDec).toString();
				} catch (final Exception exc) {
					// we are only logging it and jump to default value below
					FordiacLogHelper.logWarning("could not aquire VarDec default value", exc); //$NON-NLS-1$
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	public static Color getForegroundColor(final Object element) {
		if ((element instanceof VarDeclaration) && !hasInitalValue(element)) {

			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return null;
	}

	public static boolean hasInitalValue(final Object element) {
		if (element instanceof final VarDeclaration varDec) {
			return (varDec.getValue() != null) && (varDec.getValue().getValue() != null)
					&& !varDec.getValue().getValue().isEmpty();
		}
		if (element instanceof final ErrorMarkerInterface marker) {
			return (marker.getValue() != null) && (marker.getValue().getValue() != null)
					&& !marker.getValue().getValue().isEmpty();
		}
		return false;
	}

}
