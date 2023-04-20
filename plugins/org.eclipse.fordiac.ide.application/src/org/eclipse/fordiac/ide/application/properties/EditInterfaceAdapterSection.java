/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppIETypeCommand;
import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.ResizingSubappInterfaceCreationCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceAdapterSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class EditInterfaceAdapterSection extends AbstractEditInterfaceAdapterSection {
	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		final AdapterType last = getLastUsedAdapterType(getType().getInterface(), interfaceElement, isInput);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(last,
				getCreationName(interfaceElement), getType().getInterface(), isInput, pos);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement interfaceElement,
			final boolean isInput, final int index) {
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(interfaceElement,
				isInput, getType().getInterface(), index);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected SubApp getInputType(final Object input) {
		return getSubAppFromInput(input);
	}

	public static SubApp getSubAppFromInput(final Object input) {
		if (input instanceof final SubAppForFBNetworkEditPart subAppEP) {
			return subAppEP.getModel();
		}
		if (input instanceof final UISubAppNetworkEditPart uiSubAppNEP) {
			return uiSubAppNEP.getSubApp();
		}
		if (input instanceof final SubApp subApp) {
			return subApp;
		}
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return new ChangeSubAppIETypeCommand(data, newType);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterface() : null;
	}

}
