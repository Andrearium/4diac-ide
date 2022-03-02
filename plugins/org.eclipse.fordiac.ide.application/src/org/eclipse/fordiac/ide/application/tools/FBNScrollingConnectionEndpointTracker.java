/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.tools;

import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.tools.ScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;

public class FBNScrollingConnectionEndpointTracker extends ScrollingConnectionEndpointTracker {

	private static final int MOUSE_LEFT = 1;

	public FBNScrollingConnectionEndpointTracker(final org.eclipse.gef.ConnectionEditPart cep) {
		super(cep);
	}

	private ConnectionRoutingData originalRoutingData;

	@Override
	protected boolean handleButtonDown(final int button) {
		if (button == MOUSE_LEFT) {
			// only check selection on left mouse click
			performSelection();
		}
		return super.handleButtonDown(button);
	}


	/** This selection updater is based on perform selection from {@link org.eclipse.gef.tools.SelectEditPartTracker} */
	protected void performSelection() {
		final EditPartViewer viewer = getCurrentViewer();
		final List<Object> selectedObjects = viewer.getSelectedEditParts();

		if (getCurrentInput().isModKeyDown(SWT.MOD1)) {
			if (selectedObjects.contains(getConnectionEditPart())) {
				viewer.deselect(getConnectionEditPart());
			} else {
				viewer.appendSelection(getConnectionEditPart());
			}
		} else if (getCurrentInput().isShiftKeyDown()) {
			viewer.appendSelection(getConnectionEditPart());
		} else {
			viewer.select(getConnectionEditPart());
		}
	}

	@Override
	protected boolean handleDragStarted() {
		final Connection conn = get4diacConnection();
		if (conn != null) {
			originalRoutingData = EcoreUtil.copy(conn.getRoutingData());
		}
		return super.handleDragStarted();
	}

	@Override
	protected void executeCurrentCommand() {
		if (shouldRestoreRoutingData()) {
			get4diacConnection().setRoutingData(originalRoutingData);
		}
		super.executeCurrentCommand();
	}

	@Override
	protected Insets getCanvasBorder() {
		final org.eclipse.fordiac.ide.model.libraryElement.Connection conn = get4diacConnection();
		if (null != conn) {
			conn.getRoutingData().setNeedsValidation(true);
			if (conn.getRoutingData().is3SegementData()
					&& RequestConstants.REQ_RECONNECT_SOURCE.equals(getCommandName())) {
				// if we have a 3 segment connection and we are dragging the destination we need to take the first
				// segment into account for the border
				final Insets adjustedBorder = new Insets(super.getCanvasBorder());
				adjustedBorder.right += conn.getRoutingData().getDx1();
				return adjustedBorder;
			}
			if (conn.getRoutingData().is5SegementData()) {
				return get5SegmentCanvasBorder(conn.getRoutingData());
			}
		}

		return super.getCanvasBorder();
	}

	private Insets get5SegmentCanvasBorder(final ConnectionRoutingData routingData) {
		final Insets adjustedBorder = new Insets(super.getCanvasBorder());
		if (RequestConstants.REQ_RECONNECT_SOURCE.equals(getCommandName())) {
			adjustedBorder.right += routingData.getDx1();
			if (routingData.getDy() < 0) {
				adjustedBorder.top -= routingData.getDy();
			}
		}
		if (RequestConstants.REQ_RECONNECT_TARGET.equals(getCommandName())) {
			adjustedBorder.left += routingData.getDx2();
		}
		return adjustedBorder;
	}

	private boolean shouldRestoreRoutingData() {
		final Connection con = get4diacConnection();
		if (con != null) {
			final Command curCommand = getCurrentCommand();
			if (curCommand != null && curCommand.canExecute()) {
				if (curCommand instanceof AbstractReconnectConnectionCommand) {
					final AbstractReconnectConnectionCommand cmd = (AbstractReconnectConnectionCommand) curCommand;
					return con.getSource().equals(cmd.getNewSource())
							&& con.getDestination().equals(cmd.getNewDestination());
				}

			} else {
				return true;
			}
		}
		return false;
	}

	protected Connection get4diacConnection() {
		if (getConnectionEditPart().getModel() instanceof org.eclipse.fordiac.ide.model.libraryElement.Connection) {
			return (Connection) getConnectionEditPart().getModel();
		}
		return null;
	}

}