/*******************************************************************************
 * Copyright (c) 2011, 2023 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - copyied and modified from VariableInputContainer
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class VarInOutInputContainer extends AbstractContainerElement {

	public VarInOutInputContainer(final FBType fbtype) {
		super(fbtype);
	}

	@Override
	public List<? extends IInterfaceElement> getChildren() {
		return getFbType().getInterfaceList().getInOutVars();
	}
}