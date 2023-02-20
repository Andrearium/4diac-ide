/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Alexander Lumplecker
 *     - changed ChangeMemberVariableOrderCommand to ChangeVariableOrderCommand
 *   Martin Jobst - add initial value cell editor support
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.datatypeeditor.Messages;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnProvider;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationListProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVariableOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class StructViewingComposite extends Composite implements CommandExecutor, I4diacNatTableUtil, ISelectionProvider
{
	protected Map<String, List<String>> typeSelection = new HashMap<>();
	private NatTable natTable;
	private final CommandStack cmdStack;
	private final IWorkbenchPart part;
	private final DataTypeEntry dataTypeEntry;
	private VarDeclarationListProvider structMemberProvider;

	public StructViewingComposite(final Composite parent, final int style, final CommandStack cmdStack,
			final DataTypeEntry dataTypeEntry, final IWorkbenchPart part) {
		super(parent, style);
		this.cmdStack = cmdStack;
		this.dataTypeEntry = dataTypeEntry;
		this.part = part;
	}

	public void createPartControl(final Composite parent) {
		final TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		showLabel(parent);

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(parent, widgetFactory);

		createNatTable(parent);

		buttons.bindToTableViewer(natTable, this,
				ref -> new CreateMemberVariableCommand(getType(), getInsertionIndex(), getVarName(), getDataType()),
				ref -> new DeleteMemberVariableCommand(getType(), (VarDeclaration) ref),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, true),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, false));

		part.getSite().setSelectionProvider(this);
	}

	private static void showLabel(final Composite parent) {
		final Label label = new Label(parent, SWT.CENTER);
		label.setText(Messages.StructViewingComposite_Headline);
	}

	private void createNatTable(final Composite parent) {
		structMemberProvider = new VarDeclarationListProvider(null, new VarDeclarationColumnAccessor(this, null));
		structMemberProvider.setInput(getType().getMemberVariables());
		structMemberProvider.setTypeLib(getType().getTypeLibrary());
		final DataLayer inputDataLayer = setupDataLayer(structMemberProvider);
		initTypeSelection(getType().getTypeLibrary().getDataTypeLibrary());
		natTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, new VarDeclarationColumnProvider(),
				IEditableRule.ALWAYS_EDITABLE, new DataTypeSelectionButton(typeSelection), this);
		natTable.addConfiguration(new InitialValueEditorConfiguration(structMemberProvider));
		natTable.configure();
	}

	public DataLayer setupDataLayer(final ListDataProvider outputProvider) {
		final DataLayer dataLayer = new DataLayer(outputProvider);
		final IConfigLabelAccumulator labelAcc = dataLayer.getConfigLabelAccumulator();

		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			if (labelAcc != null) {
				labelAcc.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
			}
			if (columnPosition == I4diacNatTableUtil.TYPE) {
				configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			}

			if (columnPosition == I4diacNatTableUtil.NAME || columnPosition == I4diacNatTableUtil.COMMENT) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			}

			if (columnPosition == I4diacNatTableUtil.INITIAL_VALUE) {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			}
		});
		return dataLayer;
	}

	private DataType getDataType() {
		final VarDeclaration memVar = getLastSelectedVariable();
		return (null != memVar) ? memVar.getType() : null;
	}

	private String getVarName() {
		final VarDeclaration memVar = getLastSelectedVariable();
		return (null != memVar) ? memVar.getName() : null;
	}

	private int getInsertionIndex() {
		final VarDeclaration memVar = getLastSelectedVariable();
		if (null == memVar) {
			return getType().getMemberVariables().size();
		}
		return getType().getMemberVariables().indexOf(memVar) + 1;
	}

	private VarDeclaration getLastSelectedVariable() {
		return (VarDeclaration) structMemberProvider.getLastSelectedVariable(natTable);
	}

	private StructuredType getType() {
		return (StructuredType) dataTypeEntry.getTypeEditable();
	}

	private DataTypeLibrary getDataTypeLibrary() {
		return dataTypeEntry.getTypeLibrary().getDataTypeLibrary();
	}

	private boolean isValidStruct(final StructuredType type) {
		return type.getMemberVariables().stream()
				.filter(memVar -> memVar.getType() instanceof StructuredType)
				.noneMatch(memVar -> memVar.getTypeName().equals(StructViewingComposite.this.getType().getName())
						|| !isValidStruct(getDataTypeLibrary().getStructuredType(memVar.getTypeName())));
	}

	public void reload() {
		structMemberProvider.setInput(getType().getMemberVariables());

	}

	@Override
	public void executeCommand(final Command cmd) {
		cmdStack.execute(cmd);
	}

	@Override
	public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
		if (entry instanceof VarDeclaration) {
			final VarDeclaration varEntry = (VarDeclaration) entry;
			cmd.add(new InsertVariableCommand(getType().getMemberVariables(), varEntry, index));
		}
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
	}

	public Object removeEntry(final int index, final CompoundCommand cmd) {
		final VarDeclaration entry = (VarDeclaration) getEntry(index);
		cmd.add(new DeleteMemberVariableCommand(getType(), entry));
		return entry;
	}

	public DataType getStruct() {
		return getType();
	}

	public Object getEntry(final int index) {
		return getType().getMemberVariables().get(index);
	}

	private static void createContextMenu(final TableViewer viewer) {
		// TODO reimplement that
		// OpenStructMenu.addTo(viewer);
	}

	@Override
	public ISelection getSelection() {
		// for now return the whole object so that property sheets and other stuff can filter on it.
		return new StructuredSelection(this);
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	public void initTypeSelection(final DataTypeLibrary dataTypeLib) {
		final List<String> elementaryTypes = new ArrayList<>();
		dataTypeLib.getDataTypesSorted().stream().filter(type -> !(type instanceof StructuredType))
		.forEach(type -> elementaryTypes.add(type.getName()));
		typeSelection.put("Elementary Types", elementaryTypes); //$NON-NLS-1$

		final List<String> structuredTypes = new ArrayList<>();
		dataTypeLib.getDataTypesSorted().stream().filter(StructuredType.class::isInstance)
		.forEach(type -> structuredTypes.add(type.getName()));
		typeSelection.put("Structured Types", structuredTypes); //$NON-NLS-1$
	}

	public void refresh() {
		reload();
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// currently nothing to be done here
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// currently nothing to be done here
	}

	@Override
	public void setSelection(final ISelection selection) {
		// currently nothing to be done here
	}
}
