/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH
 *               2018 - 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - allow navigation to parent by double-clicking on subapp
 *                 interface element
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.fordiac.ide.application.policies.AdapterNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.application.policies.EventNodeEditPolicy;
import org.eclipse.fordiac.ide.application.policies.VariableNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.ui.IEditorPart;

public class SubAppInternalInterfaceEditPart extends UntypedSubAppInterfaceElementEditPart {

	@Override
	protected IFigure createFigure() {
		final InterfaceFigure figure = new InterfaceFigure();
		figure.setBorder(new ConnectorBorder(getModel()) {
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}

	@Override
	public boolean isInput() {
		return !super.isInput();
	}

	@Override
	protected boolean isUnfoldedSubapp() {
		return false; // in the subapp editor we are always not unfolded
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}

	@Override
	protected void refreshVisuals() {
		getFigure().setToolTip(new ToolTipFigure(getModel()));
		super.refreshVisuals();
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// REQ_OPEN -> doubleclick
			goToParent();
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		if (isEvent()) {
			return new EventNodeEditPolicy() {
				@Override
				protected FBNetwork getParentNetwork() {
					return getSubappNetwork();
				}
			};
		}
		if (isAdapter()) {
			return new AdapterNodeEditPolicy() {
				@Override
				protected FBNetwork getParentNetwork() {
					return getSubappNetwork();
				}
			};
		}
		if (isVariable()) {
			return new VariableNodeEditPolicy() {
				@Override
				protected FBNetwork getParentNetwork() {
					return getSubappNetwork();
				}
			};
		}
		return null;
	}

	@Override
	protected Adapter createContentAdapter() {
		return new UntypedSubappIEAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();

				if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getAttribute_Value().equals(feature)) {
					updatePadding(getYPositionFromAttribute(getModel()));
				}
				super.notifyChanged(notification);
			}
		};
	}

	protected void updatePadding(final int yPosition) {
		final IFigure paddingFigure = (IFigure) getFigure().getParent().getChildren().get(0);
		final int textHeight = ((InterfaceFigure) getFigure()).getTextBounds().height();
		paddingFigure.setMinimumSize(new Dimension(-1, yPosition - textHeight));
	}

	private static int getYPositionFromAttribute(final IInterfaceElement ie) {
		final Attribute attribute = ie.getAttribute("YPOSITION"); //$NON-NLS-1$
		if (attribute != null) {
			return Integer.parseInt(attribute.getValue());
		}
		return 0;
	}

	private void goToParent() {
		final IEditorPart newEditor = HandlerHelper.openParentEditor(getModel().getFBNetworkElement());
		final GraphicalViewer viewer = newEditor.getAdapter(GraphicalViewer.class);
		HandlerHelper.selectElement(getModel(), viewer);
	}

	private FBNetwork getSubappNetwork() {
		return ((SubApp) getModel().getFBNetworkElement()).getSubAppNetwork();
	}

}
