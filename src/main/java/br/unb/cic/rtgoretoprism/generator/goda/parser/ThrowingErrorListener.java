package br.unb.cic.rtgoretoprism.generator.goda.parser;

import java.util.Date;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import br.unb.cic.rtgoretoprism.console.ATCConsole;

public class ThrowingErrorListener extends BaseErrorListener {

	public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
			throws ParseCancellationException {
		ATCConsole.println( "Error creating DTMC model: line " + line + ":" + charPositionInLine + " " + msg);
		throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
	}
}