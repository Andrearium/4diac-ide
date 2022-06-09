/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, AIT, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed untyped subapp interface updates and according code cleanup
 *   Bianca Wiesmayr - fixed untyped subapp interface reorder/delete
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - added update support for removing or readding subapp type
 *   Bianca Wiesmayr, Alois Zoitl - unfolded subapp
 *   Daniel Lindhuber - instance comment
 *   				  - root refresh for monitoring elements
 *   Alois Zoitl - improved refresh on expand/collapse, added direct edit for
 *                 supapp comments
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.application.figures.SubAppForFbNetworkFigure;
import org.eclipse.fordiac.ide.application.policies.ContainerResizePolicy;
import org.eclipse.fordiac.ide.application.policies.FBAddToSubAppLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SubAppForFBNetworkEditPart extends AbstractFBNElementEditPart implements IContainerEditPart {
	private UnfoldedSubappContentNetwork subappContents;

	@Override
	public Adapter createContentAdapter() {
		return new SubappContentAdapter();
	}

	private final class SubappContentAdapter extends AdapterImpl {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.MOVE:
				handleAddMove(notification);
				break;
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				handleRemove(notification);
				break;
			case Notification.SET:
				handleSet(notification);
				break;
			default:
				break;
			}
			refreshToolTip();
			backgroundColorChanged(getFigure());
		}

		private void handleAddMove(final Notification notification) {
			if (notification.getNewValue() instanceof IInterfaceElement) {
				refreshChildren();
			}
			if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes()
					.equals(notification.getFeature())) {
				refreshVisuals();
				refreshChildren();
				refreshInterfaceEditParts();
				refreshRoot();
			}
		}

		private void handleRemove(final Notification notification) {
			if (notification.getOldValue() instanceof IInterfaceElement) {
				refreshChildren();
			}
			if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes()
					.equals(notification.getFeature())) {
				refreshVisuals();
				refreshChildren();
				refreshInterfaceEditParts();
				refreshRoot();
			}
		}

		private void handleSet(final Notification notification) {
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(notification.getFeature())) {
				getFigure().refreshComment();
			}
			if (LibraryElementPackage.eINSTANCE.getSubApp_Width().equals(notification.getFeature())
					|| LibraryElementPackage.eINSTANCE.getSubApp_Height().equals(notification.getFeature())) {
				refreshPosition();
			}
			refreshVisuals();
		}

		private void refreshRoot() {
			final EditPart root = getRoot();
			if (root != null) {
				root.getChildren().forEach(child -> ((EditPart) child).refresh());
			}
		}

		private void refreshInterfaceEditParts() {
			getChildren().forEach(ep -> {
				if (ep instanceof InterfaceEditPart) {
					((InterfaceEditPart) ep).refresh();
				}
			});
		}
	}

	private class SubappCommentRenameEditPolicy extends AbstractViewRenameEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (getHost().getModel() instanceof INamedElement) {
				final String str = (String) request.getCellEditor().getValue();
				if (!InstanceCommentFigure.EMPTY_COMMENT.equals(str)) {
					return new ChangeCommentCommand((INamedElement) getHost().getModel(), str);
				}
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			getCommentFigure().setText(value);
		}

		@Override
		protected void revertOldEditValue(final DirectEditRequest request) {
			getFigure().refreshComment();
		}

	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> children = super.getModelChildren();
		if (getModel().isUnfolded()) {
			children.add(getSubappContents());
		}
		return children;
	}

	private UnfoldedSubappContentNetwork getSubappContents() {
		if (null == subappContents) {
			subappContents = new UnfoldedSubappContentNetwork(getModel());
		}
		return subappContents;
	}

	public SubAppForFBNetworkEditPart() {
		super();
	}

	@Override
	protected IFigure createFigureForModel() {
		return new SubAppForFbNetworkFigure(getModel(), this);
	}

	@Override
	public SubAppForFbNetworkFigure getFigure() {
		return (SubAppForFbNetworkFigure) super.getFigure();
	}

	@Override
	public SubApp getModel() {
		return (SubApp) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		if (getModel().isUnfolded()) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new SubappCommentRenameEditPolicy());
		} else {
			installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
		}
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType().equals(RequestConstants.REQ_OPEN)) {
			if (getModel().isUnfolded()) {
				performDirectEdit();
			} else {
				openSubAppEditor();
			}
		} else if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public void performDirectEdit() {
		if (getModel().isUnfolded()) {
			// if unfolded edit comment
			new TextDirectEditManager(this, new FigureCellEditorLocator(getCommentFigure())) {
				@Override
				protected CellEditor createCellEditorOn(final Composite composite) {
					return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
				}

				@Override
				protected void initCellEditor() {
					super.initCellEditor();
					getCellEditor().setValue(getModel().getComment());
				}
			}.show();
		} else {
			super.performDirectEdit();
		}
	}

	protected InstanceCommentFigure getCommentFigure() {
		return getFigure().getCommentFigure();
	}

	private void openSubAppEditor() {
		SubApp subApp = getModel();
		if (subAppIsMapped(subApp)) {
			subApp = (SubApp) subApp.getOpposite();
		}
		OpenListenerManager.openEditor(subApp);
	}

	private boolean subAppIsMapped(final SubApp subApp) {
		return null == getModel().getTypeEntry() && (null == subApp.getSubAppNetwork()) && subApp.isMapped();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateEditPolicies();
		final SubAppForFbNetworkFigure figure = getFigure();
		figure.updateTypeLabel(getModel());
		figure.updateExpandedFigure();
	}

	private void updateEditPolicies() {
		if (getModel().isUnfolded()) {
			if (getFigure().getExpandedMainFigure() == null) {
				installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new SubappCommentRenameEditPolicy());
				installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ContainerResizePolicy());
				installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
			}
		} else {
			if (getFigure().getExpandedMainFigure() != null) {
				installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TypeDirectEditPolicy());
				installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ModifiedNonResizeableEditPolicy());
				installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
			}
		}
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof UnfoldedSubappContentEditPart) {
			final IFigure contentFigure = ((UnfoldedSubappContentEditPart) childEditPart).getFigure();
			final GridData contentGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
			getFigure().getExpandedMainFigure().add(contentFigure, contentGridData, 2);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof UnfoldedSubappContentEditPart) {
			if (getFigure().getExpandedMainFigure() != null) {
				getFigure().getExpandedMainFigure().remove(((UnfoldedSubappContentEditPart) childEditPart).getFigure());
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	public Rectangle getCommentBounds() {
		if (getFigure().getCommentFigure() != null) {
			return getFigure().getCommentFigure().getBounds();
		}
		return null;
	}

	@Override
	public GraphicalEditPart getContentEP() {
		return (GraphicalEditPart) getChildren().stream().filter(UnfoldedSubappContentEditPart.class::isInstance)
				.findAny().orElse(null);
	}

	@Override
	protected void refreshPosition() {
		if (getParent() != null) {
			final Position position = getModel().getPosition();
			final Point asPoint = position.asPoint();
			final Rectangle bounds = new Rectangle(asPoint, getSubappSize());
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private Dimension getSubappSize() {
		if (getModel().isUnfolded()) {
			return new Dimension(getModel().getWidth(), getModel().getHeight());
		}
		return new Dimension(-1, -1);
	}

	@Override
	public Object getAdapter(final Class key) {
		if (key == SubApp.class) {
			return getModel();
		}
		return super.getAdapter(key);
	}
}
