/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - fix positioning of fbnetwork
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

public class FlattenSubAppCommand extends Command {
	private final SubApp subapp;
	private final FBNetwork parent;
	private final List<FBNetworkElement> elements = new ArrayList<>();
	private final List<EventConnection> transferEventConnections = new ArrayList<>();
	private final List<DataConnection> transferDataConnections = new ArrayList<>();
	private final List<AdapterConnection> transferAdapterConnections = new ArrayList<>();
	private final CompoundCommand deleteCommands = new CompoundCommand();
	private final CompoundCommand createCommands = new CompoundCommand();
	private final CompoundCommand mappCommands = new CompoundCommand();
	private final Point fbnetworkPosInSubapp;

	public FlattenSubAppCommand(final SubApp subapp) {
		super(Messages.FlattenSubAppCommand_LABEL_FlattenSubAppCommand);
		this.subapp = subapp;
		parent = subapp.getFbNetwork();
		fbnetworkPosInSubapp = FBNetworkHelper
				.getTopLeftCornerOfFBNetwork(subapp.getSubAppNetwork().getNetworkElements());
	}

	@Override
	public void execute() {
		elements.addAll(subapp.getSubAppNetwork().getNetworkElements());
		FBNetworkHelper.moveFBNetworkByOffset(elements, getOriginalPositionX(), getOriginalPositionY());

		checkConnections();
		createMapCommands();

		deleteCommands.add(new DeleteFBNetworkElementCommand(subapp));
		deleteCommands.execute();

		subapp.getSubAppNetwork().getNetworkElements().removeAll(elements);
		parent.getNetworkElements().addAll(elements);

		subapp.getSubAppNetwork().getEventConnections().removeAll(transferEventConnections);
		parent.getEventConnections().addAll(transferEventConnections);

		subapp.getSubAppNetwork().getDataConnections().removeAll(transferDataConnections);
		parent.getDataConnections().addAll(transferDataConnections);

		subapp.getSubAppNetwork().getAdapterConnections().removeAll(transferAdapterConnections);
		parent.getAdapterConnections().addAll(transferAdapterConnections);

		createCommands.execute();
		mappCommands.execute();

		ElementSelector.selectViewObjects(elements);
	}

	@Override
	public void redo() {
		deleteCommands.redo();
		subapp.getSubAppNetwork().getNetworkElements().removeAll(elements);
		parent.getNetworkElements().addAll(elements);
		FBNetworkHelper.moveFBNetworkByOffset(elements, getOriginalPositionX(), getOriginalPositionY());

		subapp.getSubAppNetwork().getEventConnections().removeAll(transferEventConnections);
		parent.getEventConnections().addAll(transferEventConnections);

		subapp.getSubAppNetwork().getDataConnections().removeAll(transferDataConnections);
		parent.getDataConnections().addAll(transferDataConnections);

		subapp.getSubAppNetwork().getAdapterConnections().removeAll(transferAdapterConnections);
		parent.getAdapterConnections().addAll(transferAdapterConnections);

		createCommands.redo();
		mappCommands.redo();
	}

	@Override
	public void undo() {
		mappCommands.undo();
		createCommands.undo();
		parent.getNetworkElements().removeAll(elements);
		subapp.getSubAppNetwork().getNetworkElements().addAll(elements);
		FBNetworkHelper.removeXYOffsetForFBNetwork(elements); // ??

		parent.getEventConnections().removeAll(transferEventConnections);
		subapp.getSubAppNetwork().getEventConnections().addAll(transferEventConnections);

		parent.getDataConnections().removeAll(transferDataConnections);
		subapp.getSubAppNetwork().getDataConnections().addAll(transferDataConnections);

		parent.getAdapterConnections().removeAll(transferAdapterConnections);
		subapp.getSubAppNetwork().getAdapterConnections().addAll(transferAdapterConnections);

		deleteCommands.undo();
	}

	private void checkConnections() {
		checkConnectionList(subapp.getSubAppNetwork().getEventConnections(), transferEventConnections);
		checkConnectionList(subapp.getSubAppNetwork().getDataConnections(), transferDataConnections);
		checkConnectionList(subapp.getSubAppNetwork().getAdapterConnections(), transferAdapterConnections);

	}

	private int getOriginalPositionX() {
		return -subapp.getPosition().getX() + fbnetworkPosInSubapp.x;
	}

	private int getOriginalPositionY() {
		return -subapp.getPosition().getY() + fbnetworkPosInSubapp.y;
	}

	private void createMapCommands() {
		if (subapp.isMapped()) {
			for (final FBNetworkElement fbNetworkElement : elements) {
				mappCommands.add(new MapToCommand(fbNetworkElement, subapp.getResource()));
			}
		}
	}

	private <T extends Connection> void checkConnectionList(final List<T> connectionList, final List<T> transferConnectionList) {
		for (final T connection : connectionList) {
			if ((connection.getSourceElement() != subapp) && (connection.getDestinationElement() != subapp)) {
				// it is an internal connection transfer it
				transferConnectionList.add(connection);
			} else {
				deleteCommands.add(new DeleteConnectionCommand(connection));
				if ((connection.getSourceElement() == subapp) && (connection.getDestinationElement() == subapp)) {
					for (final Connection inboundConn : connection.getSource().getInputConnections()) {
						for (final Connection outboundConn : connection.getDestination().getOutputConnections()) {
							createCommands
							.add(createConnCreateCmd(inboundConn.getSource(), outboundConn.getDestination()));
						}
					}
				} else if (connection.getSourceElement() == subapp) {
					// for each connection coming into the sub app create one connection in the
					// outer fb network
					for (final Connection inboundConn : connection.getSource().getInputConnections()) {
						createCommands.add(createConnCreateCmd(inboundConn.getSource(), connection.getDestination()));
					}
				} else if (connection.getDestinationElement() == subapp) {
					// for each connection going from the subapp outputs create one connection in
					// the other fb network
					for (final Connection outboundConn : connection.getDestination().getOutputConnections()) {
						createCommands.add(createConnCreateCmd(connection.getSource(), outboundConn.getDestination()));
					}
				}
			}
		}
	}

	private AbstractConnectionCreateCommand createConnCreateCmd(final IInterfaceElement source,
			final IInterfaceElement destination) {
		AbstractConnectionCreateCommand cmd = null;
		if (source instanceof Event) {
			cmd = new EventConnectionCreateCommand(parent);
		} else if (source instanceof AdapterDeclaration) {
			cmd = new AdapterConnectionCreateCommand(parent);
		} else if (source instanceof VarDeclaration) {
			cmd = new DataConnectionCreateCommand(parent);
		}

		if (null != cmd) {
			cmd.setSource(source);
			cmd.setDestination(destination);
		}
		return cmd;
	}

}
