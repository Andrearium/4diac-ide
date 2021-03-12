/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editparts;

import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating new EditParts.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ResourceDiagramEditPartFactory extends ElementEditPartFactory {

	public ResourceDiagramEditPartFactory(final GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof IInterfaceElement) {
			final IInterfaceElement element = (IInterfaceElement) modelElement;
			if (element.getFBNetworkElement() instanceof SubApp && null == element.getFBNetworkElement().getType()) {
				return new UntypedSubAppInterfaceElementEditPartForResource();
			}
			return new InterfaceEditPartForResourceFBs();
		}
		if (modelElement instanceof VirtualIO) {
			return new VirtualInOutputEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}

	@Override
	protected EditPart getPartForFBNetwork(final FBNetwork fbNetwork) {
		return new FBNetworkContainerEditPart();
	}

}
