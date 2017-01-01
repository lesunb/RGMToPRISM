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
		T__9=1, T__8=2, T__7=3, T__6=4, T__5=5, T__4=6, T__3=7, T__2=8, T__1=9, 
		T__0=10, BOOL=11, VAR=12, INT=13, FLOAT=14, NEWLINE=15, WS=16;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'"
	};
	public static final String[] ruleNames = {
		"T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", 
		"T__0", "BOOL", "VAR", "INT", "FLOAT", "NEWLINE", "WS", "DIGIT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\22\u0096\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\5\fj\n\f\3\r\6\rm\n\r\r\r\16\rn\3\r\6\rr\n\r\r\r\16\rs\3\16\6\16w\n\16"+
		"\r\16\16\16x\3\17\6\17|\n\17\r\17\16\17}\3\17\5\17\u0081\n\17\3\17\7\17"+
		"\u0084\n\17\f\17\16\17\u0087\13\17\3\20\6\20\u008a\n\20\r\20\16\20\u008b"+
		"\3\21\6\21\u008f\n\21\r\21\16\21\u0090\3\21\3\21\3\22\3\22\2\2\23\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\2\3\2\6\5\2C\\aac|\4\2\f\f\17\17\4\2\13\13\"\"\3\2\62;\u009d\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3%\3\2\2\2"+
		"\5(\3\2\2\2\7*\3\2\2\2\t?\3\2\2\2\13B\3\2\2\2\rE\3\2\2\2\17G\3\2\2\2\21"+
		"I\3\2\2\2\23K\3\2\2\2\25^\3\2\2\2\27i\3\2\2\2\31l\3\2\2\2\33v\3\2\2\2"+
		"\35{\3\2\2\2\37\u0089\3\2\2\2!\u008e\3\2\2\2#\u0094\3\2\2\2%&\7>\2\2&"+
		"\'\7?\2\2\'\4\3\2\2\2()\7(\2\2)\6\3\2\2\2*+\7c\2\2+,\7u\2\2,-\7u\2\2-"+
		".\7g\2\2./\7t\2\2/\60\7v\2\2\60\61\7k\2\2\61\62\7q\2\2\62\63\7p\2\2\63"+
		"\64\7\"\2\2\64\65\7e\2\2\65\66\7q\2\2\66\67\7p\2\2\678\7f\2\289\7k\2\2"+
		"9:\7v\2\2:;\7k\2\2;<\7q\2\2<=\7p\2\2=>\7\"\2\2>\b\3\2\2\2?@\7#\2\2@A\7"+
		"?\2\2A\n\3\2\2\2BC\7@\2\2CD\7?\2\2D\f\3\2\2\2EF\7~\2\2F\16\3\2\2\2GH\7"+
		">\2\2H\20\3\2\2\2IJ\7?\2\2J\22\3\2\2\2KL\7c\2\2LM\7u\2\2MN\7u\2\2NO\7"+
		"g\2\2OP\7t\2\2PQ\7v\2\2QR\7k\2\2RS\7q\2\2ST\7p\2\2TU\7\"\2\2UV\7v\2\2"+
		"VW\7t\2\2WX\7k\2\2XY\7i\2\2YZ\7i\2\2Z[\7g\2\2[\\\7t\2\2\\]\7\"\2\2]\24"+
		"\3\2\2\2^_\7@\2\2_\26\3\2\2\2`a\7h\2\2ab\7c\2\2bc\7n\2\2cd\7u\2\2dj\7"+
		"g\2\2ef\7v\2\2fg\7t\2\2gh\7w\2\2hj\7g\2\2i`\3\2\2\2ie\3\2\2\2j\30\3\2"+
		"\2\2km\t\2\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2\2oq\3\2\2\2pr\5#"+
		"\22\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2t\32\3\2\2\2uw\5#\22\2vu"+
		"\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\34\3\2\2\2z|\5#\22\2{z\3\2\2\2"+
		"|}\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\u0080\3\2\2\2\177\u0081\7\60\2\2\u0080"+
		"\177\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0085\3\2\2\2\u0082\u0084\5#\22"+
		"\2\u0083\u0082\3\2\2\2\u0084\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086"+
		"\3\2\2\2\u0086\36\3\2\2\2\u0087\u0085\3\2\2\2\u0088\u008a\t\3\2\2\u0089"+
		"\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2"+
		"\2\2\u008c \3\2\2\2\u008d\u008f\t\4\2\2\u008e\u008d\3\2\2\2\u008f\u0090"+
		"\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092\3\2\2\2\u0092"+
		"\u0093\b\21\2\2\u0093\"\3\2\2\2\u0094\u0095\t\5\2\2\u0095$\3\2\2\2\f\2"+
		"insx}\u0080\u0085\u008b\u0090\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}