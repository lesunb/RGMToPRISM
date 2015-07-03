// Generated from br/unb/cic/CtxRegex.g4 by ANTLR 4.3
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
public class CtxRegexParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__11=1, T__10=2, T__9=3, T__8=4, T__7=5, T__6=6, T__5=7, T__4=8, T__3=9, 
		T__2=10, T__1=11, T__0=12, BOOL=13, VAR=14, FLOAT=15, NEWLINE=16, WS=17;
	public static final String[] tokenNames = {
		"<INVALID>", "'&'", "'assertion trigger '", "'>'", "')'", "'('", "'<'", 
		"'='", "'>='", "'!='", "'|'", "'<='", "'assertion condition '", "BOOL", 
		"VAR", "FLOAT", "NEWLINE", "WS"
	};
	public static final int
		RULE_ctx = 0, RULE_expr = 1;
	public static final String[] ruleNames = {
		"ctx", "expr"
	};

	@Override
	public String getGrammarFileName() { return "CtxRegex.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CtxRegexParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CtxContext extends ParserRuleContext {
		public CtxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ctx; }
	 
		public CtxContext() { }
		public void copyFrom(CtxContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TriggerContext extends CtxContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TriggerContext(CtxContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterTrigger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitTrigger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitTrigger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintExprContext extends CtxContext {
		public TerminalNode NEWLINE() { return getToken(CtxRegexParser.NEWLINE, 0); }
		public CtxContext ctx() {
			return getRuleContext(CtxContext.class,0);
		}
		public PrintExprContext(CtxContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterPrintExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitPrintExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitPrintExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlankContext extends CtxContext {
		public TerminalNode NEWLINE() { return getToken(CtxRegexParser.NEWLINE, 0); }
		public BlankContext(CtxContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterBlank(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitBlank(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitBlank(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionContext extends CtxContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ConditionContext(CtxContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CtxContext ctx() throws RecognitionException {
		return ctx(0);
	}

	private CtxContext ctx(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CtxContext _localctx = new CtxContext(_ctx, _parentState);
		CtxContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_ctx, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			switch (_input.LA(1)) {
			case T__0:
				{
				_localctx = new ConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(5); match(T__0);
				setState(6); expr(0);
				}
				break;
			case T__10:
				{
				_localctx = new TriggerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(7); match(T__10);
				setState(8); expr(0);
				}
				break;
			case NEWLINE:
				{
				_localctx = new BlankContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(9); match(NEWLINE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(16);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PrintExprContext(new CtxContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_ctx);
					setState(12);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(13); match(NEWLINE);
					}
					} 
				}
				setState(18);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
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
	public static class COrContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public COrContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CLEContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CLEContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCLE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCLE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCLE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CEQContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CEQContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCEQ(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCEQ(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCEQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CLTContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CLTContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCLT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCLT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCLT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CGEContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CGEContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCGE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCGE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCGE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CGTContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CGTContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCGT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCGT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCGT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CFloatContext extends ExprContext {
		public TerminalNode FLOAT() { return getToken(CtxRegexParser.FLOAT, 0); }
		public CFloatContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCFloat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCFloat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CBoolContext extends ExprContext {
		public TerminalNode BOOL() { return getToken(CtxRegexParser.BOOL, 0); }
		public CBoolContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CVarContext extends ExprContext {
		public TerminalNode VAR() { return getToken(CtxRegexParser.VAR, 0); }
		public CVarContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCVar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CParensContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CParensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CAndContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CAndContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CDIFFContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CDIFFContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).enterCDIFF(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtxRegexListener ) ((CtxRegexListener)listener).exitCDIFF(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtxRegexVisitor ) return ((CtxRegexVisitor<? extends T>)visitor).visitCDIFF(this);
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			switch (_input.LA(1)) {
			case BOOL:
				{
				_localctx = new CBoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(20); match(BOOL);
				}
				break;
			case VAR:
				{
				_localctx = new CVarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(21); match(VAR);
				}
				break;
			case FLOAT:
				{
				_localctx = new CFloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(22); match(FLOAT);
				}
				break;
			case T__7:
				{
				_localctx = new CParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(23); match(T__7);
				setState(24); expr(0);
				setState(25); match(T__8);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(55);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(53);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new CLTContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(29);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(30); ((CLTContext)_localctx).op = match(T__6);
						setState(31); expr(13);
						}
						break;

					case 2:
						{
						_localctx = new CLEContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(32);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(33); ((CLEContext)_localctx).op = match(T__1);
						setState(34); expr(12);
						}
						break;

					case 3:
						{
						_localctx = new CGTContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(35);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(36); ((CGTContext)_localctx).op = match(T__9);
						setState(37); expr(11);
						}
						break;

					case 4:
						{
						_localctx = new CGEContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(38);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(39); ((CGEContext)_localctx).op = match(T__4);
						setState(40); expr(10);
						}
						break;

					case 5:
						{
						_localctx = new CEQContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(41);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(42); ((CEQContext)_localctx).op = match(T__5);
						setState(43); expr(9);
						}
						break;

					case 6:
						{
						_localctx = new CDIFFContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(44);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(45); ((CDIFFContext)_localctx).op = match(T__3);
						setState(46); expr(8);
						}
						break;

					case 7:
						{
						_localctx = new CAndContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(47);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(48); ((CAndContext)_localctx).op = match(T__11);
						setState(49); expr(7);
						}
						break;

					case 8:
						{
						_localctx = new COrContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(50);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(51); ((COrContext)_localctx).op = match(T__2);
						setState(52); expr(6);
						}
						break;
					}
					} 
				}
				setState(57);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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
		case 0: return ctx_sempred((CtxContext)_localctx, predIndex);

		case 1: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean ctx_sempred(CtxContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 12);

		case 2: return precpred(_ctx, 11);

		case 3: return precpred(_ctx, 10);

		case 4: return precpred(_ctx, 9);

		case 5: return precpred(_ctx, 8);

		case 6: return precpred(_ctx, 7);

		case 7: return precpred(_ctx, 6);

		case 8: return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23=\4\2\t\2\4\3\t"+
		"\3\3\2\3\2\3\2\3\2\3\2\3\2\5\2\r\n\2\3\2\3\2\7\2\21\n\2\f\2\16\2\24\13"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\36\n\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\7\38\n\3\f\3\16\3;\13\3\3\3\2\4\2\4\4\2\4\2\2H\2\f\3\2\2\2\4\35\3\2"+
		"\2\2\6\7\b\2\1\2\7\b\7\16\2\2\b\r\5\4\3\2\t\n\7\4\2\2\n\r\5\4\3\2\13\r"+
		"\7\22\2\2\f\6\3\2\2\2\f\t\3\2\2\2\f\13\3\2\2\2\r\22\3\2\2\2\16\17\f\6"+
		"\2\2\17\21\7\22\2\2\20\16\3\2\2\2\21\24\3\2\2\2\22\20\3\2\2\2\22\23\3"+
		"\2\2\2\23\3\3\2\2\2\24\22\3\2\2\2\25\26\b\3\1\2\26\36\7\17\2\2\27\36\7"+
		"\20\2\2\30\36\7\21\2\2\31\32\7\7\2\2\32\33\5\4\3\2\33\34\7\6\2\2\34\36"+
		"\3\2\2\2\35\25\3\2\2\2\35\27\3\2\2\2\35\30\3\2\2\2\35\31\3\2\2\2\369\3"+
		"\2\2\2\37 \f\16\2\2 !\7\b\2\2!8\5\4\3\17\"#\f\r\2\2#$\7\r\2\2$8\5\4\3"+
		"\16%&\f\f\2\2&\'\7\5\2\2\'8\5\4\3\r()\f\13\2\2)*\7\n\2\2*8\5\4\3\f+,\f"+
		"\n\2\2,-\7\t\2\2-8\5\4\3\13./\f\t\2\2/\60\7\13\2\2\608\5\4\3\n\61\62\f"+
		"\b\2\2\62\63\7\3\2\2\638\5\4\3\t\64\65\f\7\2\2\65\66\7\f\2\2\668\5\4\3"+
		"\b\67\37\3\2\2\2\67\"\3\2\2\2\67%\3\2\2\2\67(\3\2\2\2\67+\3\2\2\2\67."+
		"\3\2\2\2\67\61\3\2\2\2\67\64\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:"+
		"\5\3\2\2\2;9\3\2\2\2\7\f\22\35\679";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}