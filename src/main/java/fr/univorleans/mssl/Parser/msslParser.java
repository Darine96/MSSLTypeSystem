// Generated from java-escape by ANTLR 4.11.1
package fr.univorleans.mssl.Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class msslParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INTEGER=1, BOOLEAN=2, Int=3, Bool=4, Unit=5, EQ=6, SEMIC=7, COMMA=8, COLON=9, 
		LBRACE=10, RBRACE=11, LPAR=12, RPAR=13, LT=14, GT=15, DoubleQuote=16, 
		Sub=17, PLUS=18, FN=19, MAIN=20, LET=21, MUT=22, Box=23, Trc=24, Sig=25, 
		Emit=26, When=27, Watch=28, Clone=29, Spawn=30, Cooperate=31, PRINT=32, 
		Mul=33, Ref=34, LIF=35, Dot=36, IF=37, ELSE=38, OPERATOR=39, IDENTIFIER=40, 
		VALID_ID_START=41, VALID_ID_CHAR=42, WS=43, OC_COMMENT=44, SL_COMMENT=45;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_params = 2, RULE_signals = 3, 
		RULE_signature = 4, RULE_lif = 5, RULE_expr = 6, RULE_type_expression = 7, 
		RULE_block = 8, RULE_instructions = 9, RULE_instruction = 10, RULE_declVar = 11, 
		RULE_value = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "params", "signals", "signature", "lif", "expr", 
			"type_expression", "block", "instructions", "instruction", "declVar", 
			"value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'int'", "'bool'", "'unit'", "'='", "';'", "','", "':'", 
			"'{'", "'}'", "'('", "')'", "'<'", "'>'", "'\"'", "'-'", "'+'", "'fn'", 
			"'main'", "'let'", "'mut'", "'box'", "'trc'", "'Sig'", "'emit'", "'when'", 
			"'watch'", "'clone'", "'spawn'", "'cooperate'", "'print!'", "'*'", "'&'", 
			"'''", "'.'", "'if'", "'else'", "'=='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INTEGER", "BOOLEAN", "Int", "Bool", "Unit", "EQ", "SEMIC", "COMMA", 
			"COLON", "LBRACE", "RBRACE", "LPAR", "RPAR", "LT", "GT", "DoubleQuote", 
			"Sub", "PLUS", "FN", "MAIN", "LET", "MUT", "Box", "Trc", "Sig", "Emit", 
			"When", "Watch", "Clone", "Spawn", "Cooperate", "PRINT", "Mul", "Ref", 
			"LIF", "Dot", "IF", "ELSE", "OPERATOR", "IDENTIFIER", "VALID_ID_START", 
			"VALID_ID_CHAR", "WS", "OC_COMMENT", "SL_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public msslParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	 
		public ProgramContext() { }
		public void copyFrom(ProgramContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ProgramContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(msslParser.EOF, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public ProgContext(ProgramContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			_localctx = new ProgContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FN) {
				{
				{
				setState(26);
				declaration();
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
			block();
			setState(33);
			match(EOF);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	 
		public DeclarationContext() { }
		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Declaration_functionContext extends DeclarationContext {
		public TerminalNode FN() { return getToken(msslParser.FN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public TerminalNode Sub() { return getToken(msslParser.Sub, 0); }
		public TerminalNode GT() { return getToken(msslParser.GT, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public SignalsContext signals() {
			return getRuleContext(SignalsContext.class,0);
		}
		public Declaration_functionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitDeclaration_function(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		int _la;
		try {
			_localctx = new Declaration_functionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			match(FN);
			setState(36);
			match(IDENTIFIER);
			setState(37);
			match(LPAR);
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==MUT) {
				{
				setState(38);
				params();
				}
			}

			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMIC) {
				{
				setState(41);
				signals();
				}
			}

			setState(44);
			match(RPAR);
			setState(45);
			match(Sub);
			setState(46);
			match(GT);
			setState(47);
			value();
			setState(48);
			signature();
			setState(49);
			block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ParamsContext extends ParserRuleContext {
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
	 
		public ParamsContext() { }
		public void copyFrom(ParamsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParamsFuncContext extends ParamsContext {
		public List<TerminalNode> MUT() { return getTokens(msslParser.MUT); }
		public TerminalNode MUT(int i) {
			return getToken(msslParser.MUT, i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(msslParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(msslParser.IDENTIFIER, i);
		}
		public List<TerminalNode> COLON() { return getTokens(msslParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(msslParser.COLON, i);
		}
		public List<SignatureContext> signature() {
			return getRuleContexts(SignatureContext.class);
		}
		public SignatureContext signature(int i) {
			return getRuleContext(SignatureContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(msslParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(msslParser.COMMA, i);
		}
		public ParamsFuncContext(ParamsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitParamsFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_params);
		int _la;
		try {
			_localctx = new ParamsFuncContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(MUT);
			setState(52);
			match(IDENTIFIER);
			setState(53);
			match(COLON);
			setState(54);
			signature();
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(55);
				match(COMMA);
				setState(56);
				match(MUT);
				setState(57);
				match(IDENTIFIER);
				setState(58);
				match(COLON);
				setState(59);
				signature();
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	@SuppressWarnings("CheckReturnValue")
	public static class SignalsContext extends ParserRuleContext {
		public SignalsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signals; }
	 
		public SignalsContext() { }
		public void copyFrom(SignalsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SignalsFuncContext extends SignalsContext {
		public TerminalNode SEMIC() { return getToken(msslParser.SEMIC, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(msslParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(msslParser.IDENTIFIER, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(msslParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(msslParser.COMMA, i);
		}
		public SignalsFuncContext(SignalsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSignalsFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignalsContext signals() throws RecognitionException {
		SignalsContext _localctx = new SignalsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_signals);
		int _la;
		try {
			_localctx = new SignalsFuncContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(65);
			match(SEMIC);
			setState(66);
			match(IDENTIFIER);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(67);
				match(COMMA);
				setState(68);
				match(IDENTIFIER);
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
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

	@SuppressWarnings("CheckReturnValue")
	public static class SignatureContext extends ParserRuleContext {
		public SignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signature; }
	 
		public SignatureContext() { }
		public void copyFrom(SignatureContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigUnitContext extends SignatureContext {
		public TerminalNode Unit() { return getToken(msslParser.Unit, 0); }
		public SigUnitContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigUnit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigBoxContext extends SignatureContext {
		public TerminalNode Box() { return getToken(msslParser.Box, 0); }
		public TerminalNode LT() { return getToken(msslParser.LT, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode GT() { return getToken(msslParser.GT, 0); }
		public SigBoxContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigBox(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigBoolContext extends SignatureContext {
		public TerminalNode Bool() { return getToken(msslParser.Bool, 0); }
		public SigBoolContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigBool(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigIntContext extends SignatureContext {
		public TerminalNode Int() { return getToken(msslParser.Int, 0); }
		public SigIntContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigCloneContext extends SignatureContext {
		public TerminalNode Clone() { return getToken(msslParser.Clone, 0); }
		public TerminalNode LT() { return getToken(msslParser.LT, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode GT() { return getToken(msslParser.GT, 0); }
		public SigCloneContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigClone(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigRefContext extends SignatureContext {
		public TerminalNode Ref() { return getToken(msslParser.Ref, 0); }
		public LifContext lif() {
			return getRuleContext(LifContext.class,0);
		}
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode MUT() { return getToken(msslParser.MUT, 0); }
		public SigRefContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigRef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SigTrcContext extends SignatureContext {
		public TerminalNode Trc() { return getToken(msslParser.Trc, 0); }
		public TerminalNode LT() { return getToken(msslParser.LT, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode GT() { return getToken(msslParser.GT, 0); }
		public SigTrcContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitSigTrc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignatureContext signature() throws RecognitionException {
		SignatureContext _localctx = new SignatureContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_signature);
		int _la;
		try {
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Unit:
				_localctx = new SigUnitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				match(Unit);
				}
				break;
			case Int:
				_localctx = new SigIntContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				match(Int);
				}
				break;
			case Bool:
				_localctx = new SigBoolContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(76);
				match(Bool);
				}
				break;
			case Box:
				_localctx = new SigBoxContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(77);
				match(Box);
				setState(78);
				match(LT);
				setState(79);
				signature();
				setState(80);
				match(GT);
				}
				break;
			case Trc:
				_localctx = new SigTrcContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(82);
				match(Trc);
				setState(83);
				match(LT);
				setState(84);
				signature();
				setState(85);
				match(GT);
				}
				break;
			case Clone:
				_localctx = new SigCloneContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(87);
				match(Clone);
				setState(88);
				match(LT);
				setState(89);
				signature();
				setState(90);
				match(GT);
				}
				break;
			case Ref:
				_localctx = new SigRefContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(92);
				match(Ref);
				setState(93);
				lif();
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==MUT) {
					{
					setState(94);
					match(MUT);
					}
				}

				setState(97);
				signature();
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

	@SuppressWarnings("CheckReturnValue")
	public static class LifContext extends ParserRuleContext {
		public LifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lif; }
	 
		public LifContext() { }
		public void copyFrom(LifContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LifetimeContext extends LifContext {
		public TerminalNode LIF() { return getToken(msslParser.LIF, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public LifetimeContext(LifContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitLifetime(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LifContext lif() throws RecognitionException {
		LifContext _localctx = new LifContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_lif);
		try {
			_localctx = new LifetimeContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(LIF);
			setState(102);
			match(IDENTIFIER);
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

	@SuppressWarnings("CheckReturnValue")
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
	@SuppressWarnings("CheckReturnValue")
	public static class ExpBlockContext extends ExprContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpBlockContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpBlock(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpCooperateContext extends ExprContext {
		public TerminalNode Cooperate() { return getToken(msslParser.Cooperate, 0); }
		public ExpCooperateContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpCooperate(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpBoxContext extends ExprContext {
		public TerminalNode Box() { return getToken(msslParser.Box, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public ExpBoxContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpBox(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpConditionalsContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OPERATOR() { return getToken(msslParser.OPERATOR, 0); }
		public ExpConditionalsContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpConditionals(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpTrcContext extends ExprContext {
		public TerminalNode Trc() { return getToken(msslParser.Trc, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public ExpTrcContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpTrc(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpInvokeContext extends ExprContext {
		public TerminalNode Spawn() { return getToken(msslParser.Spawn, 0); }
		public List<TerminalNode> LPAR() { return getTokens(msslParser.LPAR); }
		public TerminalNode LPAR(int i) {
			return getToken(msslParser.LPAR, i);
		}
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public List<TerminalNode> RPAR() { return getTokens(msslParser.RPAR); }
		public TerminalNode RPAR(int i) {
			return getToken(msslParser.RPAR, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public SignalsContext signals() {
			return getRuleContext(SignalsContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(msslParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(msslParser.COMMA, i);
		}
		public ExpInvokeContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpInvoke(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpIndexContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Dot() { return getToken(msslParser.Dot, 0); }
		public TerminalNode INTEGER() { return getToken(msslParser.INTEGER, 0); }
		public ExpIndexContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpIndex(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpPrintContext extends ExprContext {
		public TerminalNode PRINT() { return getToken(msslParser.PRINT, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public List<TerminalNode> DoubleQuote() { return getTokens(msslParser.DoubleQuote); }
		public TerminalNode DoubleQuote(int i) {
			return getToken(msslParser.DoubleQuote, i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(msslParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(msslParser.IDENTIFIER, i);
		}
		public List<TerminalNode> PLUS() { return getTokens(msslParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(msslParser.PLUS, i);
		}
		public List<TerminalNode> Mul() { return getTokens(msslParser.Mul); }
		public TerminalNode Mul(int i) {
			return getToken(msslParser.Mul, i);
		}
		public ExpPrintContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpValContext extends ExprContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ExpValContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpVal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpMutableRefContext extends ExprContext {
		public TerminalNode Ref() { return getToken(msslParser.Ref, 0); }
		public TerminalNode MUT() { return getToken(msslParser.MUT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpMutableRefContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpMutableRef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpSharedRefContext extends ExprContext {
		public TerminalNode Ref() { return getToken(msslParser.Ref, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpSharedRefContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpSharedRef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpIdentifierContext extends ExprContext {
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public ExpIdentifierContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpTupleContext extends ExprContext {
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(msslParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(msslParser.COMMA, i);
		}
		public ExpTupleContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpTuple(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpDerefContext extends ExprContext {
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode Mul() { return getToken(msslParser.Mul, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpDerefContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpDeref(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpInvokeOutSpawnContext extends ExprContext {
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public SignalsContext signals() {
			return getRuleContext(SignalsContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(msslParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(msslParser.COMMA, i);
		}
		public ExpInvokeOutSpawnContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpInvokeOutSpawn(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpIFContext extends ExprContext {
		public TerminalNode IF() { return getToken(msslParser.IF, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(msslParser.ELSE, 0); }
		public ExpIFContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpIF(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpCloneContext extends ExprContext {
		public TerminalNode Dot() { return getToken(msslParser.Dot, 0); }
		public TerminalNode Clone() { return getToken(msslParser.Clone, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode Mul() { return getToken(msslParser.Mul, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpCloneContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitExpClone(this);
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
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				_localctx = new ExpValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(105);
				value();
				}
				break;
			case 2:
				{
				_localctx = new ExpIFContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(106);
				match(IF);
				setState(107);
				match(LPAR);
				setState(108);
				expr(0);
				setState(109);
				match(RPAR);
				setState(110);
				block();
				setState(111);
				match(ELSE);
				setState(112);
				block();
				}
				break;
			case 3:
				{
				_localctx = new ExpCloneContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(117);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENTIFIER:
					{
					setState(114);
					match(IDENTIFIER);
					}
					break;
				case Mul:
					{
					setState(115);
					match(Mul);
					setState(116);
					expr(0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(119);
				match(Dot);
				setState(120);
				match(Clone);
				}
				break;
			case 4:
				{
				_localctx = new ExpIdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				match(IDENTIFIER);
				}
				break;
			case 5:
				{
				_localctx = new ExpDerefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(125);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENTIFIER:
					{
					setState(122);
					match(IDENTIFIER);
					}
					break;
				case Mul:
					{
					setState(123);
					match(Mul);
					setState(124);
					expr(0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 6:
				{
				_localctx = new ExpDerefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				match(Mul);
				setState(128);
				expr(12);
				}
				break;
			case 7:
				{
				_localctx = new ExpSharedRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(129);
				match(Ref);
				setState(130);
				expr(11);
				}
				break;
			case 8:
				{
				_localctx = new ExpMutableRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				match(Ref);
				setState(132);
				match(MUT);
				setState(133);
				expr(10);
				}
				break;
			case 9:
				{
				_localctx = new ExpBoxContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134);
				match(Box);
				setState(135);
				match(LPAR);
				setState(136);
				expr(0);
				setState(137);
				match(RPAR);
				}
				break;
			case 10:
				{
				_localctx = new ExpTrcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(139);
				match(Trc);
				setState(140);
				match(LPAR);
				setState(141);
				expr(0);
				setState(142);
				match(RPAR);
				}
				break;
			case 11:
				{
				_localctx = new ExpTupleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(144);
				match(LPAR);
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 1270261748742L) != 0) {
					{
					setState(145);
					expr(0);
					setState(150);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(146);
						match(COMMA);
						setState(147);
						expr(0);
						}
						}
						setState(152);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(155);
				match(RPAR);
				}
				break;
			case 12:
				{
				_localctx = new ExpInvokeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(156);
				match(Spawn);
				setState(157);
				match(LPAR);
				setState(158);
				match(IDENTIFIER);
				setState(159);
				match(LPAR);
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 1270261748742L) != 0) {
					{
					setState(160);
					expr(0);
					setState(165);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(161);
						match(COMMA);
						setState(162);
						expr(0);
						}
						}
						setState(167);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMIC) {
					{
					setState(170);
					signals();
					}
				}

				setState(173);
				match(RPAR);
				setState(174);
				match(RPAR);
				}
				break;
			case 13:
				{
				_localctx = new ExpInvokeOutSpawnContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				match(IDENTIFIER);
				setState(176);
				match(LPAR);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 1270261748742L) != 0) {
					{
					setState(177);
					expr(0);
					setState(182);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(178);
						match(COMMA);
						setState(179);
						expr(0);
						}
						}
						setState(184);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SEMIC) {
					{
					setState(187);
					signals();
					}
				}

				setState(190);
				match(RPAR);
				}
				break;
			case 14:
				{
				_localctx = new ExpCooperateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(191);
				match(Cooperate);
				}
				break;
			case 15:
				{
				_localctx = new ExpBlockContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(192);
				block();
				}
				break;
			case 16:
				{
				_localctx = new ExpPrintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(193);
				match(PRINT);
				setState(194);
				match(LPAR);
				setState(220);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					{
					setState(195);
					match(DoubleQuote);
					setState(196);
					match(IDENTIFIER);
					setState(197);
					match(DoubleQuote);
					}
					}
					break;
				case 2:
					{
					{
					setState(201);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Mul) {
						{
						{
						setState(198);
						match(Mul);
						}
						}
						setState(203);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(204);
					match(IDENTIFIER);
					}
					}
					break;
				case 3:
					{
					{
					{
					setState(205);
					match(DoubleQuote);
					setState(206);
					match(IDENTIFIER);
					setState(207);
					match(DoubleQuote);
					setState(208);
					match(PLUS);
					setState(209);
					match(IDENTIFIER);
					}
					setState(217);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==PLUS) {
						{
						{
						setState(211);
						match(PLUS);
						setState(212);
						match(DoubleQuote);
						setState(213);
						match(IDENTIFIER);
						setState(214);
						match(DoubleQuote);
						}
						}
						setState(219);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					}
					break;
				}
				setState(222);
				match(RPAR);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(233);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(231);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new ExpConditionalsContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(225);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(226);
						match(OPERATOR);
						setState(227);
						expr(7);
						}
						break;
					case 2:
						{
						_localctx = new ExpIndexContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(228);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(229);
						match(Dot);
						setState(230);
						match(INTEGER);
						}
						break;
					}
					} 
				}
				setState(235);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Type_expressionContext extends ParserRuleContext {
		public Type_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_expression; }
	 
		public Type_expressionContext() { }
		public void copyFrom(Type_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypIntContext extends Type_expressionContext {
		public TerminalNode Int() { return getToken(msslParser.Int, 0); }
		public TypIntContext(Type_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitTypInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypBooleanContext extends Type_expressionContext {
		public TerminalNode Bool() { return getToken(msslParser.Bool, 0); }
		public TypBooleanContext(Type_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitTypBoolean(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_expressionContext type_expression() throws RecognitionException {
		Type_expressionContext _localctx = new Type_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type_expression);
		try {
			setState(238);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Int:
				_localctx = new TypIntContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(236);
				match(Int);
				}
				break;
			case Bool:
				_localctx = new TypBooleanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				match(Bool);
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

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	 
		public BlockContext() { }
		public void copyFrom(BlockContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StmtBlockContext extends BlockContext {
		public TerminalNode LBRACE() { return getToken(msslParser.LBRACE, 0); }
		public InstructionsContext instructions() {
			return getRuleContext(InstructionsContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(msslParser.RBRACE, 0); }
		public StmtBlockContext(BlockContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitStmtBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		try {
			_localctx = new StmtBlockContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(LBRACE);
			setState(241);
			instructions();
			setState(242);
			match(RBRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class InstructionsContext extends ParserRuleContext {
		public InstructionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instructions; }
	 
		public InstructionsContext() { }
		public void copyFrom(InstructionsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstSequenceContext extends InstructionsContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public InstSequenceContext(InstructionsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionsContext instructions() throws RecognitionException {
		InstructionsContext _localctx = new InstructionsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_instructions);
		int _la;
		try {
			_localctx = new InstSequenceContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 1270767162374L) != 0) {
				{
				{
				setState(244);
				instruction();
				}
				}
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	@SuppressWarnings("CheckReturnValue")
	public static class InstructionContext extends ParserRuleContext {
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	 
		public InstructionContext() { }
		public void copyFrom(InstructionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstWatchContext extends InstructionContext {
		public TerminalNode Watch() { return getToken(msslParser.Watch, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public InstWatchContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstWatch(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstLetContext extends InstructionContext {
		public DeclVarContext declVar() {
			return getRuleContext(DeclVarContext.class,0);
		}
		public InstLetContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstLet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstAssignmentContext extends InstructionContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(msslParser.EQ, 0); }
		public TerminalNode SEMIC() { return getToken(msslParser.SEMIC, 0); }
		public InstAssignmentContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstWhenContext extends InstructionContext {
		public TerminalNode When() { return getToken(msslParser.When, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public InstWhenContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstWhen(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstEmitContext extends InstructionContext {
		public TerminalNode Emit() { return getToken(msslParser.Emit, 0); }
		public TerminalNode LPAR() { return getToken(msslParser.LPAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode RPAR() { return getToken(msslParser.RPAR, 0); }
		public TerminalNode SEMIC() { return getToken(msslParser.SEMIC, 0); }
		public InstEmitContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstEmit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstSigContext extends InstructionContext {
		public TerminalNode Sig() { return getToken(msslParser.Sig, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode SEMIC() { return getToken(msslParser.SEMIC, 0); }
		public InstSigContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstSig(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstExprContext extends InstructionContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMIC() { return getToken(msslParser.SEMIC, 0); }
		public InstExprContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstBlockContext extends InstructionContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public InstBlockContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInstBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_instruction);
		try {
			setState(278);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				_localctx = new InstLetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(250);
				declVar();
				}
				break;
			case 2:
				_localctx = new InstAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(251);
				expr(0);
				setState(252);
				match(EQ);
				setState(253);
				expr(0);
				setState(254);
				match(SEMIC);
				}
				break;
			case 3:
				_localctx = new InstExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(256);
				expr(0);
				setState(257);
				match(SEMIC);
				}
				break;
			case 4:
				_localctx = new InstEmitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(259);
				match(Emit);
				setState(260);
				match(LPAR);
				setState(261);
				match(IDENTIFIER);
				setState(262);
				match(RPAR);
				setState(263);
				match(SEMIC);
				}
				break;
			case 5:
				_localctx = new InstWhenContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(264);
				match(When);
				setState(265);
				match(LPAR);
				setState(266);
				match(IDENTIFIER);
				setState(267);
				match(RPAR);
				setState(268);
				block();
				}
				break;
			case 6:
				_localctx = new InstWatchContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(269);
				match(Watch);
				setState(270);
				match(LPAR);
				setState(271);
				match(IDENTIFIER);
				setState(272);
				match(RPAR);
				setState(273);
				block();
				}
				break;
			case 7:
				_localctx = new InstSigContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(274);
				match(Sig);
				setState(275);
				match(IDENTIFIER);
				setState(276);
				match(SEMIC);
				}
				break;
			case 8:
				_localctx = new InstBlockContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(277);
				block();
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclVarContext extends ParserRuleContext {
		public TerminalNode LET() { return getToken(msslParser.LET, 0); }
		public TerminalNode MUT() { return getToken(msslParser.MUT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(msslParser.IDENTIFIER, 0); }
		public TerminalNode EQ() { return getToken(msslParser.EQ, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMIC() { return getToken(msslParser.SEMIC, 0); }
		public DeclVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declVar; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitDeclVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclVarContext declVar() throws RecognitionException {
		DeclVarContext _localctx = new DeclVarContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_declVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(LET);
			setState(281);
			match(MUT);
			setState(282);
			match(IDENTIFIER);
			setState(283);
			match(EQ);
			setState(284);
			expr(0);
			setState(285);
			match(SEMIC);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	 
		public ValueContext() { }
		public void copyFrom(ValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BOOLContext extends ValueContext {
		public TerminalNode BOOLEAN() { return getToken(msslParser.BOOLEAN, 0); }
		public BOOLContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitBOOL(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntContext extends ValueContext {
		public TerminalNode INTEGER() { return getToken(msslParser.INTEGER, 0); }
		public IntContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof msslVisitor ) return ((msslVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_value);
		try {
			setState(289);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
				_localctx = new IntContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(287);
				match(INTEGER);
				}
				break;
			case BOOLEAN:
				_localctx = new BOOLContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(288);
				match(BOOLEAN);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 16);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001-\u0124\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0001\u0000\u0005\u0000\u001c\b\u0000\n\u0000\f\u0000\u001f"+
		"\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001(\b\u0001\u0001\u0001\u0003\u0001+\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002=\b\u0002"+
		"\n\u0002\f\u0002@\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u0003F\b\u0003\n\u0003\f\u0003I\t\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004`\b\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"d\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"v\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006~\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006\u0095\b\u0006\n\u0006\f\u0006\u0098\t\u0006\u0003\u0006\u009a"+
		"\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u00a4\b\u0006\n\u0006\f\u0006"+
		"\u00a7\t\u0006\u0003\u0006\u00a9\b\u0006\u0001\u0006\u0003\u0006\u00ac"+
		"\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006\u00b5\b\u0006\n\u0006\f\u0006\u00b8\t\u0006"+
		"\u0003\u0006\u00ba\b\u0006\u0001\u0006\u0003\u0006\u00bd\b\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u00c8\b\u0006\n\u0006\f\u0006"+
		"\u00cb\t\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006\u00d8\b\u0006\n\u0006\f\u0006\u00db\t\u0006\u0003\u0006\u00dd"+
		"\b\u0006\u0001\u0006\u0003\u0006\u00e0\b\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u00e8\b\u0006"+
		"\n\u0006\f\u0006\u00eb\t\u0006\u0001\u0007\u0001\u0007\u0003\u0007\u00ef"+
		"\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0005\t\u00f6\b\t\n\t"+
		"\f\t\u00f9\t\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u0117\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f"+
		"\u0003\f\u0122\b\f\u0001\f\u0000\u0001\f\r\u0000\u0002\u0004\u0006\b\n"+
		"\f\u000e\u0010\u0012\u0014\u0016\u0018\u0000\u0000\u014b\u0000\u001d\u0001"+
		"\u0000\u0000\u0000\u0002#\u0001\u0000\u0000\u0000\u00043\u0001\u0000\u0000"+
		"\u0000\u0006A\u0001\u0000\u0000\u0000\bc\u0001\u0000\u0000\u0000\ne\u0001"+
		"\u0000\u0000\u0000\f\u00df\u0001\u0000\u0000\u0000\u000e\u00ee\u0001\u0000"+
		"\u0000\u0000\u0010\u00f0\u0001\u0000\u0000\u0000\u0012\u00f7\u0001\u0000"+
		"\u0000\u0000\u0014\u0116\u0001\u0000\u0000\u0000\u0016\u0118\u0001\u0000"+
		"\u0000\u0000\u0018\u0121\u0001\u0000\u0000\u0000\u001a\u001c\u0003\u0002"+
		"\u0001\u0000\u001b\u001a\u0001\u0000\u0000\u0000\u001c\u001f\u0001\u0000"+
		"\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000"+
		"\u0000\u0000\u001e \u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000"+
		"\u0000 !\u0003\u0010\b\u0000!\"\u0005\u0000\u0000\u0001\"\u0001\u0001"+
		"\u0000\u0000\u0000#$\u0005\u0013\u0000\u0000$%\u0005(\u0000\u0000%\'\u0005"+
		"\f\u0000\u0000&(\u0003\u0004\u0002\u0000\'&\u0001\u0000\u0000\u0000\'"+
		"(\u0001\u0000\u0000\u0000(*\u0001\u0000\u0000\u0000)+\u0003\u0006\u0003"+
		"\u0000*)\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000\u0000+,\u0001\u0000"+
		"\u0000\u0000,-\u0005\r\u0000\u0000-.\u0005\u0011\u0000\u0000./\u0005\u000f"+
		"\u0000\u0000/0\u0003\u0018\f\u000001\u0003\b\u0004\u000012\u0003\u0010"+
		"\b\u00002\u0003\u0001\u0000\u0000\u000034\u0005\u0016\u0000\u000045\u0005"+
		"(\u0000\u000056\u0005\t\u0000\u00006>\u0003\b\u0004\u000078\u0005\b\u0000"+
		"\u000089\u0005\u0016\u0000\u00009:\u0005(\u0000\u0000:;\u0005\t\u0000"+
		"\u0000;=\u0003\b\u0004\u0000<7\u0001\u0000\u0000\u0000=@\u0001\u0000\u0000"+
		"\u0000><\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?\u0005\u0001"+
		"\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000AB\u0005\u0007\u0000\u0000"+
		"BG\u0005(\u0000\u0000CD\u0005\b\u0000\u0000DF\u0005(\u0000\u0000EC\u0001"+
		"\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000"+
		"GH\u0001\u0000\u0000\u0000H\u0007\u0001\u0000\u0000\u0000IG\u0001\u0000"+
		"\u0000\u0000Jd\u0005\u0005\u0000\u0000Kd\u0005\u0003\u0000\u0000Ld\u0005"+
		"\u0004\u0000\u0000MN\u0005\u0017\u0000\u0000NO\u0005\u000e\u0000\u0000"+
		"OP\u0003\b\u0004\u0000PQ\u0005\u000f\u0000\u0000Qd\u0001\u0000\u0000\u0000"+
		"RS\u0005\u0018\u0000\u0000ST\u0005\u000e\u0000\u0000TU\u0003\b\u0004\u0000"+
		"UV\u0005\u000f\u0000\u0000Vd\u0001\u0000\u0000\u0000WX\u0005\u001d\u0000"+
		"\u0000XY\u0005\u000e\u0000\u0000YZ\u0003\b\u0004\u0000Z[\u0005\u000f\u0000"+
		"\u0000[d\u0001\u0000\u0000\u0000\\]\u0005\"\u0000\u0000]_\u0003\n\u0005"+
		"\u0000^`\u0005\u0016\u0000\u0000_^\u0001\u0000\u0000\u0000_`\u0001\u0000"+
		"\u0000\u0000`a\u0001\u0000\u0000\u0000ab\u0003\b\u0004\u0000bd\u0001\u0000"+
		"\u0000\u0000cJ\u0001\u0000\u0000\u0000cK\u0001\u0000\u0000\u0000cL\u0001"+
		"\u0000\u0000\u0000cM\u0001\u0000\u0000\u0000cR\u0001\u0000\u0000\u0000"+
		"cW\u0001\u0000\u0000\u0000c\\\u0001\u0000\u0000\u0000d\t\u0001\u0000\u0000"+
		"\u0000ef\u0005#\u0000\u0000fg\u0005(\u0000\u0000g\u000b\u0001\u0000\u0000"+
		"\u0000hi\u0006\u0006\uffff\uffff\u0000i\u00e0\u0003\u0018\f\u0000jk\u0005"+
		"%\u0000\u0000kl\u0005\f\u0000\u0000lm\u0003\f\u0006\u0000mn\u0005\r\u0000"+
		"\u0000no\u0003\u0010\b\u0000op\u0005&\u0000\u0000pq\u0003\u0010\b\u0000"+
		"q\u00e0\u0001\u0000\u0000\u0000rv\u0005(\u0000\u0000st\u0005!\u0000\u0000"+
		"tv\u0003\f\u0006\u0000ur\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000"+
		"vw\u0001\u0000\u0000\u0000wx\u0005$\u0000\u0000x\u00e0\u0005\u001d\u0000"+
		"\u0000y\u00e0\u0005(\u0000\u0000z~\u0005(\u0000\u0000{|\u0005!\u0000\u0000"+
		"|~\u0003\f\u0006\u0000}z\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000\u0000"+
		"~\u00e0\u0001\u0000\u0000\u0000\u007f\u0080\u0005!\u0000\u0000\u0080\u00e0"+
		"\u0003\f\u0006\f\u0081\u0082\u0005\"\u0000\u0000\u0082\u00e0\u0003\f\u0006"+
		"\u000b\u0083\u0084\u0005\"\u0000\u0000\u0084\u0085\u0005\u0016\u0000\u0000"+
		"\u0085\u00e0\u0003\f\u0006\n\u0086\u0087\u0005\u0017\u0000\u0000\u0087"+
		"\u0088\u0005\f\u0000\u0000\u0088\u0089\u0003\f\u0006\u0000\u0089\u008a"+
		"\u0005\r\u0000\u0000\u008a\u00e0\u0001\u0000\u0000\u0000\u008b\u008c\u0005"+
		"\u0018\u0000\u0000\u008c\u008d\u0005\f\u0000\u0000\u008d\u008e\u0003\f"+
		"\u0006\u0000\u008e\u008f\u0005\r\u0000\u0000\u008f\u00e0\u0001\u0000\u0000"+
		"\u0000\u0090\u0099\u0005\f\u0000\u0000\u0091\u0096\u0003\f\u0006\u0000"+
		"\u0092\u0093\u0005\b\u0000\u0000\u0093\u0095\u0003\f\u0006\u0000\u0094"+
		"\u0092\u0001\u0000\u0000\u0000\u0095\u0098\u0001\u0000\u0000\u0000\u0096"+
		"\u0094\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097"+
		"\u009a\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0099"+
		"\u0091\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a"+
		"\u009b\u0001\u0000\u0000\u0000\u009b\u00e0\u0005\r\u0000\u0000\u009c\u009d"+
		"\u0005\u001e\u0000\u0000\u009d\u009e\u0005\f\u0000\u0000\u009e\u009f\u0005"+
		"(\u0000\u0000\u009f\u00a8\u0005\f\u0000\u0000\u00a0\u00a5\u0003\f\u0006"+
		"\u0000\u00a1\u00a2\u0005\b\u0000\u0000\u00a2\u00a4\u0003\f\u0006\u0000"+
		"\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a4\u00a7\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a3\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a0\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000"+
		"\u00a9\u00ab\u0001\u0000\u0000\u0000\u00aa\u00ac\u0003\u0006\u0003\u0000"+
		"\u00ab\u00aa\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005\r\u0000\u0000\u00ae"+
		"\u00e0\u0005\r\u0000\u0000\u00af\u00b0\u0005(\u0000\u0000\u00b0\u00b9"+
		"\u0005\f\u0000\u0000\u00b1\u00b6\u0003\f\u0006\u0000\u00b2\u00b3\u0005"+
		"\b\u0000\u0000\u00b3\u00b5\u0003\f\u0006\u0000\u00b4\u00b2\u0001\u0000"+
		"\u0000\u0000\u00b5\u00b8\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7\u00ba\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000\u0000\u00b9\u00b1\u0001\u0000"+
		"\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00bc\u0001\u0000"+
		"\u0000\u0000\u00bb\u00bd\u0003\u0006\u0003\u0000\u00bc\u00bb\u0001\u0000"+
		"\u0000\u0000\u00bc\u00bd\u0001\u0000\u0000\u0000\u00bd\u00be\u0001\u0000"+
		"\u0000\u0000\u00be\u00e0\u0005\r\u0000\u0000\u00bf\u00e0\u0005\u001f\u0000"+
		"\u0000\u00c0\u00e0\u0003\u0010\b\u0000\u00c1\u00c2\u0005 \u0000\u0000"+
		"\u00c2\u00dc\u0005\f\u0000\u0000\u00c3\u00c4\u0005\u0010\u0000\u0000\u00c4"+
		"\u00c5\u0005(\u0000\u0000\u00c5\u00dd\u0005\u0010\u0000\u0000\u00c6\u00c8"+
		"\u0005!\u0000\u0000\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c8\u00cb\u0001"+
		"\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cc\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001"+
		"\u0000\u0000\u0000\u00cc\u00dd\u0005(\u0000\u0000\u00cd\u00ce\u0005\u0010"+
		"\u0000\u0000\u00ce\u00cf\u0005(\u0000\u0000\u00cf\u00d0\u0005\u0010\u0000"+
		"\u0000\u00d0\u00d1\u0005\u0012\u0000\u0000\u00d1\u00d2\u0005(\u0000\u0000"+
		"\u00d2\u00d9\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005\u0012\u0000\u0000"+
		"\u00d4\u00d5\u0005\u0010\u0000\u0000\u00d5\u00d6\u0005(\u0000\u0000\u00d6"+
		"\u00d8\u0005\u0010\u0000\u0000\u00d7\u00d3\u0001\u0000\u0000\u0000\u00d8"+
		"\u00db\u0001\u0000\u0000\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00d9"+
		"\u00da\u0001\u0000\u0000\u0000\u00da\u00dd\u0001\u0000\u0000\u0000\u00db"+
		"\u00d9\u0001\u0000\u0000\u0000\u00dc\u00c3\u0001\u0000\u0000\u0000\u00dc"+
		"\u00c9\u0001\u0000\u0000\u0000\u00dc\u00cd\u0001\u0000\u0000\u0000\u00dd"+
		"\u00de\u0001\u0000\u0000\u0000\u00de\u00e0\u0005\r\u0000\u0000\u00dfh"+
		"\u0001\u0000\u0000\u0000\u00dfj\u0001\u0000\u0000\u0000\u00dfu\u0001\u0000"+
		"\u0000\u0000\u00dfy\u0001\u0000\u0000\u0000\u00df}\u0001\u0000\u0000\u0000"+
		"\u00df\u007f\u0001\u0000\u0000\u0000\u00df\u0081\u0001\u0000\u0000\u0000"+
		"\u00df\u0083\u0001\u0000\u0000\u0000\u00df\u0086\u0001\u0000\u0000\u0000"+
		"\u00df\u008b\u0001\u0000\u0000\u0000\u00df\u0090\u0001\u0000\u0000\u0000"+
		"\u00df\u009c\u0001\u0000\u0000\u0000\u00df\u00af\u0001\u0000\u0000\u0000"+
		"\u00df\u00bf\u0001\u0000\u0000\u0000\u00df\u00c0\u0001\u0000\u0000\u0000"+
		"\u00df\u00c1\u0001\u0000\u0000\u0000\u00e0\u00e9\u0001\u0000\u0000\u0000"+
		"\u00e1\u00e2\n\u0006\u0000\u0000\u00e2\u00e3\u0005\'\u0000\u0000\u00e3"+
		"\u00e8\u0003\f\u0006\u0007\u00e4\u00e5\n\u0010\u0000\u0000\u00e5\u00e6"+
		"\u0005$\u0000\u0000\u00e6\u00e8\u0005\u0001\u0000\u0000\u00e7\u00e1\u0001"+
		"\u0000\u0000\u0000\u00e7\u00e4\u0001\u0000\u0000\u0000\u00e8\u00eb\u0001"+
		"\u0000\u0000\u0000\u00e9\u00e7\u0001\u0000\u0000\u0000\u00e9\u00ea\u0001"+
		"\u0000\u0000\u0000\u00ea\r\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001\u0000"+
		"\u0000\u0000\u00ec\u00ef\u0005\u0003\u0000\u0000\u00ed\u00ef\u0005\u0004"+
		"\u0000\u0000\u00ee\u00ec\u0001\u0000\u0000\u0000\u00ee\u00ed\u0001\u0000"+
		"\u0000\u0000\u00ef\u000f\u0001\u0000\u0000\u0000\u00f0\u00f1\u0005\n\u0000"+
		"\u0000\u00f1\u00f2\u0003\u0012\t\u0000\u00f2\u00f3\u0005\u000b\u0000\u0000"+
		"\u00f3\u0011\u0001\u0000\u0000\u0000\u00f4\u00f6\u0003\u0014\n\u0000\u00f5"+
		"\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f9\u0001\u0000\u0000\u0000\u00f7"+
		"\u00f5\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8"+
		"\u0013\u0001\u0000\u0000\u0000\u00f9\u00f7\u0001\u0000\u0000\u0000\u00fa"+
		"\u0117\u0003\u0016\u000b\u0000\u00fb\u00fc\u0003\f\u0006\u0000\u00fc\u00fd"+
		"\u0005\u0006\u0000\u0000\u00fd\u00fe\u0003\f\u0006\u0000\u00fe\u00ff\u0005"+
		"\u0007\u0000\u0000\u00ff\u0117\u0001\u0000\u0000\u0000\u0100\u0101\u0003"+
		"\f\u0006\u0000\u0101\u0102\u0005\u0007\u0000\u0000\u0102\u0117\u0001\u0000"+
		"\u0000\u0000\u0103\u0104\u0005\u001a\u0000\u0000\u0104\u0105\u0005\f\u0000"+
		"\u0000\u0105\u0106\u0005(\u0000\u0000\u0106\u0107\u0005\r\u0000\u0000"+
		"\u0107\u0117\u0005\u0007\u0000\u0000\u0108\u0109\u0005\u001b\u0000\u0000"+
		"\u0109\u010a\u0005\f\u0000\u0000\u010a\u010b\u0005(\u0000\u0000\u010b"+
		"\u010c\u0005\r\u0000\u0000\u010c\u0117\u0003\u0010\b\u0000\u010d\u010e"+
		"\u0005\u001c\u0000\u0000\u010e\u010f\u0005\f\u0000\u0000\u010f\u0110\u0005"+
		"(\u0000\u0000\u0110\u0111\u0005\r\u0000\u0000\u0111\u0117\u0003\u0010"+
		"\b\u0000\u0112\u0113\u0005\u0019\u0000\u0000\u0113\u0114\u0005(\u0000"+
		"\u0000\u0114\u0117\u0005\u0007\u0000\u0000\u0115\u0117\u0003\u0010\b\u0000"+
		"\u0116\u00fa\u0001\u0000\u0000\u0000\u0116\u00fb\u0001\u0000\u0000\u0000"+
		"\u0116\u0100\u0001\u0000\u0000\u0000\u0116\u0103\u0001\u0000\u0000\u0000"+
		"\u0116\u0108\u0001\u0000\u0000\u0000\u0116\u010d\u0001\u0000\u0000\u0000"+
		"\u0116\u0112\u0001\u0000\u0000\u0000\u0116\u0115\u0001\u0000\u0000\u0000"+
		"\u0117\u0015\u0001\u0000\u0000\u0000\u0118\u0119\u0005\u0015\u0000\u0000"+
		"\u0119\u011a\u0005\u0016\u0000\u0000\u011a\u011b\u0005(\u0000\u0000\u011b"+
		"\u011c\u0005\u0006\u0000\u0000\u011c\u011d\u0003\f\u0006\u0000\u011d\u011e"+
		"\u0005\u0007\u0000\u0000\u011e\u0017\u0001\u0000\u0000\u0000\u011f\u0122"+
		"\u0005\u0001\u0000\u0000\u0120\u0122\u0005\u0002\u0000\u0000\u0121\u011f"+
		"\u0001\u0000\u0000\u0000\u0121\u0120\u0001\u0000\u0000\u0000\u0122\u0019"+
		"\u0001\u0000\u0000\u0000\u001b\u001d\'*>G_cu}\u0096\u0099\u00a5\u00a8"+
		"\u00ab\u00b6\u00b9\u00bc\u00c9\u00d9\u00dc\u00df\u00e7\u00e9\u00ee\u00f7"+
		"\u0116\u0121";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}