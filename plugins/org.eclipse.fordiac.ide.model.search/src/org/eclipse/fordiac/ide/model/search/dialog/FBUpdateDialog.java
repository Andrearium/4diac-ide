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
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.search.Messages;
import org.eclipse.fordiac.ide.model.search.ModelSearchResultPage;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.search.types.StructDataTypeSearch;
import org.eclipse.fordiac.ide.model.search.types.StructManipulatorSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class FBUpdateDialog extends MessageDialog {

	protected final DataTypeEntry dataTypeEntry;

	private static final int NUMBER_OF_COLLUMNS = 1;
	private static final int TABLE_COL_WIDTH = 150;
	private static final int CHECK_BOX_COL_WIDTH = 30;
	private boolean selectAll = true;

	private final Set<Object> collectedElements;
	// private TableViewer viewer;
	private TreeViewer treeViewer;
	private final Map<String, Set<INamedElement>> children = new HashMap<>();
	private ColumnLabelProvider labelElement;
	private ColumnLabelProvider labelPath;
	private ColumnLabelProvider labelType;

	public FBUpdateDialog(final Shell parentShell, final String dialogTitle, final Image dialogTitleImage,
			final String dialogMessage, final int dialogImageType, final String[] dialogButtonLabels,
			final int defaultIndex, final DataTypeEntry dataTypeEntry) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex);
		this.dataTypeEntry = dataTypeEntry;
		collectedElements = new HashSet<>();
	}

	public Set<Object> getCollectedFBs() {
		return collectedElements;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(NUMBER_OF_COLLUMNS, true));

		final Composite searchResArea = WidgetFactory.composite(NONE).create(parent);
		searchResArea.setLayout(new GridLayout(1, true));
		searchResArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final Set<INamedElement> result = createInputSet();
		if (result.isEmpty()) {
			// No results - display just the info
			final Label warningLabel = LabelFactory.newLabel(NONE).create(searchResArea);
			warningLabel.setText("No additional function blocks or types have been affected by this change!"); //$NON-NLS-1$
		} else {
			createfilterButtons(parent);

			treeViewer = createTreeViewer(searchResArea);
			configureTableViewer(treeViewer);
			treeViewer.setInput(result.toArray());
			GridLayoutFactory.fillDefaults().generateLayout(searchResArea);
			// viewer = createTableViewer(searchResArea);
			//
			// viewer.setInput(result.toArray());
		}
		return parent;
	}

	private Set<INamedElement> createInputSet() {
		final Set<INamedElement> inputElements = new HashSet<>();
		// find SubAppTypes
		final InstanceSearch search = StructDataTypeSearch
				.createStructInterfaceSearch((StructuredType) dataTypeEntry.getTypeEditable());
		// add types to input
		inputElements.addAll(search.performTypeLibBlockSearch(dataTypeEntry.getTypeLibrary()).stream()
				.filter(SubAppType.class::isInstance).toList());
		// initiate map with types
		inputElements.stream().forEach(st -> children.put(st.getName(), new HashSet<>()));
		// add typed subapp instances as children
		performSearch(new FBInstanceSearch(dataTypeEntry)).stream().forEach(s -> {
			if (s instanceof final SubApp subApp && !subApp.isTyped()) {
				inputElements.add(s);
			}
			children.get(((FBNetworkElement) s).getTypeName()).add(s);
		});
		// add structmanipulators and untyped subapps to input
		inputElements.addAll(performSearch(new StructManipulatorSearch(dataTypeEntry)));
		return inputElements;
	}

	protected void createfilterButtons(final Composite parent) {
		// Override this method to add searchFilter buttons
	}

	private void createLabelProviders() {
		this.labelElement = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final INamedElement namedElem) {
					return namedElem.getName();
				}
				if (element instanceof final FBNetworkElement networkElem) {
					return networkElem.getName();
				}
				return super.getText(element);
			}
		};
		this.labelPath = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ModelSearchResultPage.hierarchicalName(element);
			}
		};
		this.labelType = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final SubApp subapp && subapp.isTyped()) {
					return "Untyped SubApp"; //$NON-NLS-1$
				}
				if (element instanceof final IInterfaceElement interfaceElem) {
					return interfaceElem.getTypeName();
				}
				if (element instanceof final StructManipulator manipulator) {
					return manipulator.getTypeName();
				}
				if (element instanceof final FBNetworkElement e) {
					return e.getTypeName();
				}
				if (element instanceof final SubAppType) {
					return "SubAppType";
				}
				return super.getText(element);
			}
		};
	}

	public void refresh() {

		final Set<INamedElement> input = performSearch(new FBInstanceSearch(dataTypeEntry),
				new StructManipulatorSearch(dataTypeEntry));
		final InstanceSearch search = StructDataTypeSearch
				.createStructInterfaceSearch((StructuredType) dataTypeEntry.getTypeEditable());
		input.addAll(search.performTypeLibBlockSearch(dataTypeEntry.getTypeLibrary()).stream()
				.filter(SubAppType.class::isInstance).toList());
		treeViewer.setInput(input);

	}

	protected Set<INamedElement> performStructSearch() {
		final InstanceSearch structSearch = new StructManipulatorSearch(dataTypeEntry);
		return structSearch.performCompleteSearch();
	}

	protected Set<INamedElement> performSubAppSearch() {
		final InstanceSearch subAppSearch = new FBInstanceSearch(dataTypeEntry);
		return subAppSearch.performCompleteSearch();
	}

	protected static Set<INamedElement> performSearch(final InstanceSearch... searchers) {
		final Set<INamedElement> results = new HashSet<>();
		for (final InstanceSearch search : searchers) {
			results.addAll(search.performCompleteSearch());
		}
		return results;
	}

	private static TreeViewer createTreeViewer(final Composite parent) {
		return new TreeViewer(parent, SWT.CHECK);
	}

	private static TableViewer createTableViewer(final Composite parent) {
		return TableWidgetFactory.createTableViewer(parent,
				SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	private void configureTableViewer(final TreeViewer viewer) {
		collectedElements.clear();
		createLabelProviders();
		viewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				return ArrayContentProvider.getInstance().getElements(inputElement);
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof final SubAppType type) {
					return children.get(type.getName()).toArray();
				}
				return new Object[0];
			}

			@Override
			public Object getParent(final Object element) {
				if (element instanceof final SubApp subApp && subApp.isTyped()) {
					return subApp.getType();
				}
				return null;
			}

			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof final SubAppType type) {
					return !children.get(type.getName()).isEmpty();
				}
				return false;
			}

		});

		final Tree table = viewer.getTree();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());
		table.setSortDirection(SWT.DOWN);

		table.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				final TreeItem tableItem = (TreeItem) event.item;
				if (tableItem.getData() instanceof final FBNetworkElement fb) {
					if (tableItem.getChecked()) {
						collectedElements.add(fb);
					} else {
						collectedElements.remove(tableItem.getData());
					}
				}
			}
		});

		// Check-box column
		final TreeViewerColumn colCheckBox = new TreeViewerColumn(viewer, SWT.WRAP);
		colCheckBox.getColumn().setImage(FordiacImage.ICON_EXPAND_ALL.getImage());
		colCheckBox.getColumn().addListener(SWT.Selection, event -> {
			changeSelectionState(table, selectAll);
			if (selectAll) {
				changeSelectionState(table, selectAll);
				colCheckBox.getColumn().setImage(
						PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_COLLAPSEALL));
			} else {
				changeSelectionState(table, selectAll);
				colCheckBox.getColumn().setImage(FordiacImage.ICON_EXPAND_ALL.getImage());
			}
			selectAll = !selectAll;
		});

		colCheckBox.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ""; //$NON-NLS-1$
			}
		});
		final SelectionListener sortListener = new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				// Get the selected column

				final TreeColumn selectedColumn = (TreeColumn) e.getSource();
				if (!table.getSortColumn().equals(selectedColumn)) {
					table.setSortColumn(selectedColumn);
				} else {
					table.setSortDirection(table.getSortDirection() == SWT.DOWN ? SWT.UP : SWT.DOWN);
				}
				table.redraw();
				viewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// Do nothing
			}
		};

		// Element column
		final TreeViewerColumn colElement = new TreeViewerColumn(viewer, SWT.LEAD);
		colElement.getColumn().setText(Messages.Element);
		colElement.setLabelProvider(labelElement);

		table.setSortColumn(colElement.getColumn());

		colElement.getColumn().addSelectionListener(sortListener);

		// Location name column
		final TreeViewerColumn colPath = new TreeViewerColumn(viewer, SWT.LEAD);
		colPath.getColumn().setText(Messages.Location);
		colPath.setLabelProvider(labelPath);

		colPath.getColumn().addSelectionListener(sortListener);

		// Type name column
		final TreeViewerColumn colType = new TreeViewerColumn(viewer, SWT.LEAD);
		colType.getColumn().setText(FordiacMessages.Type);
		colType.setLabelProvider(labelType);
		colType.getColumn().addSelectionListener(sortListener);

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				final TreeColumn sortCol = table.getSortColumn();
				String s1 = "";
				String s2 = "";
				if (sortCol.equals(colElement.getColumn())) {
					s1 = labelElement.getText(e1);
					s2 = labelElement.getText(e2);
				} else if (sortCol.equals(colPath.getColumn())) {
					s1 = labelPath.getText(e1);
					s2 = labelPath.getText(e2);
				} else if (sortCol.equals(colType.getColumn())) {
					s1 = labelType.getText(e1);
					s2 = labelType.getText(e2);
				}
				return table.getSortDirection() == SWT.DOWN ? s1.compareTo(s2) : s2.compareTo(s1);
			}
		});

	}

	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(CHECK_BOX_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		return layout;
	}

	void changeSelectionState(final Tree table, final boolean state) {
		for (final TreeItem tableItem : table.getItems()) {
			tableItem.setChecked(state);
			if (tableItem.getData() instanceof final FBNetworkElement fb) {
				if (state) {
					collectedElements.add(fb);
				} else {
					collectedElements.remove(tableItem.getData());
				}
			}
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

}
