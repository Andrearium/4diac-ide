/**
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *       - registers hover provider
 *   Martin Jobst
 *       - register code mining preference initializer
 *       - content assist bindings
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.codemining.STCoreCodeMiningPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentAssistPreferences;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreContentProposalPriorities;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreSourceViewer.STCoreSourceViewerFactory;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRefactoringDocumentProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/** Use this class to register components to be used within the Eclipse IDE. */
@SuppressWarnings({ "restriction", "static-method" })
public class STCoreUiModule extends AbstractSTCoreUiModule {

	public STCoreUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider.class;
	}

	public Class<? extends IRefactoringDocument.Provider> bindIRefactoringDocument$Provider() {
		return STCoreRefactoringDocumentProvider.class;
	}

	public void configureCodeMinings(final Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("codeMiningInitializer")) //$NON-NLS-1$
		.to(STCoreCodeMiningPreferences.Initializer.class);
	}

	public Class<? extends IContentProposalPriorities> bindIContentProposalPriorities() {
		return STCoreContentProposalPriorities.class;
	}

	public Class<? extends XtextSourceViewer.Factory> bindXtextSourceViewer$Factory() {
		return STCoreSourceViewerFactory.class;
	}

	public void configureKeyBindingScope(final Binder binder) {
		binder.bindConstant().annotatedWith(Names.named(XtextEditor.KEY_BINDING_SCOPE))
		.to("org.eclipse.fordiac.ide.structuredtextcore.ui.STCoreEditorScope"); //$NON-NLS-1$
	}

	public void configureContentAssist(final Binder binder) {
		binder.bind(IPreferenceStoreInitializer.class).annotatedWith(Names.named("contentAssistInitializer")) //$NON-NLS-1$
		.to(STCoreContentAssistPreferences.Initializer.class);
		binder.bind(String.class).annotatedWith(
				com.google.inject.name.Names.named(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS))
		.toProvider(STCoreContentAssistPreferences.CompletionAutoActivationCharsProvider.class);
	}
}
