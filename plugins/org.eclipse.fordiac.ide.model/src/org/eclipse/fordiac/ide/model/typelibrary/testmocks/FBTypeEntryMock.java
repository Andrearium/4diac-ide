/*********************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.testmocks;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class FBTypeEntryMock extends BasicNotifierImpl implements FBTypeEntry {

	private FBType fbType;
	private TypeLibrary typelib;
	private IFile file;

	public FBTypeEntryMock(final FBType fbType, final TypeLibrary typelib, final IFile file) {
		super();
		this.fbType = fbType;
		this.typelib = typelib;
		this.file = file;
	}

	public FBTypeEntryMock(final FBTypeEntry typeEntry) {
		this(typeEntry.getType(), typeEntry.getTypeLibrary(), typeEntry.getFile());
	}

	@Override
	public IFile getFile() {
		return file;
	}

	@Override
	public void setFile(final IFile value) {
		file = value;
	}

	@Override
	public long getLastModificationTimestamp() {
		return 0;
	}

	@Override
	public void setLastModificationTimestamp(final long value) {
		// currently not needed in mock
	}

	@Override
	public void setType(final LibraryElement value) {
		fbType = (FBType) value;
	}

	@Override
	public void setTypeEditable(final LibraryElement value) {
		// currently not needed in mock
	}

	@Override
	public TypeLibrary getTypeLibrary() {
		return typelib;
	}

	@Override
	public void setTypeLibrary(final TypeLibrary typeLib) {
		this.typelib = typeLib;
	}

	@Override
	public FBType getType() {
		return fbType;
	}

	@Override
	public FBType getTypeEditable() {
		// currently not needed in mock
		return null;
	}

	@Override
	public String getTypeName() {
		return getType().getName();
	}

}