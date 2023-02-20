/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added delete option for error marker pins
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteErrorMarkerCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class ErrorMarkerInterfaceEditPart extends InterfaceEditPart {

	public ErrorMarkerInterfaceEditPart() {
		super();
	}

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		// we don't want to allow any connection additions here therefore return null
		return null;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a errorMarker interface elements
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {

			@Override
			protected Command createDeleteCommand(final GroupRequest request) {
				if (getHost().getModel() instanceof IInterfaceElement) {
					return new DeleteErrorMarkerCommand(getModel(), getModel().getFBNetworkElement());
				}
				return null;
			}

		});
	}

	@Override
	public ErrorMarkerInterface getModel() {
		return (ErrorMarkerInterface) super.getModel();
	}

	@Override
	protected String getLabelText() {
		final ErrorMarkerInterface model = getModel();
		return model.getErrorMessage() != null ? super.getLabelText() + ": " + model.getErrorMessage() //$NON-NLS-1$
		: super.getLabelText();
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = new InterfaceFigure();
		figure.setBackgroundColor(ColorConstants.red);
		figure.setOpaque(true);
		return figure;
	}

}
