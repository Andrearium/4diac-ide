/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 - 2020, 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added preference driven max width for value edit parts
 *   Lisa Sonnleithner - deleted setting for corner dimensions, replaced with constant
 *   				   - moved max label size into a group
 *   				   - moved creating a group into a method
 *   Prankur Agarwal - added a field to input maximum hidden connection label size
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

/** The Class DiagramPreferences. */
public class DiagramPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/** The Constant CORNER_DIM. */
	public static final int CORNER_DIM = 6;
	public static final int CORNER_DIM_HALF = CORNER_DIM / 2;

	public static final String GRID_SPACING = "GridSpacing"; //$NON-NLS-1$

	public static final String SNAP_TO_GRID = "SnapToGrid"; //$NON-NLS-1$

	public static final String SHOW_GRID = "ShowGrid"; //$NON-NLS-1$

	public static final String SHOW_RULERS = "ShowRulers"; //$NON-NLS-1$

	public static final String PIN_LABEL_STYLE = "PinLabelStyle"; //$NON-NLS-1$
	public static final String PIN_LABEL_STYLE_PIN_NAME = "PinLabelStyle_PinName"; //$NON-NLS-1$
	public static final String PIN_LABEL_STYLE_PIN_COMMENT = "PinLabelStyle_PinComment"; //$NON-NLS-1$
	public static final String PIN_LABEL_STYLE_SRC_PIN_NAME = "PinLabelStyle_SourcePinName"; //$NON-NLS-1$

	public static final String MAX_VALUE_LABEL_SIZE = "MaxValueLabelSize"; //$NON-NLS-1$

	public static final String MIN_PIN_LABEL_SIZE = "MinPinLabelSize"; //$NON-NLS-1$
	public static final String MAX_PIN_LABEL_SIZE = "MaxPinLabelSize"; //$NON-NLS-1$

	public static final String MAX_HIDDEN_CONNECTION_LABEL_SIZE = "MaxHiddenConnectionLabelSize"; //$NON-NLS-1$

	public static final String MAX_TYPE_LABEL_SIZE = "MaxTypeLabelSize"; //$NON-NLS-1$

	private boolean changesOnLabelSize = false;

	/** Instantiates a new diagram preferences. */
	public DiagramPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/* (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors() */
	@Override
	public void createFieldEditors() {

		// Create a Group to hold the ruler fields
		createGroupRulerGrid();

		// Create a Group to hold label size field
		createGroupLabelSize();

		// Create a Group to hold the interface pin field
		createGroupInterfacePins();

		// Create a Group to hold the layout options field
		createGroupLayoutOptionsPins();

		// Create a Group to hold the virtual group interface fields
		createGroupVirtualGroupInterfaceOptionsPins();
	}

	private Group createGroup(final String title) {
		final Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText(title);
		return group;
	}

	private static void configGroup(final Group group) {
		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;

		gridData.horizontalSpan = 2;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		if (event.getSource() instanceof FieldEditor && matchPreferenceName(event)) {
			changesOnLabelSize = true;
		}
	}

	private static boolean matchPreferenceName(final PropertyChangeEvent event) {
		final String sourcePrefName = ((FieldEditor) event.getSource()).getPreferenceName();
		return sourcePrefName.equalsIgnoreCase(MIN_PIN_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(MAX_PIN_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(MAX_TYPE_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(MAX_VALUE_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(MAX_HIDDEN_CONNECTION_LABEL_SIZE)
				|| sourcePrefName.equalsIgnoreCase(PIN_LABEL_STYLE);
	}

	@Override
	public boolean performOk() {
		super.performOk();
		if (changesOnLabelSize) {
			changesOnLabelSize = false;
			showMessageBox();
		}
		return true;
	}

	private static void showMessageBox() {

		final MessageBox msgBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.YES | SWT.NO);
		Display.getDefault().getActiveShell();
		msgBox.setText("4diac IDE"); //$NON-NLS-1$
		msgBox.setMessage(Messages.DiagramPreferences_Restart);

		switch (msgBox.open()) {
		case SWT.NO:
			break;
		case SWT.YES:
			PlatformUI.getWorkbench().restart();
			break;
		default:
			break;
		}
	}

	private void createGroupLabelSize() {

		final Group labelSize = createGroup(Messages.DiagramPreferences_LabelSize);
		final IntegerFieldEditor integerFieldEditorLabel = new IntegerFieldEditor(MAX_VALUE_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumValueLabelSize, labelSize);
		integerFieldEditorLabel.setValidRange(0, 120);
		addField(integerFieldEditorLabel);

		final IntegerFieldEditor integerFieldEditorTypeLabel = new IntegerFieldEditor(MAX_TYPE_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumTypeLabelSize, labelSize);
		integerFieldEditorTypeLabel.setValidRange(0, 120);
		addField(integerFieldEditorTypeLabel);

		final IntegerFieldEditor integerFieldEditorMinPin = new IntegerFieldEditor(MIN_PIN_LABEL_SIZE,
				Messages.DiagramPreferences_MinimumPinLabelSize, labelSize);
		integerFieldEditorMinPin.setValidRange(0, 60);
		addField(integerFieldEditorMinPin);

		final IntegerFieldEditor integerFieldEditorMaxPin = new IntegerFieldEditor(MAX_PIN_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumPinLabelSize, labelSize);
		integerFieldEditorMaxPin.setValidRange(0, 60);
		addField(integerFieldEditorMaxPin);

		final IntegerFieldEditor integerFieldEditorConnection = new IntegerFieldEditor(MAX_HIDDEN_CONNECTION_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumHiddenConnectionLabelSize, labelSize);
		integerFieldEditorConnection.setValidRange(0, 100);
		addField(integerFieldEditorConnection);

		configGroup(labelSize);
	}

	private void createGroupRulerGrid() {
		final Group group = createGroup(Messages.DiagramPreferences_FieldEditors_RulerAndGrid);
		// Add the fields to the group
		final BooleanFieldEditor showRulers = new BooleanFieldEditor(SHOW_RULERS,
				Messages.DiagramPreferences_FieldEditors_ShowRuler, group);
		addField(showRulers);

		final BooleanFieldEditor showGrid = new BooleanFieldEditor(SHOW_GRID,
				Messages.DiagramPreferences_FieldEditors_ShowGrid, group);
		addField(showGrid);

		final BooleanFieldEditor snapToGrid = new BooleanFieldEditor(SNAP_TO_GRID,
				Messages.DiagramPreferences_FieldEditors_SnapToGrid, group);
		addField(snapToGrid);

		final IntegerFieldEditor gridSpacing = new IntegerFieldEditor(GRID_SPACING,
				Messages.DiagramPreferences_FieldEditors_GridSpacingInPixels, group);
		gridSpacing.setTextLimit(10);
		addField(gridSpacing);
		configGroup(group);
	}

	private void createGroupLayoutOptionsPins() {
		final Group group = createGroup("Layout Options");
		final BooleanFieldEditor connectionAutoLayout = new BooleanFieldEditor("ConnectionAutoLayout",
				"Layout connections automatically", group);
		addField(connectionAutoLayout);
		configGroup(group);
	}

	private void createGroupVirtualGroupInterfaceOptionsPins() {
		final Group group = createGroup("Virtual Group Interface Options");
		final BooleanFieldEditor vgi = new BooleanFieldEditor("VirtualGroupInterfaces",
				"Enable Virtual Group Interfaces", group);
		addField(vgi);
		configGroup(group);
	}

	private void createGroupInterfacePins() {
		addField(new RadioGroupFieldEditor(PIN_LABEL_STYLE, Messages.DiagramPreferences_PinLabelText, 1,
				new String[][] { { Messages.DiagramPreferences_ShowPinName, PIN_LABEL_STYLE_PIN_NAME },
						{ Messages.DiagramPreferences_ShowPinComment, PIN_LABEL_STYLE_PIN_COMMENT },
						{ Messages.DiagramPreferences_ShowConnectedOutputPinName, PIN_LABEL_STYLE_SRC_PIN_NAME } },
				getFieldEditorParent(), true));
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do here
	}

}