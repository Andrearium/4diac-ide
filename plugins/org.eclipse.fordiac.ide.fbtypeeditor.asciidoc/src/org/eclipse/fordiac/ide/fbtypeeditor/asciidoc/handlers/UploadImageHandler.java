/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class UploadImageHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor == null) {
			return null;
		}

		final ITextEditor textEditor = Adapters.adapt(activeEditor, ITextEditor.class);
		if (textEditor == null) {
			return null;
		}

		final IEditorInput editorInput = textEditor.getEditorInput();
		if (!(editorInput instanceof IFileEditorInput)) {
			return null;
		}

		// Retrieve the current file being edited ("type.adoc")
		final IFile docFile = ((IFileEditorInput) editorInput).getFile();
		// Since type.adoc lives inside the assets folder, the parent is the assets
		// folder
		final IContainer assetsFolder = docFile.getParent();

		if (assetsFolder == null || !assetsFolder.exists()) {
			throw new ExecutionException("Assets folder does not exist.");
		}

		// 1. Open Dialog to choose an image from local disk
		final FileDialog fileDialog = new FileDialog(HandlerUtil.getActiveShell(event), SWT.OPEN);
		fileDialog.setText("Select Image to Upload");
		fileDialog.setFilterExtensions("*.png", "*.jpg", "*.jpeg", "*.gif", "*.svg");

		final String selectedPath = fileDialog.open();
		if (selectedPath == null) {
			return null; // User canceled the dialog
		}

		final File localFile = new File(selectedPath);
		final String fileName = localFile.getName();

		// 2. Upload/Copy the file into the Eclipse assets workspace folder
		if (assetsFolder instanceof final org.eclipse.core.resources.IFolder folder) {
			final IFile targetFile = folder.getFile(fileName);
			try (InputStream inputStream = new FileInputStream(localFile)) {
				if (targetFile.exists()) {
					// Overwrite the file if it already exists
					targetFile.setContents(inputStream, IResource.FORCE, new NullProgressMonitor());
				} else {
					// Create new workspace resource file
					targetFile.create(inputStream, true, new NullProgressMonitor());
				}
				// Refresh folder content so Eclipse workspace is in-sync
				assetsFolder.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			} catch (final Exception e) {
				throw new ExecutionException("Failed to upload image: " + e.getMessage(), e);
			}
		}

		// 3. Insert the AsciiDoc image markup at the cursor position
		final IDocument document = textEditor.getDocumentProvider().getDocument(editorInput);
		final ISelection selection = textEditor.getSelectionProvider().getSelection();

		if (document != null && selection instanceof final ITextSelection textSelection) {
			final int offset = textSelection.getOffset();
			final int length = textSelection.getLength();

			// As type.adoc is in the same directory, we can reference by filename directly
			final String imageMarkup = "image::" + fileName + "[]";
			try {
				document.replace(offset, length, imageMarkup);
			} catch (final Exception e) {
				throw new ExecutionException("Failed to insert image markup", e);
			}
		}

		return null;
	}
}
