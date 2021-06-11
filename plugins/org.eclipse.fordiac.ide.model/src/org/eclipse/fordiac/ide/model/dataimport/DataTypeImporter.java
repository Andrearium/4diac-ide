/********************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr - initial implementation and documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;

/**
 * Managing class for importing *.dtp files
 *
 */

public class DataTypeImporter extends TypeImporter {

	@Override
	public AnyDerivedType getElement() {
		return (AnyDerivedType) super.getElement();
	}

	public DataTypeImporter(final IFile typeFile) {
		super(typeFile);
	}

	@Override
	public void loadElement() {
		super.loadElement();
		if (!(getElement() instanceof StructuredType)) {
			createErrorMarker(
					MessageFormat.format(Messages.DataTypeImporter_UNSUPPORTED_DATATYPE_IN_FILE, getFile().getName()));
		}
	}

	@Override
	protected AnyDerivedType createRootModelElement() {
		return DataFactory.eINSTANCE.createAnyDerivedType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.DATA_TYPE;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(getElement());
				break;
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(getElement());
				break;
			case LibraryElementTags.COMPILER_INFO_ELEMENT:
				getElement().setCompilerInfo(parseCompilerInfo());
				break;
			case LibraryElementTags.ASN1_TAG:
				parseASN1Tag(getElement());
				break;
			case LibraryElementTags.STRUCTURED_TYPE_ELEMENT:
				setElement(convertToStructuredType(getElement()));
				parseStructuredType((StructuredType) getElement());
				break;
				// TODO support other AnyDerivedTypes such as ArrayType
			default:
				return false;
			}
			return true;
		};
	}

	private void parseASN1Tag(final AnyDerivedType type) throws XMLStreamException {
		proceedToEndElementNamed(LibraryElementTags.ASN1_TAG);
	}

	/**
	 * This method converts the data type AnyDerivedType to a StructuredType.
	 *
	 * @param type - The AnyDerivedType that is being converted to StructuredType
	 *
	 * @return - A StructuredType that is converted
	 */
	private static StructuredType convertToStructuredType(final AnyDerivedType type) {
		final StructuredType structuredType = DataFactory.eINSTANCE.createStructuredType();
		copyGeneralTypeInformation(structuredType, type);
		return structuredType;
	}

	private static void copyGeneralTypeInformation(final AnyDerivedType dstType, final AnyDerivedType srcType) {
		dstType.setName(srcType.getName());
		dstType.setComment(srcType.getComment());
		dstType.setIdentification(srcType.getIdentification());
		dstType.getVersionInfo().addAll(srcType.getVersionInfo());
		dstType.setCompilerInfo(srcType.getCompilerInfo());
	}

	/**
	 * This method parses the contents of a StructuredType
	 *
	 * @param
	 *
	 * @
	 */
	private void parseStructuredType(final StructuredType struct) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.STRUCTURED_TYPE_ELEMENT, name -> {
			if (LibraryElementTags.VAR_DECLARATION_ELEMENT.equals(name)) {
				struct.getMemberVariables().add(parseVarDeclaration());
				return true;
			}
			return false;
		});
	}
}
