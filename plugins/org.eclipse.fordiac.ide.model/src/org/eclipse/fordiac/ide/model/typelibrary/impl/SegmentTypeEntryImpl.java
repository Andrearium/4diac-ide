/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SEGImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class SegmentTypeEntryImpl extends AbstractTypeEntryImpl implements SegmentTypeEntry {

	@Override
	public SegmentType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof SegmentType) {
			return (SegmentType) type;
		}
		return null;
	}

	@Override
	public SegmentType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if(type instanceof SegmentType){
			return (SegmentType) type;
		}
		return null;
	}

	@Override
	public void setType(final LibraryElement type) {
		if(type instanceof SegmentType){
			super.setType(type);
		}else{
			super.setType(null);
			if(null != type){
				FordiacLogHelper.logError("tried to set no SegmentType as type entry for SegmentTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new SEGImporter(getFile());
	}

}
