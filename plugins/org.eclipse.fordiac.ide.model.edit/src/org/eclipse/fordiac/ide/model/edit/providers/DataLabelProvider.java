/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *	 Fabio Gandolfi
 *	   - swap out label provider for initial value
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.swt.graphics.Image;

/**
 * A label provider that can be used to display data in columns
 */
public class DataLabelProvider extends InitialValueLabelProvider {

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		return super.getColumnText(element, columnIndex);
	}
}
