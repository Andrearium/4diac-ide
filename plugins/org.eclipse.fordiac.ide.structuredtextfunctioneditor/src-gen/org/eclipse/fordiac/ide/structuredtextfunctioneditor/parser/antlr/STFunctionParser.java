/*
 * generated by Xtext 2.26.0
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.parser.antlr.internal.InternalSTFunctionParser;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class STFunctionParser extends AbstractAntlrParser {

	@Inject
	private STFunctionGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalSTFunctionParser createParser(XtextTokenStream stream) {
		return new InternalSTFunctionParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "STFunctionSource";
	}

	public STFunctionGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(STFunctionGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
