// Generated from br/unb/cic/CtxRegex.g4 by ANTLR 4.3
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
public class CtxRegexLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__11=1, T__10=2, T__9=3, T__8=4, T__7=5, T__6=6, T__5=7, T__4=8, T__3=9, 
		T__2=10, T__1=11, T__0=12, BOOL=13, VAR=14, FLOAT=15, NEWLINE=16, WS=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'"
	};
	public static final String[] ruleNames = {
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "BOOL", "VAR", "FLOAT", "NEWLINE", "WS", "DIGIT"
	};


	public CtxRegexLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CtxRegex.g4"; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23\u0089\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\17\6\17j\n\17\r\17\16\17k\3\20\6\20o\n\20\r\20\16\20p\3\20\5\20t\n"+
		"\20\3\20\7\20w\n\20\f\20\16\20z\13\20\3\21\6\21}\n\21\r\21\16\21~\3\22"+
		"\6\22\u0082\n\22\r\22\16\22\u0083\3\22\3\22\3\23\3\23\2\2\24\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\2\3\2\7\7\2ccghnntw~~\5\2C\\aac|\4\2\f\f\17\17\4\2\13\13\"\"\3\2"+
		"\62;\u008d\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\3\'\3\2\2\2\5)\3\2\2\2\7<\3\2\2\2\t>\3\2\2\2\13@\3\2\2\2"+
		"\rB\3\2\2\2\17D\3\2\2\2\21F\3\2\2\2\23I\3\2\2\2\25L\3\2\2\2\27N\3\2\2"+
		"\2\31Q\3\2\2\2\33f\3\2\2\2\35i\3\2\2\2\37n\3\2\2\2!|\3\2\2\2#\u0081\3"+
		"\2\2\2%\u0087\3\2\2\2\'(\7(\2\2(\4\3\2\2\2)*\7c\2\2*+\7u\2\2+,\7u\2\2"+
		",-\7g\2\2-.\7t\2\2./\7v\2\2/\60\7k\2\2\60\61\7q\2\2\61\62\7p\2\2\62\63"+
		"\7\"\2\2\63\64\7v\2\2\64\65\7t\2\2\65\66\7k\2\2\66\67\7i\2\2\678\7i\2"+
		"\289\7g\2\29:\7t\2\2:;\7\"\2\2;\6\3\2\2\2<=\7@\2\2=\b\3\2\2\2>?\7+\2\2"+
		"?\n\3\2\2\2@A\7*\2\2A\f\3\2\2\2BC\7>\2\2C\16\3\2\2\2DE\7?\2\2E\20\3\2"+
		"\2\2FG\7@\2\2GH\7?\2\2H\22\3\2\2\2IJ\7#\2\2JK\7?\2\2K\24\3\2\2\2LM\7~"+
		"\2\2M\26\3\2\2\2NO\7>\2\2OP\7?\2\2P\30\3\2\2\2QR\7c\2\2RS\7u\2\2ST\7u"+
		"\2\2TU\7g\2\2UV\7t\2\2VW\7v\2\2WX\7k\2\2XY\7q\2\2YZ\7p\2\2Z[\7\"\2\2["+
		"\\\7e\2\2\\]\7q\2\2]^\7p\2\2^_\7f\2\2_`\7k\2\2`a\7v\2\2ab\7k\2\2bc\7q"+
		"\2\2cd\7p\2\2de\7\"\2\2e\32\3\2\2\2fg\t\2\2\2g\34\3\2\2\2hj\t\3\2\2ih"+
		"\3\2\2\2jk\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\36\3\2\2\2mo\5%\23\2nm\3\2\2\2"+
		"op\3\2\2\2pn\3\2\2\2pq\3\2\2\2qs\3\2\2\2rt\7\60\2\2sr\3\2\2\2st\3\2\2"+
		"\2tx\3\2\2\2uw\5%\23\2vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y \3\2\2"+
		"\2zx\3\2\2\2{}\t\4\2\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177"+
		"\"\3\2\2\2\u0080\u0082\t\5\2\2\u0081\u0080\3\2\2\2\u0082\u0083\3\2\2\2"+
		"\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086"+
		"\b\22\2\2\u0086$\3\2\2\2\u0087\u0088\t\6\2\2\u0088&\3\2\2\2\t\2kpsx~\u0083"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}