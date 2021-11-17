/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;

public final class ConnectionAnnotations {

	public static void setVisible(final Connection connection, final boolean visible) {
		if (visible) {
			// if we are visible the attribute can be removed
			connection.deleteAttribute(LibraryElementTags.CONNECTION_VISIBLE);
		} else {
			setVisible(connection, Boolean.toString(visible));
		}
	}

	private static void setVisible(final Connection connection, final String visible) {
		connection.setAttribute(LibraryElementTags.CONNECTION_VISIBLE, FordiacKeywords.STRING, visible, "");  //$NON-NLS-1$
	}

	public static boolean isVisible(final Connection connection) {
		final String visibleAttribute = connection.getAttributeValue(LibraryElementTags.CONNECTION_VISIBLE);
		return !"false".equalsIgnoreCase(visibleAttribute); //$NON-NLS-1$
	}

	private ConnectionAnnotations() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}
}
