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
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class STCoreProposalStringJoiner {

	private final CharSequence prefix;
	private final CharSequence delimiter;
	private final CharSequence suffix;

	private final List<STCoreProposalString> strings = new ArrayList<>();

	public STCoreProposalStringJoiner(final CharSequence delimiter) {
		this(delimiter, "", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public STCoreProposalStringJoiner(final CharSequence delimiter, final CharSequence prefix,
			final CharSequence suffix) {
		this.prefix = prefix;
		this.delimiter = delimiter;
		this.suffix = suffix;
	}

	public STCoreProposalStringJoiner add(final STCoreProposalString str) {
		strings.add(str);
		return this;
	}

	public STCoreProposalStringJoiner merge(final STCoreProposalStringJoiner other) {
		strings.addAll(other.strings);
		return this;
	}

	public STCoreProposalString toProposalString() {
		final STCoreProposalString result = new STCoreProposalString(toString());
		int stringsLength = 0;
		for (int i = 0; i < strings.size(); ++i) {
			final STCoreProposalString str = strings.get(i);
			str.getRegions(prefix.length() + i * delimiter.length() + stringsLength).forEach(result::addRegion);
			stringsLength += str.length();
		}
		return result;
	}

	@Override
	public String toString() {
		return strings.stream().collect(Collectors.joining(delimiter, prefix, suffix));
	}
}
