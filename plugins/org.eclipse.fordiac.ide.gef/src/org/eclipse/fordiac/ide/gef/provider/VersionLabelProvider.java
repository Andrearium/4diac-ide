/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2014, 2016 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.provider;

import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The Class VersionLabelProvider.
 */
public class VersionLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
	 *      int)
	 */
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
	 *      int)
	 */
	@Override
	public String getColumnText(final Object element, final int columnIndex) {

		if (element instanceof VersionInfo) {
			switch (columnIndex) {
			case 0:
				return ((VersionInfo) element).getVersion();
			case 1:
				return null == ((VersionInfo) element).getOrganization() ? "" : ((VersionInfo) element).getOrganization(); //$NON-NLS-1$
			case 2:
				return ((VersionInfo) element).getAuthor();
			case 3:
				return ((VersionInfo) element).getDate();
			case 4:
				return (null == ((VersionInfo) element).getRemarks()) ? "" : ((VersionInfo) element).getRemarks(); //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}
