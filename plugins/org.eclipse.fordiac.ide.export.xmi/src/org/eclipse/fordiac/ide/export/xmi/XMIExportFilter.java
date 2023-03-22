/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.xmi;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceSetInitializer;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreCommentAssociater;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportFactory;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

public class XMIExportFilter extends ExportFilter {
	public static final String XMI_EXTENSION = "xmi"; //$NON-NLS-1$

	@Override
	public void export(final IFile typeFile, final String destination, final boolean forceOverwrite)
			throws ExportException {
		export(typeFile, destination, forceOverwrite, null);
	}

	@Override
	public void export(final IFile typeFile, final String destination, final boolean forceOverwrite,
			final EObject element) throws ExportException {
		if (typeFile == null || !typeFile.exists()) {
			getErrors().add(Messages.XMIExportFilter_UnknownFile);
			return;
		}

		final URI sourceUri = URI.createPlatformResourceURI(typeFile.getFullPath().toString(), true);
		final URI destinationUri = URI.createFileURI(destination);
		final URI xmiUri = destinationUri.appendSegment(typeFile.getName()).appendFileExtension(XMI_EXTENSION);

		final XtextResourceSet resourceSet = new XtextResourceSet();
		new STAlgorithmResourceSetInitializer().initialize(resourceSet, typeFile.getProject());
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

		final Resource resource = resourceSet.getResource(sourceUri, true);
		if (resource instanceof final XtextResource xtextResource) {
			final EObject root = xtextResource.getParseResult().getRootASTElement();
			if (root instanceof final STSource source) {
				final var commentAssociater = ((XtextResource) resource).getResourceServiceProvider()
						.get(STCoreCommentAssociater.class);
				if (commentAssociater != null) {
					source.getComments().addAll(commentAssociater.associateComments(source));
				}
			}
		}
		resource.getContents().add(createInitialValues(resource));

		final ResourceSetImpl xmiResourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().putIfAbsent(XMI_EXTENSION,
				new XMIResourceFactoryImpl());
		final Resource xmiResource = xmiResourceSet.createResource(xmiUri);
		xmiResource.getContents().addAll(EcoreUtil.copyAll(resource.getContents()));

		if (!forceOverwrite && xmiResourceSet.getURIConverter().exists(xmiUri, Collections.emptyMap())
				&& !MessageDialog.openConfirm(Display.getDefault().getActiveShell(),
						Messages.XMIExportFilter_OverwriteDialogTitle,
						MessageFormat.format(Messages.XMIExportFilter_OverwriteDialogMessage, xmiUri.toFileString()))) {
			return;
		}

		try {
			final HashMap<String, Object> options = new HashMap<>();
			options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
			options.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
			xmiResource.save(options);
		} catch (final IOException e) {
			getErrors().add(e.getMessage());
		}
	}

	protected XMIExportInitialValues createInitialValues(final Resource resource) {
		final var result = XMIExportFactory.eINSTANCE.createXMIExportInitialValues();
		StreamSupport
		.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllProperContents(resource, true), 0), false)
		.filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
		.filter(XMIExportFilter::hasInitialValue).map(this::createInitialValue).flatMap(Optional::stream)
		.forEachOrdered(result.getInitialValues()::add);
		return result;
	}

	protected Optional<XMIExportInitialValue> createInitialValue(final VarDeclaration varDeclaration) {
		final var source = parseInitialValue(varDeclaration);
		if (source == null || source.getInitializerExpression() == null) {
			return Optional.empty();
		}
		final var result = XMIExportFactory.eINSTANCE.createXMIExportInitialValue();
		result.setVariable(varDeclaration);
		result.setExpression(source.getInitializerExpression());
		return Optional.of(result);
	}

	protected STInitializerExpressionSource parseInitialValue(final VarDeclaration varDeclaration) {
		return StructuredTextParseUtil.parse(varDeclaration.getValue().getValue(), varDeclaration.eResource().getURI(),
				STCoreUtil.getFeatureType(varDeclaration), null, null, getErrors(), getWarnings(), getInfos());
	}

	protected static boolean hasInitialValue(final VarDeclaration varDeclaration) {
		return varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank();
	}
}
