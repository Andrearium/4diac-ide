/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;

public class FBInstanceSearch extends InstanceSearch {

	public static FBInstanceSearch createSubappSearch(final StructuredType entry) {
		return new FBInstanceSearch(new StructInterfaceSearchFilter((DataTypeEntry) entry.getTypeEntry()));
	}

	public FBInstanceSearch(final DataTypeEntry dataTypeEntry) {
		super(new StructInterfaceSearchFilter(dataTypeEntry));
	}

	private FBInstanceSearch(final SearchFilter filter) {
		super(filter);
	}

	private static class StructInterfaceSearchFilter implements SearchFilter {

		private final DataTypeEntry entry;

		public StructInterfaceSearchFilter(final DataTypeEntry entry) {
			this.entry = entry;
		}

		@Override
		public boolean apply(final INamedElement searchCandiate) {
			if (searchCandiate instanceof final FBNetworkElement subapp) {
				return subapp.getInterface().getAllInterfaceElements().stream()
						.anyMatch(i -> i.getTypeName().equals(entry.getTypeName()));
			}
			return false;
		}
	}
}
