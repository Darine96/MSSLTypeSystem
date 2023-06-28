// Generated from java-escape by ANTLR 4.11.1
package fr.univorleans.mssl.Parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class msslLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INTEGER=1, BOOLEAN=2, Int=3, Bool=4, Unit=5, EQ=6, SEMIC=7, COMMA=8, COLON=9, 
		LBRACE=10, RBRACE=11, LPAR=12, RPAR=13, LT=14, GT=15, DoubleQuote=16, 
		Sub=17, PLUS=18, FN=19, MAIN=20, LET=21, MUT=22, Box=23, Trc=24, Sig=25, 
		Emit=26, When=27, Watch=28, Clone=29, Spawn=30, Cooperate=31, PRINT=32, 
		Mul=33, Ref=34, Dot=35, IF=36, ELSE=37, OPERATOR=38, IDENTIFIER=39, VALID_ID_START=40, 
		VALID_ID_CHAR=41, WS=42, OC_COMMENT=43, SL_COMMENT=44;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INTEGER", "BOOLEAN", "Int", "Bool", "Unit", "EQ", "SEMIC", "COMMA", 
			"COLON", "LBRACE", "RBRACE", "LPAR", "RPAR", "LT", "GT", "DoubleQuote", 
			"Sub", "PLUS", "FN", "MAIN", "LET", "MUT", "Box", "Trc", "Sig", "Emit", 
			"When", "Watch", "Clone", "Spawn", "Cooperate", "PRINT", "Mul", "Ref", 
			"Dot", "IF", "ELSE", "OPERATOR", "IDENTIFIER", "VALID_ID_START", "VALID_ID_CHAR", 
			"WS", "OC_COMMENT", "SL_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'int'", "'bool'", "'unit'", "'='", "';'", "','", "':'", 
			"'{'", "'}'", "'('", "')'", "'<'", "'>'", "'\"'", "'-'", "'+'", "'fn'", 
			"'main'", "'let'", "'mut'", "'box'", "'trc'", "'Sig'", "'emit'", "'when'", 
			"'watch'", "'clone'", "'spawn'", "'cooperate'", "'print!'", "'*'", "'&'", 
			"'.'", "'if'", "'else'", "'=='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INTEGER", "BOOLEAN", "Int", "Bool", "Unit", "EQ", "SEMIC", "COMMA", 
			"COLON", "LBRACE", "RBRACE", "LPAR", "RPAR", "LT", "GT", "DoubleQuote", 
			"Sub", "PLUS", "FN", "MAIN", "LET", "MUT", "Box", "Trc", "Sig", "Emit", 
			"When", "Watch", "Clone", "Spawn", "Cooperate", "PRINT", "Mul", "Ref", 
			"Dot", "IF", "ELSE", "OPERATOR", "IDENTIFIER", "VALID_ID_START", "VALID_ID_CHAR", 
			"WS", "OC_COMMENT", "SL_COMMENT"
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


	public msslLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "mssl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000,\u0120\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000]\b\u0000\n\u0000\f\u0000"+
		"`\t\u0000\u0003\u0000b\b\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u0001m\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001"+
		"\"\u0001\"\u0001#\u0001#\u0001#\u0001$\u0001$\u0001$\u0001$\u0001$\u0001"+
		"%\u0001%\u0001%\u0001&\u0001&\u0005&\u00f3\b&\n&\f&\u00f6\t&\u0001\'\u0003"+
		"\'\u00f9\b\'\u0001(\u0001(\u0003(\u00fd\b(\u0001)\u0004)\u0100\b)\u000b"+
		")\f)\u0101\u0001)\u0001)\u0001*\u0001*\u0001*\u0001*\u0005*\u010a\b*\n"+
		"*\f*\u010d\t*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001+\u0001+\u0001+"+
		"\u0001+\u0005+\u0118\b+\n+\f+\u011b\t+\u0001+\u0001+\u0001+\u0001+\u0002"+
		"\u010b\u0119\u0000,\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t"+
		"\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f"+
		"\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014"+
		")\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e"+
		"=\u001f? A!C\"E#G$I%K&M\'O(Q)S*U+W,\u0001\u0000\u0004\u0001\u000019\u0001"+
		"\u000009\u0003\u0000AZ__az\u0003\u0000\t\n\r\r  \u0127\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000"+
		"\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000"+
		"\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'"+
		"\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000"+
		"\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000"+
		"\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005"+
		"\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000"+
		"\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000"+
		"\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C"+
		"\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000"+
		"\u0000\u0000\u0000I\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000"+
		"\u0000M\u0001\u0000\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q"+
		"\u0001\u0000\u0000\u0000\u0000S\u0001\u0000\u0000\u0000\u0000U\u0001\u0000"+
		"\u0000\u0000\u0000W\u0001\u0000\u0000\u0000\u0001a\u0001\u0000\u0000\u0000"+
		"\u0003l\u0001\u0000\u0000\u0000\u0005n\u0001\u0000\u0000\u0000\u0007r"+
		"\u0001\u0000\u0000\u0000\tw\u0001\u0000\u0000\u0000\u000b|\u0001\u0000"+
		"\u0000\u0000\r~\u0001\u0000\u0000\u0000\u000f\u0080\u0001\u0000\u0000"+
		"\u0000\u0011\u0082\u0001\u0000\u0000\u0000\u0013\u0084\u0001\u0000\u0000"+
		"\u0000\u0015\u0086\u0001\u0000\u0000\u0000\u0017\u0088\u0001\u0000\u0000"+
		"\u0000\u0019\u008a\u0001\u0000\u0000\u0000\u001b\u008c\u0001\u0000\u0000"+
		"\u0000\u001d\u008e\u0001\u0000\u0000\u0000\u001f\u0090\u0001\u0000\u0000"+
		"\u0000!\u0092\u0001\u0000\u0000\u0000#\u0094\u0001\u0000\u0000\u0000%"+
		"\u0096\u0001\u0000\u0000\u0000\'\u0099\u0001\u0000\u0000\u0000)\u009e"+
		"\u0001\u0000\u0000\u0000+\u00a2\u0001\u0000\u0000\u0000-\u00a6\u0001\u0000"+
		"\u0000\u0000/\u00aa\u0001\u0000\u0000\u00001\u00ae\u0001\u0000\u0000\u0000"+
		"3\u00b2\u0001\u0000\u0000\u00005\u00b7\u0001\u0000\u0000\u00007\u00bc"+
		"\u0001\u0000\u0000\u00009\u00c2\u0001\u0000\u0000\u0000;\u00c8\u0001\u0000"+
		"\u0000\u0000=\u00ce\u0001\u0000\u0000\u0000?\u00d8\u0001\u0000\u0000\u0000"+
		"A\u00df\u0001\u0000\u0000\u0000C\u00e1\u0001\u0000\u0000\u0000E\u00e3"+
		"\u0001\u0000\u0000\u0000G\u00e5\u0001\u0000\u0000\u0000I\u00e8\u0001\u0000"+
		"\u0000\u0000K\u00ed\u0001\u0000\u0000\u0000M\u00f0\u0001\u0000\u0000\u0000"+
		"O\u00f8\u0001\u0000\u0000\u0000Q\u00fc\u0001\u0000\u0000\u0000S\u00ff"+
		"\u0001\u0000\u0000\u0000U\u0105\u0001\u0000\u0000\u0000W\u0113\u0001\u0000"+
		"\u0000\u0000Yb\u00050\u0000\u0000Z^\u0007\u0000\u0000\u0000[]\u0007\u0001"+
		"\u0000\u0000\\[\u0001\u0000\u0000\u0000]`\u0001\u0000\u0000\u0000^\\\u0001"+
		"\u0000\u0000\u0000^_\u0001\u0000\u0000\u0000_b\u0001\u0000\u0000\u0000"+
		"`^\u0001\u0000\u0000\u0000aY\u0001\u0000\u0000\u0000aZ\u0001\u0000\u0000"+
		"\u0000b\u0002\u0001\u0000\u0000\u0000cd\u0005t\u0000\u0000de\u0005r\u0000"+
		"\u0000ef\u0005u\u0000\u0000fm\u0005e\u0000\u0000gh\u0005f\u0000\u0000"+
		"hi\u0005a\u0000\u0000ij\u0005l\u0000\u0000jk\u0005s\u0000\u0000km\u0005"+
		"e\u0000\u0000lc\u0001\u0000\u0000\u0000lg\u0001\u0000\u0000\u0000m\u0004"+
		"\u0001\u0000\u0000\u0000no\u0005i\u0000\u0000op\u0005n\u0000\u0000pq\u0005"+
		"t\u0000\u0000q\u0006\u0001\u0000\u0000\u0000rs\u0005b\u0000\u0000st\u0005"+
		"o\u0000\u0000tu\u0005o\u0000\u0000uv\u0005l\u0000\u0000v\b\u0001\u0000"+
		"\u0000\u0000wx\u0005u\u0000\u0000xy\u0005n\u0000\u0000yz\u0005i\u0000"+
		"\u0000z{\u0005t\u0000\u0000{\n\u0001\u0000\u0000\u0000|}\u0005=\u0000"+
		"\u0000}\f\u0001\u0000\u0000\u0000~\u007f\u0005;\u0000\u0000\u007f\u000e"+
		"\u0001\u0000\u0000\u0000\u0080\u0081\u0005,\u0000\u0000\u0081\u0010\u0001"+
		"\u0000\u0000\u0000\u0082\u0083\u0005:\u0000\u0000\u0083\u0012\u0001\u0000"+
		"\u0000\u0000\u0084\u0085\u0005{\u0000\u0000\u0085\u0014\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0005}\u0000\u0000\u0087\u0016\u0001\u0000\u0000\u0000"+
		"\u0088\u0089\u0005(\u0000\u0000\u0089\u0018\u0001\u0000\u0000\u0000\u008a"+
		"\u008b\u0005)\u0000\u0000\u008b\u001a\u0001\u0000\u0000\u0000\u008c\u008d"+
		"\u0005<\u0000\u0000\u008d\u001c\u0001\u0000\u0000\u0000\u008e\u008f\u0005"+
		">\u0000\u0000\u008f\u001e\u0001\u0000\u0000\u0000\u0090\u0091\u0005\""+
		"\u0000\u0000\u0091 \u0001\u0000\u0000\u0000\u0092\u0093\u0005-\u0000\u0000"+
		"\u0093\"\u0001\u0000\u0000\u0000\u0094\u0095\u0005+\u0000\u0000\u0095"+
		"$\u0001\u0000\u0000\u0000\u0096\u0097\u0005f\u0000\u0000\u0097\u0098\u0005"+
		"n\u0000\u0000\u0098&\u0001\u0000\u0000\u0000\u0099\u009a\u0005m\u0000"+
		"\u0000\u009a\u009b\u0005a\u0000\u0000\u009b\u009c\u0005i\u0000\u0000\u009c"+
		"\u009d\u0005n\u0000\u0000\u009d(\u0001\u0000\u0000\u0000\u009e\u009f\u0005"+
		"l\u0000\u0000\u009f\u00a0\u0005e\u0000\u0000\u00a0\u00a1\u0005t\u0000"+
		"\u0000\u00a1*\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005m\u0000\u0000\u00a3"+
		"\u00a4\u0005u\u0000\u0000\u00a4\u00a5\u0005t\u0000\u0000\u00a5,\u0001"+
		"\u0000\u0000\u0000\u00a6\u00a7\u0005b\u0000\u0000\u00a7\u00a8\u0005o\u0000"+
		"\u0000\u00a8\u00a9\u0005x\u0000\u0000\u00a9.\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ab\u0005t\u0000\u0000\u00ab\u00ac\u0005r\u0000\u0000\u00ac\u00ad\u0005"+
		"c\u0000\u0000\u00ad0\u0001\u0000\u0000\u0000\u00ae\u00af\u0005S\u0000"+
		"\u0000\u00af\u00b0\u0005i\u0000\u0000\u00b0\u00b1\u0005g\u0000\u0000\u00b1"+
		"2\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005e\u0000\u0000\u00b3\u00b4\u0005"+
		"m\u0000\u0000\u00b4\u00b5\u0005i\u0000\u0000\u00b5\u00b6\u0005t\u0000"+
		"\u0000\u00b64\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005w\u0000\u0000\u00b8"+
		"\u00b9\u0005h\u0000\u0000\u00b9\u00ba\u0005e\u0000\u0000\u00ba\u00bb\u0005"+
		"n\u0000\u0000\u00bb6\u0001\u0000\u0000\u0000\u00bc\u00bd\u0005w\u0000"+
		"\u0000\u00bd\u00be\u0005a\u0000\u0000\u00be\u00bf\u0005t\u0000\u0000\u00bf"+
		"\u00c0\u0005c\u0000\u0000\u00c0\u00c1\u0005h\u0000\u0000\u00c18\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c3\u0005c\u0000\u0000\u00c3\u00c4\u0005l\u0000"+
		"\u0000\u00c4\u00c5\u0005o\u0000\u0000\u00c5\u00c6\u0005n\u0000\u0000\u00c6"+
		"\u00c7\u0005e\u0000\u0000\u00c7:\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005"+
		"s\u0000\u0000\u00c9\u00ca\u0005p\u0000\u0000\u00ca\u00cb\u0005a\u0000"+
		"\u0000\u00cb\u00cc\u0005w\u0000\u0000\u00cc\u00cd\u0005n\u0000\u0000\u00cd"+
		"<\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005c\u0000\u0000\u00cf\u00d0\u0005"+
		"o\u0000\u0000\u00d0\u00d1\u0005o\u0000\u0000\u00d1\u00d2\u0005p\u0000"+
		"\u0000\u00d2\u00d3\u0005e\u0000\u0000\u00d3\u00d4\u0005r\u0000\u0000\u00d4"+
		"\u00d5\u0005a\u0000\u0000\u00d5\u00d6\u0005t\u0000\u0000\u00d6\u00d7\u0005"+
		"e\u0000\u0000\u00d7>\u0001\u0000\u0000\u0000\u00d8\u00d9\u0005p\u0000"+
		"\u0000\u00d9\u00da\u0005r\u0000\u0000\u00da\u00db\u0005i\u0000\u0000\u00db"+
		"\u00dc\u0005n\u0000\u0000\u00dc\u00dd\u0005t\u0000\u0000\u00dd\u00de\u0005"+
		"!\u0000\u0000\u00de@\u0001\u0000\u0000\u0000\u00df\u00e0\u0005*\u0000"+
		"\u0000\u00e0B\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005&\u0000\u0000\u00e2"+
		"D\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005.\u0000\u0000\u00e4F\u0001"+
		"\u0000\u0000\u0000\u00e5\u00e6\u0005i\u0000\u0000\u00e6\u00e7\u0005f\u0000"+
		"\u0000\u00e7H\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005e\u0000\u0000\u00e9"+
		"\u00ea\u0005l\u0000\u0000\u00ea\u00eb\u0005s\u0000\u0000\u00eb\u00ec\u0005"+
		"e\u0000\u0000\u00ecJ\u0001\u0000\u0000\u0000\u00ed\u00ee\u0005=\u0000"+
		"\u0000\u00ee\u00ef\u0005=\u0000\u0000\u00efL\u0001\u0000\u0000\u0000\u00f0"+
		"\u00f4\u0003O\'\u0000\u00f1\u00f3\u0003Q(\u0000\u00f2\u00f1\u0001\u0000"+
		"\u0000\u0000\u00f3\u00f6\u0001\u0000\u0000\u0000\u00f4\u00f2\u0001\u0000"+
		"\u0000\u0000\u00f4\u00f5\u0001\u0000\u0000\u0000\u00f5N\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f7\u00f9\u0007\u0002\u0000"+
		"\u0000\u00f8\u00f7\u0001\u0000\u0000\u0000\u00f9P\u0001\u0000\u0000\u0000"+
		"\u00fa\u00fd\u0003O\'\u0000\u00fb\u00fd\u000209\u0000\u00fc\u00fa\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fb\u0001\u0000\u0000\u0000\u00fdR\u0001\u0000"+
		"\u0000\u0000\u00fe\u0100\u0007\u0003\u0000\u0000\u00ff\u00fe\u0001\u0000"+
		"\u0000\u0000\u0100\u0101\u0001\u0000\u0000\u0000\u0101\u00ff\u0001\u0000"+
		"\u0000\u0000\u0101\u0102\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000"+
		"\u0000\u0000\u0103\u0104\u0006)\u0000\u0000\u0104T\u0001\u0000\u0000\u0000"+
		"\u0105\u0106\u0005/\u0000\u0000\u0106\u0107\u0005*\u0000\u0000\u0107\u010b"+
		"\u0001\u0000\u0000\u0000\u0108\u010a\t\u0000\u0000\u0000\u0109\u0108\u0001"+
		"\u0000\u0000\u0000\u010a\u010d\u0001\u0000\u0000\u0000\u010b\u010c\u0001"+
		"\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010c\u010e\u0001"+
		"\u0000\u0000\u0000\u010d\u010b\u0001\u0000\u0000\u0000\u010e\u010f\u0005"+
		"*\u0000\u0000\u010f\u0110\u0005/\u0000\u0000\u0110\u0111\u0001\u0000\u0000"+
		"\u0000\u0111\u0112\u0006*\u0000\u0000\u0112V\u0001\u0000\u0000\u0000\u0113"+
		"\u0114\u0005/\u0000\u0000\u0114\u0115\u0005/\u0000\u0000\u0115\u0119\u0001"+
		"\u0000\u0000\u0000\u0116\u0118\t\u0000\u0000\u0000\u0117\u0116\u0001\u0000"+
		"\u0000\u0000\u0118\u011b\u0001\u0000\u0000\u0000\u0119\u011a\u0001\u0000"+
		"\u0000\u0000\u0119\u0117\u0001\u0000\u0000\u0000\u011a\u011c\u0001\u0000"+
		"\u0000\u0000\u011b\u0119\u0001\u0000\u0000\u0000\u011c\u011d\u0005\n\u0000"+
		"\u0000\u011d\u011e\u0001\u0000\u0000\u0000\u011e\u011f\u0006+\u0000\u0000"+
		"\u011fX\u0001\u0000\u0000\u0000\n\u0000^al\u00f4\u00f8\u00fc\u0101\u010b"+
		"\u0119\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}