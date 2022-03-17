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
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.widgets.PinInfoDataWidget;

public class PinAdapterInfoSection extends PinEventInfoSection {


	@Override
	protected void pinInfoCreation() {
		pinInfo = new PinInfoDataWidget(leftComposite, widgetFactory);
		((PinInfoDataWidget) pinInfo).setAdapterVisibility(false);
	}
}
