/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Jaeger, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - comment field
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.properties;

import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructViewingComposite;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.TypeInfoWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class DataTypeInfoSection extends AbstractSection {

	private TypeInfoWidget typeInfoWidget;
	private Text commentText;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);

		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		createCommentField(parent);
		createTypeInfoGroup(parent);
	}

	private void createCommentField(final Composite parent) {
		final Composite container = getWidgetFactory().createComposite(parent);
		container.setLayout(new GridLayout(2, false));
		final GridData data = new GridData(SWT.FILL, 0, true, false);
		data.horizontalSpan = 2;
		container.setLayoutData(data);

		getWidgetFactory().createLabel(container, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(container, true);
		commentText.addModifyListener(e -> {
			/*
			 * Without this if statement the editor would be "dirty" from the get-go:
			 *  - editor listens for changes on the type
			 *  - first load also triggers refresh
			 *  - refresh sets comment text (always, even if the comment is empty)
			 *  - the ChangeCommentCommand makes a change to the type
			 *  Therefore, restricting command execution does the trick.
			 */
			if (!commentText.getText().equals(getType().getComment())) {
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			}
		});

	}

	private void createTypeInfoGroup(final Composite parent) {
		typeInfoWidget = new TypeInfoWidget(getWidgetFactory());
		typeInfoWidget.createControls(parent);
	}

	@Override
	protected StructuredType getType() {
		return (StructuredType) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof StructViewingComposite) {
			return ((StructViewingComposite) input).getStruct();
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}

	@Override
	public void refresh() {
		if (null != getType()) {
			commentText.setText((null != getType().getComment()) ? getType().getComment() : "");
			typeInfoWidget.refresh();
		}
	}

	@Override
	protected void setInputInit() {
		typeInfoWidget.initialize(getType(), this::executeCommand);
	}
}
