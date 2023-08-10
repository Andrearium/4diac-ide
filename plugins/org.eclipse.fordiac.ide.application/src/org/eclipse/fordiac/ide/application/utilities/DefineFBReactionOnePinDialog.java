/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DefineFBReactionOnePinDialog extends MessageDialog {
	private static final int NUM_COLUMNS = 4;
	private Text inputTime;
	private Text offset;
	private final Event pinFrom;
	private final Event pinTo;
	private String inputTimeText;
	private String offsetText;
	private Button offsetCheckbox;
	private boolean defineOffset;
	private CCombo inputOffsetCombo;
	private String state;

	public DefineFBReactionOnePinDialog(final Shell parentShell, final Event pinFrom) {
		super(parentShell, Messages.DefineFBReactionOnePinDialog_Title, null,
				Messages.DefineFBReactionOnePinDialog_Info, MessageDialog.INFORMATION, 0,
				Messages.DefineFBReactionOnePinDialog_Button);
		this.pinFrom = pinFrom;
		this.pinTo = null;
	}

	public String getTime() {
		return inputTimeText;
	}

	public boolean hasOffset() {
		return defineOffset;
	}

	public String getOffsetText() {
		return offsetText;
	}

	public String getState() {
		return state;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.FILL);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		final GridLayout layout = new GridLayout(2, false);
		dialogArea.setLayout(layout);

		final Group group = new Group(dialogArea, SWT.FILL);

		group.setText(Messages.DefineFBReactionOnePinDialog_DefineAssumption);
		group.setLayout(new GridLayout(NUM_COLUMNS, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.None);
		if (pinTo != null) {
			label.setText("Reaction (" + pinFrom.getName() + " , " + pinTo.getName() + ")");   //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

			label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());
		} else {
			label.setText("Event " + pinFrom.getName()); //$NON-NLS-1$
			label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());
		}

		label = new Label(group, SWT.None);
		label.setText("occurs "); //$NON-NLS-1$
		inputOffsetCombo = ComboBoxWidgetFactory.createCombo(group);
		inputOffsetCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		final String[] options = { "within", "every" }; //$NON-NLS-1$ //$NON-NLS-2$
		inputOffsetCombo.setItems(options);
		inputOffsetCombo.select(0);

		inputTime = new Text(group, SWT.RIGHT);
		inputTime.addListener(SWT.KeyDown, new IntervalVerifyListener(inputTime));
		inputTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		label = new Label(group, SWT.None);
		label.setText(" ms"); //$NON-NLS-1$

		final Label labelOffset = new Label(group, SWT.None);
		labelOffset.setText(Messages.DefineFBReactionOnePinDialog_SpecifyOffset);
		labelOffset.setVisible(false);

		final Label labelOff = new Label(group, SWT.None);
		labelOff.setText(""); //$NON-NLS-1$
		labelOff.setVisible(false);

		offset = new Text(group, SWT.RIGHT);
		offset.addListener(SWT.KeyDown, new IntervalVerifyListener(offset));

		offset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		offset.setVisible(false);
		final Label labelOffsetMs = new Label(group, SWT.None);
		labelOffsetMs.setText(" ms"); //$NON-NLS-1$
		labelOffsetMs.setVisible(false);

		offsetCheckbox = new Button(dialogArea, SWT.CHECK);
		offsetCheckbox.setText("Offset"); //$NON-NLS-1$
		offsetCheckbox.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		offsetCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				offset.setEnabled(offsetCheckbox.getSelection());
				labelOffset.setVisible(offsetCheckbox.getSelection());
				offset.setVisible(offsetCheckbox.getSelection());
				labelOffsetMs.setVisible(offsetCheckbox.getSelection());
				labelOff.setVisible(offsetCheckbox.getSelection());

			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// is never called
			}
		});

		return dialogArea;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		defineOffset = offsetCheckbox.getSelection();
		if (offsetCheckbox.isEnabled()) {
			offsetText = offset.getText();
		}
		inputTimeText = inputTime.getText();
		state = inputOffsetCombo.getItem(inputOffsetCombo.getSelectionIndex());
		final String[] strInputTime = DefineContractUtils.getTimeIntervalFromString(inputTimeText);
		String[] strOffset = { "0", "0" };  //$NON-NLS-1$//$NON-NLS-2$
		if (offset.isVisible()) {
			strOffset = DefineContractUtils.getTimeIntervalFromString(offsetText);
		}
		if (inputTimeText.isBlank() || (offset.isVisible() && offset.getText().isBlank())
				|| (strInputTime.length == 2 && (Integer.parseInt(strInputTime[0]) > Integer.parseInt(strInputTime[1])))
				|| (offset.isVisible() && strOffset.length == 2
						&& (Integer.parseInt(strOffset[0]) > Integer.parseInt(strOffset[1])))) {
			MessageDialog.openError(this.getShell(), Messages.DefineFBReactionOnePinDialog_Error,
					Messages.DefineFBReactionOnePinDialog_PleaseFill);
		} else {
			super.buttonPressed(buttonId);
		}
	}

}
