/*
 * generated by Xtext 2.26.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.fordiac.ide.structuredtextcore.parser.antlr.internal.InternalSTCoreParser;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class STCoreParser extends AbstractAntlrParser {

	@Inject
	private STCoreGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalSTCoreParser createParser(XtextTokenStream stream) {
		return new InternalSTCoreParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "STCoreSource";
	}

	public STCoreGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(STCoreGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
