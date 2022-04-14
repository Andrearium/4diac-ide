/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class UntypedSubappConnectorBorder extends ConnectorBorder {

	private boolean secondPaint;

	public UntypedSubappConnectorBorder(final IInterfaceElement editPartModelOject) {
		super(editPartModelOject);
	}

	private SubApp getSubapp() {
		return (SubApp) getEditPartModelOject().getFBNetworkElement();
	}

	@Override
	public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
		super.paint(figure, graphics, insets);
		if (getSubapp().isUnfolded()) {
			secondPaint = true;
			super.paint(figure, graphics, insets);
			secondPaint = false;
		}
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		if (getSubapp().isUnfolded()) {
			final int lrMargin = (isAdapter()) ? LR_ADAPTER_MARGIN : LR_MARGIN;
			return new Insets(0, lrMargin, 0, lrMargin);
		}
		return super.getInsets(figure);
	}

	@Override
	public boolean isInput() {
		final boolean input = super.isInput();
		return (secondPaint) ? !input : input;
	}

}
