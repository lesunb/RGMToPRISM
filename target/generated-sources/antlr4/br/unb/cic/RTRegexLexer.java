// Generated from br/unb/cic/RTRegex.g4 by ANTLR 4.3
package br.unb.cic;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RTRegexLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, FLOAT=7, SEQ=8, INT=9, 
		C_SEQ=10, C_INT=11, C_RTRY=12, ALT=13, TASK=14, GOAL=15, SKIP=16, NEWLINE=17, 
		WS=18;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'"
	};
	public static final String[] ruleNames = {
		"T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "FLOAT", "SEQ", "INT", 
		"C_SEQ", "C_INT", "C_RTRY", "ALT", "TASK", "GOAL", "SKIP", "NEWLINE", 
		"WS", "DIGIT"
	};


	public RTRegexLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "RTRegex.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24l\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\7\3\b\6\b=\n\b\r\b\16\b>\3\b\5\bB\n\b\3\b\7\bE\n"+
		"\b\f\b\16\bH\13\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\6\22`\n\22\r\22\16"+
		"\22a\3\23\6\23e\n\23\r\23\16\23f\3\23\3\23\3\24\3\24\2\2\25\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\2\3\2\5\4\2\f\f\17\17\3\2\13\13\3\2\62;o\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3)\3"+
		"\2\2\2\5.\3\2\2\2\7\60\3\2\2\2\t\62\3\2\2\2\13\64\3\2\2\2\r\66\3\2\2\2"+
		"\17<\3\2\2\2\21I\3\2\2\2\23K\3\2\2\2\25M\3\2\2\2\27O\3\2\2\2\31Q\3\2\2"+
		"\2\33S\3\2\2\2\35U\3\2\2\2\37W\3\2\2\2!Y\3\2\2\2#_\3\2\2\2%d\3\2\2\2\'"+
		"j\3\2\2\2)*\7v\2\2*+\7t\2\2+,\7{\2\2,-\7*\2\2-\4\3\2\2\2./\7+\2\2/\6\3"+
		"\2\2\2\60\61\7<\2\2\61\b\3\2\2\2\62\63\7*\2\2\63\n\3\2\2\2\64\65\7A\2"+
		"\2\65\f\3\2\2\2\66\67\7q\2\2\678\7r\2\289\7v\2\29:\7*\2\2:\16\3\2\2\2"+
		";=\5\'\24\2<;\3\2\2\2=>\3\2\2\2><\3\2\2\2>?\3\2\2\2?A\3\2\2\2@B\7\60\2"+
		"\2A@\3\2\2\2AB\3\2\2\2BF\3\2\2\2CE\5\'\24\2DC\3\2\2\2EH\3\2\2\2FD\3\2"+
		"\2\2FG\3\2\2\2G\20\3\2\2\2HF\3\2\2\2IJ\7=\2\2J\22\3\2\2\2KL\7%\2\2L\24"+
		"\3\2\2\2MN\7-\2\2N\26\3\2\2\2OP\7\'\2\2P\30\3\2\2\2QR\7B\2\2R\32\3\2\2"+
		"\2ST\7~\2\2T\34\3\2\2\2UV\7V\2\2V\36\3\2\2\2WX\7I\2\2X \3\2\2\2YZ\7u\2"+
		"\2Z[\7m\2\2[\\\7k\2\2\\]\7r\2\2]\"\3\2\2\2^`\t\2\2\2_^\3\2\2\2`a\3\2\2"+
		"\2a_\3\2\2\2ab\3\2\2\2b$\3\2\2\2ce\t\3\2\2dc\3\2\2\2ef\3\2\2\2fd\3\2\2"+
		"\2fg\3\2\2\2gh\3\2\2\2hi\b\23\2\2i&\3\2\2\2jk\t\4\2\2k(\3\2\2\2\b\2>A"+
		"Faf\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}