package fr.univorleans.mssl.MSSL;

import fr.univorleans.mssl.CompiletoC.CompileToC;
import fr.univorleans.mssl.DynamicParser.MyvisitorBlock;
import fr.univorleans.mssl.DynamicSyntax.Lifetime;
import fr.univorleans.mssl.DynamicSyntax.Syntax.*;
import fr.univorleans.mssl.SOS.OperationalSemantics;
import fr.univorleans.mssl.SOS.OperationalSemanticsFunction;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.SOS.StoreProgram;
import fr.univorleans.mssl.TypeSystem.BorrowChecker;
import fr.univorleans.mssl.TypeSystem.BorrowCheckerFunction;
import fr.univorleans.mssl.Parser.msslLexer;
import fr.univorleans.mssl.Parser.msslParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class ExtensionMain {

    /*public class NullPointerExceptionExample {
         static void printLength(String str) {
            System.out.println(str.length());
        }
    }*/
    static private String source="";
    static private int NBthreads;

    public static int getNBthreads() {
        return NBthreads;
    }

    public static void setNBthreads() {
        ExtensionMain.NBthreads ++;
    }

    public static String getSource() {
        return source;
    }

    public static void main(String[] args) {
        Lifetime globalLifetime = new Lifetime();

        if (args.length == 0) {
            //printLength(Arrays.toString(args));
        }

        try {
            String fileName = args[0];
            /**
             * recuperer le nom du fichier
             */
            int index = fileName.lastIndexOf("/");
            source = "src/main/MSSLtoC/MSSLToCWithThreads/"+fileName.substring(fileName.lastIndexOf("/")+1, fileName.indexOf('.'))+".c";
            //  System.out.printf("\n ------------ "+source+" ----------------------\n");
            CharStream cs = fromFileName(fileName);
            /************** Parser and Lexer ************************/
            msslLexer lexer = new msslLexer(cs);
            CommonTokenStream token = new CommonTokenStream(lexer);
            msslParser parser = new msslParser(token);
            ParseTree tree = parser.program();

            MyvisitorBlock visitor = new MyvisitorBlock(globalLifetime);
            visitor.visit(tree);
            Expression.Block block = visitor.block;
            for (int i = 0; i < visitor.declarations.size(); i++) {
                System.out.printf("\n Function " + visitor.declarations.get(i).toString() + "\n");
            }
            System.out.printf("\n block " + block.toString());
            /*******************************************************/
            /**************** Borrow checker and typing rules ********/
            /*******************************************************/
            /**************** Borrow checker and typing rules ********/
            BorrowCheckerFunction typingFunction = new BorrowCheckerFunction(false, "Declaration", visitor.declarations);
            typingFunction.apply(BorrowChecker.EMPTY_ENVIRONMENT, globalLifetime, visitor.declarations);
            /** stock the number of threads created into functions **/
            int functionNBthreads = getNBthreads();
            BorrowChecker typing =  new BorrowChecker(false, "Declaration", visitor.declarations);
            //System.out.printf("\n block "+ block.toString());
            typing.apply(BorrowChecker.EMPTY_ENVIRONMENT, globalLifetime, block);
            /*******************************************************/
            /**************** Semantics and reduction rules ********/
         /*   OperationalSemanticsFunction opf = new OperationalSemanticsFunction(visitor.declarations);
            Pair<StoreProgram.State, Expression> state = new Pair<>(new StoreProgram.State(),visitor.block);
            Expression result = new OperationalSemantics(globalLifetime, opf.getFunctions()).execute(globalLifetime, state.second());
            /*****************************************************************/
            /**************** Compiler To C to execute in fairthreads ********/
            CompileToC compileToC = new CompileToC(visitor.declarations, typingFunction.getEnvFunctions(), typing.getGlobal(), getNBthreads() );
            CompileToC.debut_code();
            compileToC.execute(block);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }


}
// gradle build
// gradle --version
// ./gradlew run
// commande: ./gradlew --console=plain --quiet run --args "src/main/ExampleMSSL/TypeSystemTest/cooperate1.mssl"
