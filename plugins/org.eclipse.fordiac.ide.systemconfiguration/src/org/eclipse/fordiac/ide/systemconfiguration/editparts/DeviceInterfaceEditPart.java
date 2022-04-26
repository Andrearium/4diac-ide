/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - allowed resource drop on on whole interfaces
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.policies.DataInterfaceLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceMoveCommand;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.SelectEditPartTracker;

/**
 * The Class DeviceInterfaceEditPart.
 *
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class DeviceInterfaceEditPart extends InterfaceEditPart {

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		return new SelectEditPartTracker(this);
	}

	@Override
	protected List<?> getModelSourceConnections() {
		return Collections.emptyList();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		return Collections.emptyList();
	}

	@Override
	protected LayoutEditPolicy getLayoutPolicy() {
		return new DataInterfaceLayoutEditPolicy() {
			@Override
			protected Command getCreateCommand(final CreateRequest request) {
				if (((getHost() instanceof InterfaceEditPart)
						&& (request.getNewObjectType() instanceof ResourceTypeEntry))) {
					final ResourceTypeEntry type = (ResourceTypeEntry) request.getNewObjectType();
					final Device dev = (Device) ((InterfaceEditPart) getHost()).getModel().eContainer();
					return new ResourceCreateCommand(type, dev, false);
				}
				return super.getCreateCommand(request);
			}

			@Override
			protected Command getAddCommand(final Request generic) {
				final ChangeBoundsRequest request = (ChangeBoundsRequest) generic;
				final List<EditPart> editParts = request.getEditParts();
				final CompoundCommand command = new CompoundCommand();

				for (final EditPart child : editParts) {
					if (child instanceof ResourceEditPart) {
						final Device targetDevice = (Device) ((InterfaceEditPart) getHost()).getModel().eContainer();
						return new ResourceMoveCommand(((ResourceEditPart) child).getModel(), targetDevice,
								targetDevice.getResource().size());
					}
				}
				return command.unwrap();
			}
		};
	}

}
