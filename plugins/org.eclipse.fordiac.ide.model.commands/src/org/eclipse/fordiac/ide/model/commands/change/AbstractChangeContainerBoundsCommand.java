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
 * 	Alois Zoitl - initial API and implementation and/or initial documentation
 * *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.List;

import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractChangeContainerBoundsCommand extends Command implements ConnectionLayoutTagger {

	private final int dx;
	private final int dy;
	private final int oldWidth;
	private final int oldHeight;
	private final int newWidth;
	private final int newHeight;
	private final PositionableElement target;
	private CompoundCommand updatePositions;

	protected AbstractChangeContainerBoundsCommand(final PositionableElement target, final int dx, final int dy,
			final int dw, final int dh, final int oldWidth, final int oldHeight) {
		this.target = target;
		this.dx = dx;
		this.dy = dy;
		this.oldWidth = oldWidth;
		this.oldHeight = oldHeight;
		newWidth = oldWidth + dw;
		newHeight = oldHeight + dh;
	}

	@Override
	public void execute() {
		updatePositions = createSetPosCommand();
		updateSize(newWidth, newHeight);
		if (updatePositions != null) {
			updatePositions.execute();
		}
	}

	@Override
	public void undo() {
		if (updatePositions != null) {
			updatePositions.undo();
		}
		updateSize(oldWidth, oldHeight);
	}

	@Override
	public void redo() {
		if (updatePositions != null) {
			updatePositions.redo();
		}
		updateSize(newWidth, newHeight);
	}


	public PositionableElement getTarget() {
		return target;
	}

	private CompoundCommand createSetPosCommand() {
		if (dx != 0 || dy != 0) {
			final CompoundCommand cmd = new CompoundCommand();
			cmd.add(new SetPositionCommand(target, dx, dy));
			// Ensure that the children stay at their position when the group grows or shrinks on the left/top side
			// If the child is in a group we must only consider it if the group the child is contained in itself is
			// changed.
			getChildren().stream().filter(el -> !el.isInGroup() || target.equals(el.getGroup()))
			.forEach(el -> cmd.add(new SetPositionCommand(el, -dx, -dy)));
			return cmd;
		}
		return null;
	}

	protected abstract void updateSize(int width, int height);

	protected abstract List<FBNetworkElement> getChildren();
}