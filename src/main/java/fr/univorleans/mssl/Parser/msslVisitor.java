// Generated from java-escape by ANTLR 4.11.1
package fr.univorleans.mssl.Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link msslParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface msslVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code Prog}
	 * labeled alternative in {@link msslParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(msslParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Declaration_function}
	 * labeled alternative in {@link msslParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration_function(msslParser.Declaration_functionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParamsFunc}
	 * labeled alternative in {@link msslParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamsFunc(msslParser.ParamsFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SigUnit}
	 * labeled alternative in {@link msslParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigUnit(msslParser.SigUnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SigInt}
	 * labeled alternative in {@link msslParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigInt(msslParser.SigIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SigBool}
	 * labeled alternative in {@link msslParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigBool(msslParser.SigBoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SigBox}
	 * labeled alternative in {@link msslParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigBox(msslParser.SigBoxContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SigTrc}
	 * labeled alternative in {@link msslParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigTrc(msslParser.SigTrcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SigClone}
	 * labeled alternative in {@link msslParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigClone(msslParser.SigCloneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpBlock}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpBlock(msslParser.ExpBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpCooperate}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpCooperate(msslParser.ExpCooperateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpBox}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpBox(msslParser.ExpBoxContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpTrc}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpTrc(msslParser.ExpTrcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpInvoke}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpInvoke(msslParser.ExpInvokeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpIndex}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpIndex(msslParser.ExpIndexContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpPrint}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpPrint(msslParser.ExpPrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpVal}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpVal(msslParser.ExpValContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpMutableRef}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpMutableRef(msslParser.ExpMutableRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpSharedRef}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpSharedRef(msslParser.ExpSharedRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpTuple}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpTuple(msslParser.ExpTupleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpDeref}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpDeref(msslParser.ExpDerefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpClone}
	 * labeled alternative in {@link msslParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpClone(msslParser.ExpCloneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypInt}
	 * labeled alternative in {@link msslParser#type_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypInt(msslParser.TypIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypBoolean}
	 * labeled alternative in {@link msslParser#type_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypBoolean(msslParser.TypBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StmtBlock}
	 * labeled alternative in {@link msslParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtBlock(msslParser.StmtBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstSequence}
	 * labeled alternative in {@link msslParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstSequence(msslParser.InstSequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstLet}
	 * labeled alternative in {@link msslParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstLet(msslParser.InstLetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstAssignment}
	 * labeled alternative in {@link msslParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstAssignment(msslParser.InstAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstExpr}
	 * labeled alternative in {@link msslParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstExpr(msslParser.InstExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstBlock}
	 * labeled alternative in {@link msslParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstBlock(msslParser.InstBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link msslParser#declVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclVar(msslParser.DeclVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link msslParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(msslParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BOOL}
	 * labeled alternative in {@link msslParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBOOL(msslParser.BOOLContext ctx);
}