/*******************************************************************************
 * Copyright (c) 2016, 2018 fortiss GmbH, Johannes Kepler Universtiy
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - connections across subapp borders
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.commands.CreateSubAppCrossingConnectionsCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public abstract class InterfaceElementEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		final AbstractConnectionCreateCommand command = (AbstractConnectionCreateCommand) request.getStartCommand();
		command.setDestination(((InterfaceEditPart) getHost()).getModel());

		final FBNetwork newParent = checkConnectionParent(command.getSource(), command.getDestination(),
				command.getParent());
		if (newParent != null) {
			command.setParent(newParent);
			return command;
		}
		// if we are here it is not a direct connection try border crossing command
		return CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(command.getSource(),
				command.getDestination());
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return createReconnectCommand(request, false);
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return createReconnectCommand(request, true);
	}

	private Command createReconnectCommand(final ReconnectRequest request, final boolean isSourceReconnect) {
		final AbstractReconnectConnectionCommand cmd = createReconnectCommand(
				(Connection) request.getConnectionEditPart().getModel(), isSourceReconnect, getRequestTarget(request));
		final FBNetwork newParent = checkConnectionParent(cmd.getNewSource(), cmd.getNewDestination(), cmd.getParent());
		if (newParent != null) {
			cmd.setParent(newParent);
			return cmd;
		}
		return null;
	}

	protected FBNetwork getParentNetwork() {
		return getHost().getRoot().getAdapter(FBNetwork.class);
	}

	protected abstract AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget);

	private static FBNetwork checkConnectionParent(final IInterfaceElement source, final IInterfaceElement destination,
			final FBNetwork parent) {
		final FBNetwork srcParent = getFBNetwork4Pin(source);
		final FBNetwork dstParent = getFBNetwork4Pin(destination);

		if ((srcParent != null) && (dstParent != null)) {
			if (srcParent == dstParent) {
				return checkParentInSameNetwork(source, parent, srcParent);
			}
			if ((source.getFBNetworkElement() instanceof SubApp)
					&& (((SubApp) source.getFBNetworkElement()).getSubAppNetwork() == dstParent)) {
				// we have a connection from a subapp pin to an internal FB
				return dstParent;
			}
			if ((destination.getFBNetworkElement() instanceof SubApp)
					&& (((SubApp) destination.getFBNetworkElement()).getSubAppNetwork() == srcParent)) {
				// we have a connection from a subapp pin to an internal FB
				return srcParent;
			}
		}
		return null;
	}

	private static FBNetwork checkParentInSameNetwork(final IInterfaceElement source, final FBNetwork parent,
			final FBNetwork srcParent) {
		if (srcParent == parent) {
			return parent;
		}
		if ((source.getFBNetworkElement() instanceof SubApp)
				&& (((SubApp) source.getFBNetworkElement()).getSubAppNetwork() == parent)) {
			// we have a subapp pin to pin connection inside of a subapp
			return parent;
		}
		// we have a connection in an unfolded subapp
		return srcParent;
	}

	private static FBNetwork getFBNetwork4Pin(final IInterfaceElement pin) {
		if (pin != null) {
			if (pin.getFBNetworkElement() != null) {
				return pin.getFBNetworkElement().getFbNetwork();
			}
			if (pin.eContainer().eContainer() instanceof CompositeFBType) {
				return ((CompositeFBType) pin.eContainer().eContainer()).getFBNetwork();
			}
		}
		return null;
	}

	private static IInterfaceElement getRequestTarget(final ReconnectRequest request) {
		final EditPart target = request.getTarget();
		if (target.getModel() instanceof IInterfaceElement) {
			return (IInterfaceElement) target.getModel();
		}
		return null;
	}
}
