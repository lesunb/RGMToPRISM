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
		T__2=10, T__1=11, T__0=12, BOOL=13, VAR=14, INT=15, FLOAT=16, NEWLINE=17, 
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
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "BOOL", "VAR", "INT", "FLOAT", "NEWLINE", "WS", 
		"DIGIT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24\u009d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16r\n\16\3\17\6\17u\n"+
		"\17\r\17\16\17v\3\17\7\17z\n\17\f\17\16\17}\13\17\3\20\6\20\u0080\n\20"+
		"\r\20\16\20\u0081\3\21\6\21\u0085\n\21\r\21\16\21\u0086\3\21\3\21\7\21"+
		"\u008b\n\21\f\21\16\21\u008e\13\21\3\22\6\22\u0091\n\22\r\22\16\22\u0092"+
		"\3\23\6\23\u0096\n\23\r\23\16\23\u0097\3\23\3\23\3\24\3\24\2\2\25\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\2\3\2\6\5\2C\\aac|\4\2\f\f\17\17\4\2\13\13\"\"\3\2\62;"+
		"\u00a3\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\3)\3\2\2\2\5,\3\2\2\2\7.\3\2\2\2\tC\3\2\2\2\13"+
		"E\3\2\2\2\rH\3\2\2\2\17J\3\2\2\2\21M\3\2\2\2\23O\3\2\2\2\25Q\3\2\2\2\27"+
		"S\3\2\2\2\31f\3\2\2\2\33q\3\2\2\2\35t\3\2\2\2\37\177\3\2\2\2!\u0084\3"+
		"\2\2\2#\u0090\3\2\2\2%\u0095\3\2\2\2\'\u009b\3\2\2\2)*\7>\2\2*+\7?\2\2"+
		"+\4\3\2\2\2,-\7(\2\2-\6\3\2\2\2./\7c\2\2/\60\7u\2\2\60\61\7u\2\2\61\62"+
		"\7g\2\2\62\63\7t\2\2\63\64\7v\2\2\64\65\7k\2\2\65\66\7q\2\2\66\67\7p\2"+
		"\2\678\7\"\2\289\7e\2\29:\7q\2\2:;\7p\2\2;<\7f\2\2<=\7k\2\2=>\7v\2\2>"+
		"?\7k\2\2?@\7q\2\2@A\7p\2\2AB\7\"\2\2B\b\3\2\2\2CD\7*\2\2D\n\3\2\2\2EF"+
		"\7#\2\2FG\7?\2\2G\f\3\2\2\2HI\7+\2\2I\16\3\2\2\2JK\7@\2\2KL\7?\2\2L\20"+
		"\3\2\2\2MN\7~\2\2N\22\3\2\2\2OP\7>\2\2P\24\3\2\2\2QR\7?\2\2R\26\3\2\2"+
		"\2ST\7c\2\2TU\7u\2\2UV\7u\2\2VW\7g\2\2WX\7t\2\2XY\7v\2\2YZ\7k\2\2Z[\7"+
		"q\2\2[\\\7p\2\2\\]\7\"\2\2]^\7v\2\2^_\7t\2\2_`\7k\2\2`a\7i\2\2ab\7i\2"+
		"\2bc\7g\2\2cd\7t\2\2de\7\"\2\2e\30\3\2\2\2fg\7@\2\2g\32\3\2\2\2hi\7h\2"+
		"\2ij\7c\2\2jk\7n\2\2kl\7u\2\2lr\7g\2\2mn\7v\2\2no\7t\2\2op\7w\2\2pr\7"+
		"g\2\2qh\3\2\2\2qm\3\2\2\2r\34\3\2\2\2su\t\2\2\2ts\3\2\2\2uv\3\2\2\2vt"+
		"\3\2\2\2vw\3\2\2\2w{\3\2\2\2xz\5\'\24\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2"+
		"{|\3\2\2\2|\36\3\2\2\2}{\3\2\2\2~\u0080\5\'\24\2\177~\3\2\2\2\u0080\u0081"+
		"\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082 \3\2\2\2\u0083\u0085"+
		"\5\'\24\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0084\3\2\2\2"+
		"\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008c\7\60\2\2\u0089\u008b"+
		"\5\'\24\2\u008a\u0089\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2"+
		"\u008c\u008d\3\2\2\2\u008d\"\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0091\t"+
		"\3\2\2\u0090\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0090\3\2\2\2\u0092"+
		"\u0093\3\2\2\2\u0093$\3\2\2\2\u0094\u0096\t\4\2\2\u0095\u0094\3\2\2\2"+
		"\u0096\u0097\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0099"+
		"\3\2\2\2\u0099\u009a\b\23\2\2\u009a&\3\2\2\2\u009b\u009c\t\5\2\2\u009c"+
		"(\3\2\2\2\13\2qv{\u0081\u0086\u008c\u0092\u0097\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}