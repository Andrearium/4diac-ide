/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *   Daniel Lindhuber - added recursive type handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;

public abstract class AbstractCreateFBNetworkElementCommand extends Command implements ConnectionLayoutTagger {

	private final FBNetworkElement element;
	private final FBNetwork fbNetwork;
	private int x;
	private int y;

	protected AbstractCreateFBNetworkElementCommand(final FBNetwork fbNetwork, final FBNetworkElement element,
			final int x, final int y) {
		this.fbNetwork = fbNetwork;
		this.element = element;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canExecute() {
		if (FBNetworkHelper.isTypeInsertionSave(element.getType(), fbNetwork)) {
			return fbNetwork != null;
		}
		return false;
	}

	@Override
	public void execute() {
		element.setInterface(getTypeInterfaceList().copy());
		element.updatePosition(x, y);
		insertFBNetworkElement();
		checkName();
	}

	protected void checkName() {
		element.setName(NameRepository.createUniqueName(element, getInitialInstanceName()));
	}

	@Override
	public void redo() {
		insertFBNetworkElement();
	}

	@Override
	public void undo() {
		removeFBNetworkElement();
	}

	public FBNetworkElement getElement() {
		return element;
	}

	protected String getInitialInstanceName() {
		return element.getTypeName();
	}

	protected abstract InterfaceList getTypeInterfaceList();

	public FBNetwork getFBNetwork() {
		return fbNetwork;
	}

	public static AbstractCreateFBNetworkElementCommand createCreateCommand(final TypeEntry typeEntry,
			final FBNetwork fbNetwork, final int x, final int y) {
		if (typeEntry instanceof FBTypeEntry) {
			return new FBCreateCommand((FBTypeEntry) typeEntry, fbNetwork, x, y);
		}
		if (typeEntry instanceof SubAppTypeEntry) {
			return new CreateSubAppInstanceCommand((SubAppTypeEntry) typeEntry, fbNetwork, x, y);
		}
		return null;
	}

	public void updateCreatePosition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	private void insertFBNetworkElement() {
		if (fbNetwork != null) {
			fbNetwork.getNetworkElements().add(element);
		}
	}

	private void removeFBNetworkElement() {
		if (fbNetwork != null) {
			fbNetwork.getNetworkElements().remove(element);
		}
	}
}
