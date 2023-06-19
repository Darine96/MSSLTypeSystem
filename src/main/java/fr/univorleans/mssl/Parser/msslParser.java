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
		Sub=17, PLUS=18, FN=19, MAIN=20, LET=21, MUT=22, Box=23, Trc=24, Clone=25, 
		Spawn=26, Cooperate=27, PRINT=28, Mul=29, Ref=30, Dot=31, IDENTIFIER=32, 
		VALID_ID_START=33, VALID_ID_CHAR=34, WS=35, OC_COMMENT=36, SL_COMMENT=37;
	public static final int
		RULE_program = 0, RULE_declaration = 1, RULE_params = 2, RULE_signature = 3, 
		RULE_expr = 4, RULE_type_expression = 5, RULE_block = 6, RULE_instructions = 7, 
		RULE_instruction = 8, RULE_declVar = 9, RULE_value = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declaration", "params", "signature", "expr", "type_expression", 
			"block", "instructions", "instruction", "declVar", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'int'", "'bool'", "'unit'", "'='", "';'", "','", "':'", 
			"'{'", "'}'", "'('", "')'", "'<'", "'>'", "'\"'", "'-'", "'+'", "'fn'", 
			"'main'", "'let'", "'mut'", "'box'", "'trc'", "'clone'", "'spawn'", "'cooperate'", 
			"'print!'", "'*'", "'&'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INTEGER", "BOOLEAN", "Int", "Bool", "Unit", "EQ", "SEMIC", "COMMA", 
			"COLON", "LBRACE", "RBRACE", "LPAR", "RPAR", "LT", "GT", "DoubleQuote", 
			"Sub", "PLUS", "FN", "MAIN", "LET", "MUT", "Box", "Trc", "Clone", "Spawn", 
			"Cooperate", "PRINT", "Mul", "Ref", "Dot", "IDENTIFIER", "VALID_ID_START", 
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
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FN) {
				{
				{
				setState(22);
				declaration();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(28);
			block();
			setState(29);
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
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
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
			setState(31);
			match(FN);
			setState(32);
			match(IDENTIFIER);
			setState(33);
			match(LPAR);
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==MUT) {
				{
				setState(34);
				params();
				}
			}

			setState(37);
			match(RPAR);
			setState(38);
			match(Sub);
			setState(39);
			match(GT);
			setState(40);
			signature();
			setState(41);
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
			setState(43);
			match(MUT);
			setState(44);
			match(IDENTIFIER);
			setState(45);
			match(COLON);
			setState(46);
			signature();
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(47);
				match(COMMA);
				setState(48);
				match(MUT);
				setState(49);
				match(IDENTIFIER);
				setState(50);
				match(COLON);
				setState(51);
				signature();
				}
				}
				setState(56);
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
		enterRule(_localctx, 6, RULE_signature);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Unit:
				_localctx = new SigUnitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				match(Unit);
				}
				break;
			case Int:
				_localctx = new SigIntContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				match(Int);
				}
				break;
			case Bool:
				_localctx = new SigBoolContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(59);
				match(Bool);
				}
				break;
			case Box:
				_localctx = new SigBoxContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(60);
				match(Box);
				setState(61);
				match(LT);
				setState(62);
				signature();
				setState(63);
				match(GT);
				}
				break;
			case Trc:
				_localctx = new SigTrcContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(65);
				match(Trc);
				setState(66);
				match(LT);
				setState(67);
				signature();
				setState(68);
				match(GT);
				}
				break;
			case Clone:
				_localctx = new SigCloneContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(70);
				match(Clone);
				setState(71);
				match(LT);
				setState(72);
				signature();
				setState(73);
				match(GT);
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
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				_localctx = new ExpValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(78);
				value();
				}
				break;
			case 2:
				{
				_localctx = new ExpCloneContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(82);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENTIFIER:
					{
					setState(79);
					match(IDENTIFIER);
					}
					break;
				case Mul:
					{
					setState(80);
					match(Mul);
					setState(81);
					expr(0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(84);
				match(Dot);
				setState(85);
				match(Clone);
				}
				break;
			case 3:
				{
				_localctx = new ExpDerefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(89);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IDENTIFIER:
					{
					setState(86);
					match(IDENTIFIER);
					}
					break;
				case Mul:
					{
					setState(87);
					match(Mul);
					setState(88);
					expr(0);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 4:
				{
				_localctx = new ExpSharedRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91);
				match(Ref);
				setState(92);
				expr(9);
				}
				break;
			case 5:
				{
				_localctx = new ExpMutableRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93);
				match(Ref);
				setState(94);
				match(MUT);
				setState(95);
				expr(8);
				}
				break;
			case 6:
				{
				_localctx = new ExpBoxContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(96);
				match(Box);
				setState(97);
				match(LPAR);
				setState(98);
				expr(0);
				setState(99);
				match(RPAR);
				}
				break;
			case 7:
				{
				_localctx = new ExpTrcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(101);
				match(Trc);
				setState(102);
				match(LPAR);
				setState(103);
				expr(0);
				setState(104);
				match(RPAR);
				}
				break;
			case 8:
				{
				_localctx = new ExpTupleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(106);
				match(LPAR);
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 6400513030L) != 0) {
					{
					setState(107);
					expr(0);
					setState(112);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(108);
						match(COMMA);
						setState(109);
						expr(0);
						}
						}
						setState(114);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(117);
				match(RPAR);
				}
				break;
			case 9:
				{
				_localctx = new ExpInvokeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(118);
				match(Spawn);
				setState(119);
				match(LPAR);
				setState(120);
				match(IDENTIFIER);
				setState(121);
				match(LPAR);
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((_la) & ~0x3f) == 0 && ((1L << _la) & 6400513030L) != 0) {
					{
					setState(122);
					expr(0);
					setState(127);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(123);
						match(COMMA);
						setState(124);
						expr(0);
						}
						}
						setState(129);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(132);
				match(RPAR);
				setState(133);
				match(RPAR);
				}
				break;
			case 10:
				{
				_localctx = new ExpCooperateContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134);
				match(Cooperate);
				}
				break;
			case 11:
				{
				_localctx = new ExpBlockContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				block();
				}
				break;
			case 12:
				{
				_localctx = new ExpPrintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(136);
				match(PRINT);
				setState(137);
				match(LPAR);
				setState(163);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					{
					setState(138);
					match(DoubleQuote);
					setState(139);
					match(IDENTIFIER);
					setState(140);
					match(DoubleQuote);
					}
					}
					break;
				case 2:
					{
					{
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==Mul) {
						{
						{
						setState(141);
						match(Mul);
						}
						}
						setState(146);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(147);
					match(IDENTIFIER);
					}
					}
					break;
				case 3:
					{
					{
					{
					setState(148);
					match(DoubleQuote);
					setState(149);
					match(IDENTIFIER);
					setState(150);
					match(DoubleQuote);
					setState(151);
					match(PLUS);
					setState(152);
					match(IDENTIFIER);
					}
					setState(160);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==PLUS) {
						{
						{
						setState(154);
						match(PLUS);
						setState(155);
						match(DoubleQuote);
						setState(156);
						match(IDENTIFIER);
						setState(157);
						match(DoubleQuote);
						}
						}
						setState(162);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					}
					break;
				}
				setState(165);
				match(RPAR);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(173);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExpIndexContext(new ExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(168);
					if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
					setState(169);
					match(Dot);
					setState(170);
					match(INTEGER);
					}
					} 
				}
				setState(175);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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
		enterRule(_localctx, 10, RULE_type_expression);
		try {
			setState(178);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Int:
				_localctx = new TypIntContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				match(Int);
				}
				break;
			case Bool:
				_localctx = new TypBooleanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
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
		enterRule(_localctx, 12, RULE_block);
		try {
			_localctx = new StmtBlockContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(LBRACE);
			setState(181);
			instructions();
			setState(182);
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
		enterRule(_localctx, 14, RULE_instructions);
		int _la;
		try {
			_localctx = new InstSequenceContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 6402610182L) != 0) {
				{
				{
				setState(184);
				instruction();
				}
				}
				setState(189);
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
		enterRule(_localctx, 16, RULE_instruction);
		try {
			setState(200);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new InstLetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(190);
				declVar();
				}
				break;
			case 2:
				_localctx = new InstAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(191);
				expr(0);
				setState(192);
				match(EQ);
				setState(193);
				expr(0);
				setState(194);
				match(SEMIC);
				}
				break;
			case 3:
				_localctx = new InstExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(196);
				expr(0);
				setState(197);
				match(SEMIC);
				}
				break;
			case 4:
				_localctx = new InstBlockContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(199);
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
		enterRule(_localctx, 18, RULE_declVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(LET);
			setState(203);
			match(MUT);
			setState(204);
			match(IDENTIFIER);
			setState(205);
			match(EQ);
			setState(206);
			expr(0);
			setState(207);
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
		enterRule(_localctx, 20, RULE_value);
		try {
			setState(211);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
				_localctx = new IntContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(209);
				match(INTEGER);
				}
				break;
			case BOOLEAN:
				_localctx = new BOOLContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(210);
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
		case 4:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001%\u00d6\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0005\u0000\u0018"+
		"\b\u0000\n\u0000\f\u0000\u001b\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001$\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u00025\b\u0002\n\u0002\f\u0002"+
		"8\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003L\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004S\b\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004Z\b\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0005\u0004o\b\u0004\n\u0004\f\u0004r\t\u0004\u0003\u0004t\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0005\u0004~\b\u0004\n\u0004\f\u0004\u0081\t\u0004"+
		"\u0003\u0004\u0083\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0005\u0004\u008f\b\u0004\n\u0004\f\u0004\u0092\t\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u009f\b\u0004\n"+
		"\u0004\f\u0004\u00a2\t\u0004\u0003\u0004\u00a4\b\u0004\u0001\u0004\u0003"+
		"\u0004\u00a7\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u00ac"+
		"\b\u0004\n\u0004\f\u0004\u00af\t\u0004\u0001\u0005\u0001\u0005\u0003\u0005"+
		"\u00b3\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0005\u0007\u00ba\b\u0007\n\u0007\f\u0007\u00bd\t\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003"+
		"\b\u00c9\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0003\n\u00d4\b\n\u0001\n\u0000\u0001\b\u000b\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0000\u0000\u00ee\u0000\u0019\u0001"+
		"\u0000\u0000\u0000\u0002\u001f\u0001\u0000\u0000\u0000\u0004+\u0001\u0000"+
		"\u0000\u0000\u0006K\u0001\u0000\u0000\u0000\b\u00a6\u0001\u0000\u0000"+
		"\u0000\n\u00b2\u0001\u0000\u0000\u0000\f\u00b4\u0001\u0000\u0000\u0000"+
		"\u000e\u00bb\u0001\u0000\u0000\u0000\u0010\u00c8\u0001\u0000\u0000\u0000"+
		"\u0012\u00ca\u0001\u0000\u0000\u0000\u0014\u00d3\u0001\u0000\u0000\u0000"+
		"\u0016\u0018\u0003\u0002\u0001\u0000\u0017\u0016\u0001\u0000\u0000\u0000"+
		"\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000"+
		"\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u001c\u0001\u0000\u0000\u0000"+
		"\u001b\u0019\u0001\u0000\u0000\u0000\u001c\u001d\u0003\f\u0006\u0000\u001d"+
		"\u001e\u0005\u0000\u0000\u0001\u001e\u0001\u0001\u0000\u0000\u0000\u001f"+
		" \u0005\u0013\u0000\u0000 !\u0005 \u0000\u0000!#\u0005\f\u0000\u0000\""+
		"$\u0003\u0004\u0002\u0000#\"\u0001\u0000\u0000\u0000#$\u0001\u0000\u0000"+
		"\u0000$%\u0001\u0000\u0000\u0000%&\u0005\r\u0000\u0000&\'\u0005\u0011"+
		"\u0000\u0000\'(\u0005\u000f\u0000\u0000()\u0003\u0006\u0003\u0000)*\u0003"+
		"\f\u0006\u0000*\u0003\u0001\u0000\u0000\u0000+,\u0005\u0016\u0000\u0000"+
		",-\u0005 \u0000\u0000-.\u0005\t\u0000\u0000.6\u0003\u0006\u0003\u0000"+
		"/0\u0005\b\u0000\u000001\u0005\u0016\u0000\u000012\u0005 \u0000\u0000"+
		"23\u0005\t\u0000\u000035\u0003\u0006\u0003\u00004/\u0001\u0000\u0000\u0000"+
		"58\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000067\u0001\u0000\u0000"+
		"\u00007\u0005\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u00009L\u0005"+
		"\u0005\u0000\u0000:L\u0005\u0003\u0000\u0000;L\u0005\u0004\u0000\u0000"+
		"<=\u0005\u0017\u0000\u0000=>\u0005\u000e\u0000\u0000>?\u0003\u0006\u0003"+
		"\u0000?@\u0005\u000f\u0000\u0000@L\u0001\u0000\u0000\u0000AB\u0005\u0018"+
		"\u0000\u0000BC\u0005\u000e\u0000\u0000CD\u0003\u0006\u0003\u0000DE\u0005"+
		"\u000f\u0000\u0000EL\u0001\u0000\u0000\u0000FG\u0005\u0019\u0000\u0000"+
		"GH\u0005\u000e\u0000\u0000HI\u0003\u0006\u0003\u0000IJ\u0005\u000f\u0000"+
		"\u0000JL\u0001\u0000\u0000\u0000K9\u0001\u0000\u0000\u0000K:\u0001\u0000"+
		"\u0000\u0000K;\u0001\u0000\u0000\u0000K<\u0001\u0000\u0000\u0000KA\u0001"+
		"\u0000\u0000\u0000KF\u0001\u0000\u0000\u0000L\u0007\u0001\u0000\u0000"+
		"\u0000MN\u0006\u0004\uffff\uffff\u0000N\u00a7\u0003\u0014\n\u0000OS\u0005"+
		" \u0000\u0000PQ\u0005\u001d\u0000\u0000QS\u0003\b\u0004\u0000RO\u0001"+
		"\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000"+
		"TU\u0005\u001f\u0000\u0000U\u00a7\u0005\u0019\u0000\u0000VZ\u0005 \u0000"+
		"\u0000WX\u0005\u001d\u0000\u0000XZ\u0003\b\u0004\u0000YV\u0001\u0000\u0000"+
		"\u0000YW\u0001\u0000\u0000\u0000Z\u00a7\u0001\u0000\u0000\u0000[\\\u0005"+
		"\u001e\u0000\u0000\\\u00a7\u0003\b\u0004\t]^\u0005\u001e\u0000\u0000^"+
		"_\u0005\u0016\u0000\u0000_\u00a7\u0003\b\u0004\b`a\u0005\u0017\u0000\u0000"+
		"ab\u0005\f\u0000\u0000bc\u0003\b\u0004\u0000cd\u0005\r\u0000\u0000d\u00a7"+
		"\u0001\u0000\u0000\u0000ef\u0005\u0018\u0000\u0000fg\u0005\f\u0000\u0000"+
		"gh\u0003\b\u0004\u0000hi\u0005\r\u0000\u0000i\u00a7\u0001\u0000\u0000"+
		"\u0000js\u0005\f\u0000\u0000kp\u0003\b\u0004\u0000lm\u0005\b\u0000\u0000"+
		"mo\u0003\b\u0004\u0000nl\u0001\u0000\u0000\u0000or\u0001\u0000\u0000\u0000"+
		"pn\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000"+
		"\u0000rp\u0001\u0000\u0000\u0000sk\u0001\u0000\u0000\u0000st\u0001\u0000"+
		"\u0000\u0000tu\u0001\u0000\u0000\u0000u\u00a7\u0005\r\u0000\u0000vw\u0005"+
		"\u001a\u0000\u0000wx\u0005\f\u0000\u0000xy\u0005 \u0000\u0000y\u0082\u0005"+
		"\f\u0000\u0000z\u007f\u0003\b\u0004\u0000{|\u0005\b\u0000\u0000|~\u0003"+
		"\b\u0004\u0000}{\u0001\u0000\u0000\u0000~\u0081\u0001\u0000\u0000\u0000"+
		"\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080"+
		"\u0083\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0082"+
		"z\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0084"+
		"\u0001\u0000\u0000\u0000\u0084\u0085\u0005\r\u0000\u0000\u0085\u00a7\u0005"+
		"\r\u0000\u0000\u0086\u00a7\u0005\u001b\u0000\u0000\u0087\u00a7\u0003\f"+
		"\u0006\u0000\u0088\u0089\u0005\u001c\u0000\u0000\u0089\u00a3\u0005\f\u0000"+
		"\u0000\u008a\u008b\u0005\u0010\u0000\u0000\u008b\u008c\u0005 \u0000\u0000"+
		"\u008c\u00a4\u0005\u0010\u0000\u0000\u008d\u008f\u0005\u001d\u0000\u0000"+
		"\u008e\u008d\u0001\u0000\u0000\u0000\u008f\u0092\u0001\u0000\u0000\u0000"+
		"\u0090\u008e\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000"+
		"\u0091\u0093\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000"+
		"\u0093\u00a4\u0005 \u0000\u0000\u0094\u0095\u0005\u0010\u0000\u0000\u0095"+
		"\u0096\u0005 \u0000\u0000\u0096\u0097\u0005\u0010\u0000\u0000\u0097\u0098"+
		"\u0005\u0012\u0000\u0000\u0098\u0099\u0005 \u0000\u0000\u0099\u00a0\u0001"+
		"\u0000\u0000\u0000\u009a\u009b\u0005\u0012\u0000\u0000\u009b\u009c\u0005"+
		"\u0010\u0000\u0000\u009c\u009d\u0005 \u0000\u0000\u009d\u009f\u0005\u0010"+
		"\u0000\u0000\u009e\u009a\u0001\u0000\u0000\u0000\u009f\u00a2\u0001\u0000"+
		"\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a1\u00a4\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000"+
		"\u0000\u0000\u00a3\u008a\u0001\u0000\u0000\u0000\u00a3\u0090\u0001\u0000"+
		"\u0000\u0000\u00a3\u0094\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a7\u0005\r\u0000\u0000\u00a6M\u0001\u0000\u0000"+
		"\u0000\u00a6R\u0001\u0000\u0000\u0000\u00a6Y\u0001\u0000\u0000\u0000\u00a6"+
		"[\u0001\u0000\u0000\u0000\u00a6]\u0001\u0000\u0000\u0000\u00a6`\u0001"+
		"\u0000\u0000\u0000\u00a6e\u0001\u0000\u0000\u0000\u00a6j\u0001\u0000\u0000"+
		"\u0000\u00a6v\u0001\u0000\u0000\u0000\u00a6\u0086\u0001\u0000\u0000\u0000"+
		"\u00a6\u0087\u0001\u0000\u0000\u0000\u00a6\u0088\u0001\u0000\u0000\u0000"+
		"\u00a7\u00ad\u0001\u0000\u0000\u0000\u00a8\u00a9\n\f\u0000\u0000\u00a9"+
		"\u00aa\u0005\u001f\u0000\u0000\u00aa\u00ac\u0005\u0001\u0000\u0000\u00ab"+
		"\u00a8\u0001\u0000\u0000\u0000\u00ac\u00af\u0001\u0000\u0000\u0000\u00ad"+
		"\u00ab\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae"+
		"\t\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0\u00b3"+
		"\u0005\u0003\u0000\u0000\u00b1\u00b3\u0005\u0004\u0000\u0000\u00b2\u00b0"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b1\u0001\u0000\u0000\u0000\u00b3\u000b"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b5\u0005\n\u0000\u0000\u00b5\u00b6\u0003"+
		"\u000e\u0007\u0000\u00b6\u00b7\u0005\u000b\u0000\u0000\u00b7\r\u0001\u0000"+
		"\u0000\u0000\u00b8\u00ba\u0003\u0010\b\u0000\u00b9\u00b8\u0001\u0000\u0000"+
		"\u0000\u00ba\u00bd\u0001\u0000\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000"+
		"\u0000\u00bb\u00bc\u0001\u0000\u0000\u0000\u00bc\u000f\u0001\u0000\u0000"+
		"\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00be\u00c9\u0003\u0012\t\u0000"+
		"\u00bf\u00c0\u0003\b\u0004\u0000\u00c0\u00c1\u0005\u0006\u0000\u0000\u00c1"+
		"\u00c2\u0003\b\u0004\u0000\u00c2\u00c3\u0005\u0007\u0000\u0000\u00c3\u00c9"+
		"\u0001\u0000\u0000\u0000\u00c4\u00c5\u0003\b\u0004\u0000\u00c5\u00c6\u0005"+
		"\u0007\u0000\u0000\u00c6\u00c9\u0001\u0000\u0000\u0000\u00c7\u00c9\u0003"+
		"\f\u0006\u0000\u00c8\u00be\u0001\u0000\u0000\u0000\u00c8\u00bf\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c4\u0001\u0000\u0000\u0000\u00c8\u00c7\u0001\u0000"+
		"\u0000\u0000\u00c9\u0011\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005\u0015"+
		"\u0000\u0000\u00cb\u00cc\u0005\u0016\u0000\u0000\u00cc\u00cd\u0005 \u0000"+
		"\u0000\u00cd\u00ce\u0005\u0006\u0000\u0000\u00ce\u00cf\u0003\b\u0004\u0000"+
		"\u00cf\u00d0\u0005\u0007\u0000\u0000\u00d0\u0013\u0001\u0000\u0000\u0000"+
		"\u00d1\u00d4\u0005\u0001\u0000\u0000\u00d2\u00d4\u0005\u0002\u0000\u0000"+
		"\u00d3\u00d1\u0001\u0000\u0000\u0000\u00d3\u00d2\u0001\u0000\u0000\u0000"+
		"\u00d4\u0015\u0001\u0000\u0000\u0000\u0013\u0019#6KRYps\u007f\u0082\u0090"+
		"\u00a0\u00a3\u00a6\u00ad\u00b2\u00bb\u00c8\u00d3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}