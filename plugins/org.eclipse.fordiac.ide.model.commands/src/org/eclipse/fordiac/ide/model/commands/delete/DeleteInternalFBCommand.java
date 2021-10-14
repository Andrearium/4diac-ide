/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.commands.Command;

public class DeleteInternalFBCommand extends Command {

	/** The type to whose list the new variable is added. */
	private final BaseFBType baseFbtype;

	/** The variable that is deleted */
	private FB fbToDelete;

	/** The old index. */
	private int oldIndex;

	public DeleteInternalFBCommand(final BaseFBType baseFbtype, final FB fb) {
		this.baseFbtype = baseFbtype;
		this.fbToDelete = fb;
	}

	private EList<FB> getInteralFBList() {
		BaseFBType type = baseFbtype;
		return type.getInternalFbs();
	}

	@Override
	public void execute() {
		oldIndex = getInteralFBList().indexOf(fbToDelete);
		redo();
	}

	@Override
	public void redo() {
		getInteralFBList().remove(fbToDelete);
	}

	@Override
	public void undo() {
		getInteralFBList().add(oldIndex, fbToDelete);
	}

}
