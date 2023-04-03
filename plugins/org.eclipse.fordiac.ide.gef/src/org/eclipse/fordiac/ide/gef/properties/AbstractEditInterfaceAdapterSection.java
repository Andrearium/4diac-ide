/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added addEntry method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.nat.AdapterColumnProvider;
import org.eclipse.fordiac.ide.gef.nat.AdapterListProvider;
import org.eclipse.fordiac.ide.gef.nat.FordiacInterfaceListProvider;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.swt.widgets.Group;

public abstract class AbstractEditInterfaceAdapterSection extends AbstractEditInterfaceSection<AdapterDeclaration> {

	private static final boolean ADAPTER_TYPE_SELECTION = true;

	@Override
	public void initTypeSelection(final DataTypeLibrary dataTypeLib) {
		final List<String> adapterTypes = new ArrayList<>();
		if ((null != getType()) && (null != getTypeLibrary())) {
			getTypeLibrary().getAdapterTypesSorted().forEach(adpType -> adapterTypes.add(adpType.getTypeName()));
		}
		typeSelection.put("Adapter Types", adapterTypes); //$NON-NLS-1$
	}

	@Override
	protected String[] fillTypeCombo() {
		return new String[0];
	}

	protected AdapterType getLastUsedAdapterType(final InterfaceList interfaceList, final IInterfaceElement interfaceElement,
			final boolean isInput) {
		if (null != interfaceElement) {
			return (AdapterType) interfaceElement.getType();
		}
		final EList<AdapterDeclaration> adapterList = getAdapterList(interfaceList, isInput);
		if (!adapterList.isEmpty()) {
			return adapterList.get(adapterList.size() - 1).getType();
		}
		return getTypeLibrary().getAdapterTypes().values().iterator().next().getType();
	}

	@Override
	protected int getInsertingIndex(final IInterfaceElement interfaceElement, final boolean isInput) {
		if (null != interfaceElement) {
			final InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getAdapterList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<AdapterDeclaration> getAdapterList(final InterfaceList interfaceList, final boolean isInput) {
		return isInput ? interfaceList.getSockets() : interfaceList.getPlugs();
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof AdapterDeclaration) {
			final AdapterDeclaration adapterDeclaration = (AdapterDeclaration) entry;
			cmd.add(newInsertCommand(adapterDeclaration, isInput, index));
		}
	}

	@Override
	protected void configureLabels(final ListDataProvider<AdapterDeclaration> provider, final LabelStack configLabels,
			final int columnPosition, final int rowPosition) {
		final AdapterDeclaration rowItem = provider.getRowObject(rowPosition);
		switch (columnPosition) {
		case I4diacNatTableUtil.TYPE:
			if (rowItem.getType() instanceof ErrorMarkerDataType) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			}
			if (isEditable()) {

				configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			}
			break;
		case I4diacNatTableUtil.NAME:
		case I4diacNatTableUtil.COMMENT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			break;
		default:
			break;
		}
	}


	@Override
	public void setupOutputTable(final Group outputsGroup) {
		IEditableRule rule = IEditableRule.NEVER_EDITABLE;
		if (isEditable()) {
			rule = IEditableRule.ALWAYS_EDITABLE;
		}
		outputProvider = new AdapterListProvider(this);
		final DataLayer outputDataLayer = setupDataLayer(outputProvider);
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer,
				new AdapterColumnProvider(),
				rule, new DataTypeSelectionButton(typeSelection, ADAPTER_TYPE_SELECTION), this, false);
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		IEditableRule rule = IEditableRule.NEVER_EDITABLE;
		if (isEditable()) {
			rule = IEditableRule.ALWAYS_EDITABLE;
		}
		inputProvider = new AdapterListProvider(this);
		final DataLayer inputDataLayer = setupDataLayer(inputProvider);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer, new AdapterColumnProvider(),
				rule, new DataTypeSelectionButton(typeSelection, ADAPTER_TYPE_SELECTION), this, true);
	}

	@Override
	public void setTableInput(final InterfaceList il) {
		((FordiacInterfaceListProvider) inputProvider).setInput(il.getSockets());
		((FordiacInterfaceListProvider) outputProvider).setInput(il.getPlugs());
	}

}
