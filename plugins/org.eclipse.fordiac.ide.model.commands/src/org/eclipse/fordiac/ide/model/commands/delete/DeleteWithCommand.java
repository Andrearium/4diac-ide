/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.Command;

public class DeleteWithCommand extends Command {
	private With with;
	private VarDeclaration oldVarDecl;
	private Event event;

	public DeleteWithCommand(final With with) {
		this.with = with;
	}

	@Override
	public void execute() {
		event = (Event) with.eContainer();
		oldVarDecl = with.getVariables();
		with.setVariables(null);
		event.getWith().remove(with);
	}

	@Override
	public void undo() {
		event.getWith().add(with);
		with.setVariables(oldVarDecl);
	}

	@Override
	public void redo() {
		with.setVariables(null);
		event.getWith().remove(with);
	}
}
