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
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.application.wizards.StructSearch;
import org.eclipse.fordiac.ide.application.wizards.StructUpdateDialog;
import org.eclipse.fordiac.ide.model.commands.change.TransferInstanceCommentsCommand;
import org.eclipse.fordiac.ide.model.helpers.FBEndpointFinder;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransferInstanceCommentsHandler extends AbstractHandler {

	private static final int DEFAULT_BUTTON_INDEX = 0; // Save
	private Button outputConnectedOnlyBtn;
	private StructManipulator selectedItem;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final StructManipulatorEditPart struct = (StructManipulatorEditPart) sel.getFirstElement();
		selectedItem = struct.getModel();
		final String[] labels = { Messages.TransferInstanceComments_TransferLabel, Messages.Cancel };

		final StructUpdateDialog structUpdateDialog = new StructUpdateDialog(null,
				Messages.TransferInstanceComments_WizardTitle, null, "",
				MessageDialog.NONE, labels, DEFAULT_BUTTON_INDEX,
				(DataTypeEntry) struct.getModel().getStructType().getTypeEntry()) {
			@Override
			protected final List<INamedElement> performStructSearch() {
				final StructSearch structSearch = new StructSearch(dataTypeEntry);

				List<INamedElement> elements = new ArrayList<>();

				final EObject root = EcoreUtil.getRootContainer(struct.getModel());
				if (root instanceof AutomationSystem) {
					elements = structSearch.getAllTypesWithStructFromSystem((AutomationSystem) root);
				} else if (root instanceof SubAppType) {
					elements = structSearch.getAllTypesWithStructFromNetworkElements(
							((SubAppType) root).getFBNetwork().getNetworkElements());
				}
				elements.removeIf(el -> el.equals(struct.getModel()) || isTypedOrContainedInTypedInstance(el));

				// output connected elements only filter
				final List<INamedElement> elementsFilteredOut = new ArrayList<>();
				if (outputConnectedOnlyBtn != null && !outputConnectedOnlyBtn.isDisposed() && outputConnectedOnlyBtn.getSelection()) {
					final List<FBNetworkElement> connectedFbs = FBEndpointFinder.getConnectedFbs(new ArrayList<>(),
							selectedItem);
					elements.forEach(res -> {
						if (res instanceof StructManipulator
								&& (!((StructManipulator) res).getInterface().getEventInputs().isEmpty()
										|| !((StructManipulator) res).getInterface().getInputVars().isEmpty())
								&& !connectedFbs.contains(res)) {
							elementsFilteredOut.add(res);
						}
					});
				}
				elements.removeAll(elementsFilteredOut);
				return elements;
			}

			@Override
			protected void createfilterButtons(final Composite parent) {
				outputConnectedOnlyBtn = new Button(parent, SWT.CHECK);
				outputConnectedOnlyBtn.setText("Output connected only");
				outputConnectedOnlyBtn.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(final SelectionEvent e) {
						refresh();
					}

					@Override
					public void widgetDefaultSelected(final SelectionEvent e) {
						// do nothing
					}
				});
			}

		};

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		if (structUpdateDialog.open() == DEFAULT_BUTTON_INDEX) {
			final TransferInstanceCommentsCommand cmd = new TransferInstanceCommentsCommand(
					struct.getModel(), structUpdateDialog.getUpdatedTypes());
			commandStack.execute(cmd);
		}
		return null;
	}

	public static List<FBNetworkElement> getConnectedFbs(final FBNetworkElement src) {
		final List<FBNetworkElement> connectedElements = new ArrayList<>();

		final List<IInterfaceElement> pins = new ArrayList<>();
		pins.addAll(src.getInterface().getEventOutputs());
		pins.addAll(src.getInterface().getOutputVars());

		for (final IInterfaceElement pin : pins) {
			connectedElements.addAll(getConnectedFbs(pin));
		}

		// search for connected elements of connected elements
		if (!connectedElements.isEmpty() && ((connectedElements.size() == 1 && !connectedElements.get(0).equals(src))
				|| (connectedElements.size() > 1))) {
			final List<FBNetworkElement> connectedOfConnectedElements = new ArrayList<>();
			for (final FBNetworkElement element : connectedElements) {
				if (!element.equals(src)) {
					connectedOfConnectedElements.addAll(getConnectedFbs(element));
				}
			}
			connectedOfConnectedElements.forEach(el -> {
				if (!connectedElements.contains(el)) {
					connectedElements.add(el);
				}
			});
		}

		return connectedElements.stream().distinct().collect(Collectors.toList());
	}

	private static List<FBNetworkElement> getConnectedFbs(final IInterfaceElement srcPin) {

		final List<FBNetworkElement> connectedElements = new ArrayList<>();
		for (final Connection con : srcPin.getOutputConnections()) {
			if (con.getDestinationElement() instanceof SubApp) {
				connectedElements.addAll(getConnectedFbs(con.getDestination()));
			} else {
				connectedElements.add(con.getDestinationElement());
			}
		}

		return connectedElements;

	}

	private static boolean isTypedOrContainedInTypedInstance(final INamedElement element) {
		return element.eContainer() != null && element.eContainer().eContainer() instanceof SubApp
				&& (((SubApp) element.eContainer().eContainer()).isContainedInTypedInstance()
						|| ((SubApp) element.eContainer().eContainer()).isTyped());
	}

}
