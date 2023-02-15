/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr  - fix column traversal, add context menu
 *   Michael Jaeger   - replaced HashSet with ArrayList
 *   Lukas Wais		  - implemented tree menu for structured types
 *   Alois Zoitl	  - fixed fokus checking for linux.
 *   Dunja Životin    - extracted DataTypeTreeSelectionDialog and TypeNode into separate classes
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalListener2;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

public class DataTypeDropdown extends TextCellEditor {

	private ContentProposalAdapter adapter;
	private Text textControl;
	private final ITypeSelectionContentProvider contentProvider;
	private SimpleContentProposalProvider provider;
	private List<DataType> types;
	private String[] elementaryTypes;
	private final TableViewer viewer;
	/* A flag that indicates if the content proposals have been set to elementary types (this is the case if the text
	 * field is empty) or all types. This means that the ModifyListener of the textControl does not have to set the
	 * types for the proposal provider with every modify event but only when its necessary. */
	private boolean isElementary;

	private boolean isTraverseNextProcessActive;
	private boolean isTraversePreviousProcessActive;

	public DataTypeDropdown(final ITypeSelectionContentProvider contentProvider, final TableViewer viewer) {
		super(viewer.getTable());
		this.contentProvider = contentProvider;
		this.viewer = viewer;
		types = new ArrayList<>(); // empty list for initial provider creation
		configureTextControl();
		createDialogButton();
		enableContentProposal();
		loadContent();
	}

	public DataType getType(final String value) {
		return types.stream().filter(type -> value.equals(type.getName())).findAny().orElse(null);
	}

	@Override
	protected void doSetValue(final Object value) {
		if (null == value) {
			textControl.setText(""); //$NON-NLS-1$
		} else {
			super.doSetValue(value);
		}
	}

	/* is called with every opening of the content proposal popup, may lead to performance issues */
	private void loadContent() {
		types = getDataTypesSorted(); // get sorted types for convenient order in dialog
		provider.setProposals(getTypesAsStringArray());
	}

	// can be overridden to filter the list differently
	protected List<DataType> getDataTypesSorted() {
		if (null != contentProvider) {
			return contentProvider.getTypes();
		}
		return Collections.emptyList();
	}

	@Override
	protected Control createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridLayout contLayout = new GridLayout(2, false);
		contLayout.horizontalSpacing = 0;
		contLayout.marginTop = 0;
		contLayout.marginBottom = 0;
		contLayout.marginWidth = 0;
		contLayout.marginHeight = 0;
		contLayout.verticalSpacing = 0;
		contLayout.horizontalSpacing = 0;
		container.setLayout(contLayout);
		textControl = (Text) super.createControl(container);
		return container;
	}

	private void configureTextControl() {
		textControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textControl.addModifyListener(e -> {
			loadContent(); // refresh content before opening textfield
			if (textControl.getText().isEmpty()) {
				provider.setProposals(getElementaryTypes());
				isElementary = true;
			} else if (isElementary) {
				provider.setProposals(getTypesAsStringArray());
				isElementary = false;
			}
		});
		textControl.addListener(SWT.Traverse, e -> {
			e.doit = false; // prevent the action from doing anything automatically
			if ((e.detail == SWT.TRAVERSE_TAB_NEXT) && ((e.stateMask & SWT.CTRL) != 0)) {
				// move within column down
				final int selIndex = viewer.getTable().getSelectionIndex() + 1;
				if (selIndex < viewer.getTable().getItemCount()) {
					final Object data = viewer.getTable().getItem(selIndex).getData();
					viewer.editElement(data, 1); // type column
				}
			} else if ((e.detail == SWT.TRAVERSE_TAB_PREVIOUS) && ((e.stateMask & SWT.CTRL) != 0)) {
				// move within column up
				final int selIndex = viewer.getTable().getSelectionIndex() - 1;
				if (selIndex >= 0) {
					final Object data = viewer.getTable().getItem(selIndex).getData();
					viewer.editElement(data, 1); // type column
				}
			} else if (e.detail == SWT.TRAVERSE_TAB_NEXT) {
				// move one column to the right
				if (adapter.isProposalPopupOpen()) {
					triggerEnterKeyEvent();
					isTraverseNextProcessActive = true;
				} else {
					traverseToNextCell();
				}
			} else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
				// move one column to the left
				if (adapter.isProposalPopupOpen()) {
					triggerEnterKeyEvent();
					isTraversePreviousProcessActive = true;
				} else {
					traverseToPreviousCell();
				}
			}
		});
	}

	private static void triggerEnterKeyEvent() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		final Event event = new Event();
		event.keyCode = SWT.CR;
		event.display = display;
		event.type = SWT.KeyDown;
		display.post(event);
	}

	private void traverseToPreviousCell() {
		final int selIndex = viewer.getTable().getSelectionIndex();
		final Object data = viewer.getTable().getItem(selIndex).getData();
		adapter.setEnabled(false);
		viewer.editElement(data, 0); // name column
		adapter.setEnabled(true);
	}

	private void traverseToNextCell() {
		final int selIndex = viewer.getTable().getSelectionIndex();
		final Object data = viewer.getTable().getItem(selIndex).getData();
		adapter.setEnabled(false);
		viewer.editElement(data, 2); // comment column
		adapter.setEnabled(true);
	}

	private String[] getElementaryTypes() {
		// only load elementary types once because they do not change
		if (null == elementaryTypes) {
			elementaryTypes = types.stream().filter(type -> !(type instanceof StructuredType)).map(DataType::getName)
					.toArray(String[]::new);
		}
		return elementaryTypes;
	}

	private String[] getTypesAsStringArray() {
		return types.stream().map(DataType::getName).toArray(String[]::new);
	}

	private void createDialogButton() {
		final Button menuButton = new Button((Composite) getControl(), SWT.FLAT);
		menuButton.setText("..."); //$NON-NLS-1$
		menuButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				openDialog();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// no need to listen to the default selection
			}
		});
	}

	private void openDialog() {
		loadContent(); // refresh content before opening

		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getControl().getShell());
		dialog.setInput(types);
		dialog.setTitle(Messages.DataTypeDropdown_Type_Selection);
		dialog.setMessage(Messages.DataTypeDropdown_Select_Type);
		dialog.setDoubleClickSelects(false); // because it is incompatible with multi-level tree
		dialog.setHelpAvailable(false);

		// user pressed cancel
		if (dialog.open() != Window.OK) {
			deactivate();
			return;
		}
		final Object result = dialog.getFirstResult();
		// check for DataType so that no VarDeclaration or directories can be selected
		if (result instanceof TypeNode) {
			final TypeNode node = (TypeNode) result;
			if (!node.isDirectory()) { // nodes without types are directories
				doSetValue(node.getName());
				fireApplyEditorValue();
			}
		}
		deactivate();
	}

	static final char[] ACTIVATION_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '_', '.', SWT.BS };

	private void enableContentProposal() {
		provider = new SimpleContentProposalProvider(getTypesAsStringArray());
		provider.setFiltering(true);

		adapter = new ContentProposalAdapter(text, new TextContentAdapter(), provider, null, ACTIVATION_CHARS);
		adapter.addContentProposalListener(new IContentProposalListener2() {

			@Override
			public void proposalPopupClosed(final ContentProposalAdapter adapter) {
				// no need to listen to closing
			}

			@Override
			public void proposalPopupOpened(final ContentProposalAdapter adapter) {
				loadContent();
			}

		});

		adapter.addContentProposalListener(proposal -> {
			fireApplyEditorValue();
			// if apply value was triggered programmatically -> tab to next/previous cell
			if (isTraverseNextProcessActive) {
				traverseToNextCell();
				isTraverseNextProcessActive = false;
			} else if (isTraversePreviousProcessActive) {
				traverseToPreviousCell();
				isTraversePreviousProcessActive = false;
			}
		});

		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}

	@Override
	protected void focusLost() {
		if (!insideAnyEditorArea()) {
			deactivate();
		}
	}
	private boolean insideAnyEditorArea() {
		final Point cursorLocation = getControl().getDisplay().getCursorLocation();
		final Point containerRelativeCursor = getControl().getParent().toControl(cursorLocation);
		return getControl().getBounds().contains(containerRelativeCursor);
	}


	@Override
	protected boolean dependsOnExternalFocusListener() {
		/* if true, a separate focus listener is created and the whole cell editor looses focus when the proposal popup
		 * is opened */
		return false;
	}
}
