// Generated from br/unb/cic/RTRegex.g4 by ANTLR 4.3
package br.unb.cic;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RTRegexParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, FLOAT=7, SEQ=8, INT=9, 
		C_SEQ=10, C_RTRY=11, ALT=12, TASK=13, GOAL=14, SKIPP=15, NEWLINE=16, WS=17;
	public static final String[] tokenNames = {
		"<INVALID>", "'try('", "'opt('", "':'", "'?'", "'('", "')'", "FLOAT", 
		"';'", "'#'", "'+'", "'@'", "'|'", "'T'", "'G'", "'skip'", "NEWLINE", 
		"WS"
	};
	public static final int
		RULE_rt = 0, RULE_expr = 1;
	public static final String[] ruleNames = {
		"rt", "expr"
	};

	@Override
	public String getGrammarFileName() { return "RTRegex.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RTRegexParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RtContext extends ParserRuleContext {
		public RtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rt; }
	 
		public RtContext() { }
		public void copyFrom(RtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BlankContext extends RtContext {
		public TerminalNode NEWLINE() { return getToken(RTRegexParser.NEWLINE, 0); }
		public BlankContext(RtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterBlank(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitBlank(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitBlank(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintExprContext extends RtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(RTRegexParser.NEWLINE, 0); }
		public PrintExprContext(RtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterPrintExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitPrintExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitPrintExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RtContext rt() throws RecognitionException {
		RtContext _localctx = new RtContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_rt);
		try {
			setState(8);
			switch (_input.LA(1)) {
			case T__5:
			case T__4:
			case T__1:
			case TASK:
			case GOAL:
			case SKIPP:
				_localctx = new PrintExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(4); expr(0);
				setState(5); match(NEWLINE);
				}
				break;
			case NEWLINE:
				_localctx = new BlankContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(7); match(NEWLINE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParensContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GIdContext extends ExprContext {
		public Token t;
		public TerminalNode FLOAT() { return getToken(RTRegexParser.FLOAT, 0); }
		public GIdContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GTryContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GTryContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGTry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGTry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGTry(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GSkipContext extends ExprContext {
		public TerminalNode SKIPP() { return getToken(RTRegexParser.SKIPP, 0); }
		public GSkipContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGSkip(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGSkip(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGSkip(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GTimeContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GTimeContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGTime(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGTime(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GOptContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public GOptContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGOpt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGOpt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGOpt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GCardContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FLOAT() { return getToken(RTRegexParser.FLOAT, 0); }
		public GCardContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGCard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGCard(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGCard(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GAltContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GAltContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).enterGAlt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RTRegexListener ) ((RTRegexListener)listener).exitGAlt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RTRegexVisitor ) return ((RTRegexVisitor<? extends T>)visitor).visitGAlt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			switch (_input.LA(1)) {
			case T__4:
				{
				_localctx = new GOptContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(11); match(T__4);
				setState(12); expr(0);
				setState(13); match(T__0);
				}
				break;
			case T__5:
				{
				_localctx = new GTryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(15); match(T__5);
				setState(16); expr(0);
				setState(17); match(T__0);
				setState(18); match(T__2);
				setState(19); expr(0);
				setState(20); match(T__3);
				setState(21); expr(0);
				}
				break;
			case SKIPP:
				{
				_localctx = new GSkipContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(23); match(SKIPP);
				}
				break;
			case TASK:
			case GOAL:
				{
				_localctx = new GIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(24);
				((GIdContext)_localctx).t = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==TASK || _la==GOAL) ) {
					((GIdContext)_localctx).t = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(25); match(FLOAT);
				}
				break;
			case T__1:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(26); match(T__1);
				setState(27); expr(0);
				setState(28); match(T__0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(43);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(41);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new GAltContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(32);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(33); ((GAltContext)_localctx).op = match(ALT);
						setState(34); expr(8);
						}
						break;

					case 2:
						{
						_localctx = new GTimeContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(35);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(36);
						((GTimeContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==SEQ || _la==INT) ) {
							((GTimeContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(37); expr(5);
						}
						break;

					case 3:
						{
						_localctx = new GCardContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(38);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(39);
						((GCardContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << C_SEQ) | (1L << C_RTRY))) != 0)) ) {
							((GCardContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(40); match(FLOAT);
						}
						break;
					}
					} 
				}
				setState(45);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 7);

		case 1: return precpred(_ctx, 4);

		case 2: return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23\61\4\2\t\2\4\3"+
		"\t\3\3\2\3\2\3\2\3\2\5\2\13\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3!\n\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\7\3,\n\3\f\3\16\3/\13\3\3\3\2\3\4\4\2\4\2\5\3\2\17"+
		"\20\3\2\n\13\3\2\13\r\66\2\n\3\2\2\2\4 \3\2\2\2\6\7\5\4\3\2\7\b\7\22\2"+
		"\2\b\13\3\2\2\2\t\13\7\22\2\2\n\6\3\2\2\2\n\t\3\2\2\2\13\3\3\2\2\2\f\r"+
		"\b\3\1\2\r\16\7\4\2\2\16\17\5\4\3\2\17\20\7\b\2\2\20!\3\2\2\2\21\22\7"+
		"\3\2\2\22\23\5\4\3\2\23\24\7\b\2\2\24\25\7\6\2\2\25\26\5\4\3\2\26\27\7"+
		"\5\2\2\27\30\5\4\3\2\30!\3\2\2\2\31!\7\21\2\2\32\33\t\2\2\2\33!\7\t\2"+
		"\2\34\35\7\7\2\2\35\36\5\4\3\2\36\37\7\b\2\2\37!\3\2\2\2 \f\3\2\2\2 \21"+
		"\3\2\2\2 \31\3\2\2\2 \32\3\2\2\2 \34\3\2\2\2!-\3\2\2\2\"#\f\t\2\2#$\7"+
		"\16\2\2$,\5\4\3\n%&\f\6\2\2&\'\t\3\2\2\',\5\4\3\7()\f\n\2\2)*\t\4\2\2"+
		"*,\7\t\2\2+\"\3\2\2\2+%\3\2\2\2+(\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2"+
		"\2.\5\3\2\2\2/-\3\2\2\2\6\n +-";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}