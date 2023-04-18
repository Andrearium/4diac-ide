/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - Adds validator to ensure global const are marked constant
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.validation;

import org.eclipse.fordiac.ide.globalconstantseditor.Messages;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;

/**
 * This class contains custom validation rules.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class GlobalConstantsValidator extends AbstractGlobalConstantsValidator {

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.globalconstseditor."; //$NON-NLS-1$
	public static final String GLOBAL_VARS_NOT_MARKED_CONSTANT = ISSUE_CODE_PREFIX + "globalVarsNotMarkedConstant"; //$NON-NLS-1$

	@Inject
	GlobalConstantsGrammarAccess grammarAccess;

	@Check
	public void checkVarConfigBlockIsMarkedConstant(final STVarGlobalDeclarationBlock declarationBlock) {
		if (!declarationBlock.isConstant()) {
			final var node = NodeModelUtils.findActualNodeFor(declarationBlock);
			for (final var n : node.getAsTreeIterable()) {
				final var grammarElement = n.getGrammarElement();
				if (grammarElement instanceof Keyword && grammarElement == grammarAccess
						.getSTVarGlobalDeclarationBlockAccess().getVAR_GLOBALKeyword_1()) {
					getMessageAcceptor().acceptError(Messages.GlobalConstValidator_GlobalConstBlockNotMarkedConstant,
							declarationBlock, n.getOffset(), n.getLength(), GLOBAL_VARS_NOT_MARKED_CONSTANT);
				}
			}
		}
	}
}
