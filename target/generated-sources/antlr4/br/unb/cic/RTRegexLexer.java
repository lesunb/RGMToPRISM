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
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, GID=7, FLOAT=8, SEQ=9, 
		INT=10, C_SEQ=11, C_INT=12, C_RTRY=13, ALT=14, SKIP=15, NEWLINE=16, WS=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'"
	};
	public static final String[] ruleNames = {
		"T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "GID", "FLOAT", "SEQ", 
		"INT", "C_SEQ", "C_INT", "C_RTRY", "ALT", "SKIP", "NEWLINE", "WS", "DIGIT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23i\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6"+
		"\3\6\3\7\3\7\3\b\3\b\3\b\3\t\6\t>\n\t\r\t\16\t?\3\t\5\tC\n\t\3\t\7\tF"+
		"\n\t\f\t\16\tI\13\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\20\3\20\3\20\3\21\6\21]\n\21\r\21\16\21^\3\22\6\22b\n"+
		"\22\r\22\16\22c\3\22\3\22\3\23\3\23\2\2\24\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\2\3\2\6\4\2I"+
		"IVV\4\2\f\f\17\17\3\2\13\13\3\2\62;l\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3\'\3\2\2\2\5,\3\2\2\2\7.\3\2"+
		"\2\2\t\63\3\2\2\2\13\65\3\2\2\2\r\67\3\2\2\2\179\3\2\2\2\21=\3\2\2\2\23"+
		"J\3\2\2\2\25L\3\2\2\2\27N\3\2\2\2\31P\3\2\2\2\33R\3\2\2\2\35T\3\2\2\2"+
		"\37V\3\2\2\2!\\\3\2\2\2#a\3\2\2\2%g\3\2\2\2\'(\7v\2\2()\7t\2\2)*\7{\2"+
		"\2*+\7*\2\2+\4\3\2\2\2,-\7+\2\2-\6\3\2\2\2./\7q\2\2/\60\7r\2\2\60\61\7"+
		"v\2\2\61\62\7*\2\2\62\b\3\2\2\2\63\64\7<\2\2\64\n\3\2\2\2\65\66\7*\2\2"+
		"\66\f\3\2\2\2\678\7A\2\28\16\3\2\2\29:\t\2\2\2:;\5\21\t\2;\20\3\2\2\2"+
		"<>\5%\23\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2AC\7\60\2"+
		"\2BA\3\2\2\2BC\3\2\2\2CG\3\2\2\2DF\5%\23\2ED\3\2\2\2FI\3\2\2\2GE\3\2\2"+
		"\2GH\3\2\2\2H\22\3\2\2\2IG\3\2\2\2JK\7=\2\2K\24\3\2\2\2LM\7%\2\2M\26\3"+
		"\2\2\2NO\7-\2\2O\30\3\2\2\2PQ\7\'\2\2Q\32\3\2\2\2RS\7B\2\2S\34\3\2\2\2"+
		"TU\7~\2\2U\36\3\2\2\2VW\7u\2\2WX\7m\2\2XY\7k\2\2YZ\7r\2\2Z \3\2\2\2[]"+
		"\t\3\2\2\\[\3\2\2\2]^\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_\"\3\2\2\2`b\t\4\2"+
		"\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\b\22\2\2f$\3\2"+
		"\2\2gh\t\5\2\2h&\3\2\2\2\b\2?BG^c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}