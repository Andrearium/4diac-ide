/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.ConnectionDisplayWidget;
import org.eclipse.fordiac.ide.gef.widgets.InternalConnectionsViewer;
import org.eclipse.fordiac.ide.gef.widgets.PinInfoBasicWidget;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PinEventInfoSection extends AbstractSection {

	private PinInfoBasicWidget pinInfo;
	private ConnectionDisplayWidget inConnections;
	private InternalConnectionsViewer outConnections;

	private static final int NUM_OF_CONN_DISPLAYS = 2;
	private static final int PARTS = 2;

	protected TabbedPropertySheetWidgetFactory widgetFactory;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(PARTS, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		widgetFactory = tabbedPropertySheetPage.getWidgetFactory();

		// Enforcing the layout so the connections would be side by side
		getRightComposite().setLayout(new GridLayout(NUM_OF_CONN_DISPLAYS, true));

		final Composite middleComposite = createSmallComposite(getRightComposite());
		final Composite rightComposite = createSmallComposite(getRightComposite());

		pinInfo = pinInfoCreation(getLeftComposite());
		inConnections = new ConnectionDisplayWidget(widgetFactory, middleComposite, this);
		outConnections = new InternalConnectionsViewer(widgetFactory, rightComposite, this);

	}

	protected PinInfoBasicWidget pinInfoCreation(final Composite parent) {
		return new PinInfoBasicWidget(parent, widgetFactory);
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement)type;
	}

	@Override
	protected EObject getInputType(final Object input) {
		Object refType = input;
		if (input instanceof EditPart) {
			refType = ((EditPart) input).getModel();
		}
		return (refType instanceof IInterfaceElement) ? (IInterfaceElement) refType : null;
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != pinInfo && null != inConnections && null != outConnections && null != getType()) {
			pinInfo.refresh();
			inConnections.refreshConnectionsViewer(getType());
			outConnections.refreshConnectionsViewer(getType());
			final FBNetworkElement fb = getType().getFBNetworkElement();
			if (fb != null) {
				inConnections.setEditable(true);
				outConnections.setEditable(true);
			}
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputCode() {
		pinInfo.disableAllFields();
		inConnections.setEditable(false);
		outConnections.setEditable(false);
	}

	@Override
	protected void setInputInit() {
		if (pinInfo != null) {
			pinInfo.initialize(getType(), this::executeCommand);
			pinInfo.getTypeSelectionWidget().initialize(getType(), getTypeSelectionContentProvider());
		}
	}

	private Composite createSmallComposite(final Composite parent) {
		final Composite composite = widgetFactory.createComposite(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		return composite;
	}

	@SuppressWarnings("static-method")  // allow subclasses to override
	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return () -> new ArrayList<>(EventTypeLibrary.getInstance().getEventTypes());
	}

}
