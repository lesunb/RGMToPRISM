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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23x\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17"+
		"\6\17Y\n\17\r\17\16\17Z\3\20\6\20^\n\20\r\20\16\20_\3\20\5\20c\n\20\3"+
		"\20\7\20f\n\20\f\20\16\20i\13\20\3\21\6\21l\n\21\r\21\16\21m\3\22\6\22"+
		"q\n\22\r\22\16\22r\3\22\3\22\3\23\3\23\2\2\24\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\2\3\2\7\7"+
		"\2ccghnntw~~\5\2C\\aac|\4\2\f\f\17\17\4\2\13\13\"\"\3\2\62;|\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3\'\3"+
		"\2\2\2\5)\3\2\2\2\7+\3\2\2\2\t-\3\2\2\2\13/\3\2\2\2\r\61\3\2\2\2\17\63"+
		"\3\2\2\2\21\65\3\2\2\2\238\3\2\2\2\25;\3\2\2\2\27=\3\2\2\2\31@\3\2\2\2"+
		"\33U\3\2\2\2\35X\3\2\2\2\37]\3\2\2\2!k\3\2\2\2#p\3\2\2\2%v\3\2\2\2\'("+
		"\7\'\2\2(\4\3\2\2\2)*\7(\2\2*\6\3\2\2\2+,\7@\2\2,\b\3\2\2\2-.\7+\2\2."+
		"\n\3\2\2\2/\60\7*\2\2\60\f\3\2\2\2\61\62\7>\2\2\62\16\3\2\2\2\63\64\7"+
		"?\2\2\64\20\3\2\2\2\65\66\7@\2\2\66\67\7?\2\2\67\22\3\2\2\289\7#\2\29"+
		":\7?\2\2:\24\3\2\2\2;<\7~\2\2<\26\3\2\2\2=>\7>\2\2>?\7?\2\2?\30\3\2\2"+
		"\2@A\7c\2\2AB\7u\2\2BC\7u\2\2CD\7g\2\2DE\7t\2\2EF\7v\2\2FG\7k\2\2GH\7"+
		"q\2\2HI\7p\2\2IJ\7\"\2\2JK\7e\2\2KL\7q\2\2LM\7p\2\2MN\7f\2\2NO\7k\2\2"+
		"OP\7v\2\2PQ\7k\2\2QR\7q\2\2RS\7p\2\2ST\7\"\2\2T\32\3\2\2\2UV\t\2\2\2V"+
		"\34\3\2\2\2WY\t\3\2\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\36\3\2"+
		"\2\2\\^\5%\23\2]\\\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`b\3\2\2\2ac\7"+
		"\60\2\2ba\3\2\2\2bc\3\2\2\2cg\3\2\2\2df\5%\23\2ed\3\2\2\2fi\3\2\2\2ge"+
		"\3\2\2\2gh\3\2\2\2h \3\2\2\2ig\3\2\2\2jl\t\4\2\2kj\3\2\2\2lm\3\2\2\2m"+
		"k\3\2\2\2mn\3\2\2\2n\"\3\2\2\2oq\t\5\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2"+
		"rs\3\2\2\2st\3\2\2\2tu\b\22\2\2u$\3\2\2\2vw\t\6\2\2w&\3\2\2\2\t\2Z_bg"+
		"mr\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}