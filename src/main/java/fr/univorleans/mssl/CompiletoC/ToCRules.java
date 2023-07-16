package fr.univorleans.mssl.CompiletoC;

import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;
public abstract class ToCRules< E extends ToCRules.ExtensionToC> {

    private final E[] expressions;

    protected ToCRules(E[] expressions) {
        this.expressions = expressions;
    }
    protected Expression apply(Expression expression) {
        switch (expression.getOpcode()) {
            case Syntax.Let_Expression:
                return  apply((Expression.Declaration) expression);
            case Syntax.Assignment_Expression:
                return  apply((Expression.Assignment) expression);
            case Syntax.Block_Expression:
                return  apply((Expression.Block) expression);
            case Syntax.Box_Expression:
                return  apply((Expression.Box) expression);
            case Syntax.Trc_Expression:
                return  apply((Expression.Trc) expression);
            case Syntax.Invoke_Expression:
                return  apply((InvokeFunction) expression);
            case Syntax.Declaration_Function:
                return  apply((Function) expression);
            case Syntax.Clone_Expression:
                return  apply((Expression.Clone) expression);
            case Syntax.Borrow_Expression:
                return  apply((Expression.Borrow) expression);
            case Syntax.Dereference_Expression:
                return  apply((Expression.Access) expression);
            case Syntax.Tuples_Expression:
                return  apply((Tuples.TuplesExpression) expression);
            case Syntax.Cooperate_Expression:
                return  apply((Expression.Cooperate) expression);
            case Syntax.Print_Expression:
                return  apply((Expression.Print) expression);
            case Syntax.Sig_Expression:
                return  apply((Expression.Sig) expression);
            case Syntax.When_Expression:
                return  apply((Expression.When) expression);
            case Syntax.Watch_Expression:
                return  apply((Expression.Watch) expression);
            case Syntax.Conditional_Expression:
                return  apply((Expression.Conditional) expression);
            case Syntax.IfElse_Expression:
                return  apply((Expression.IfElse) expression);
            case Syntax.Emit_Expression:
                return  apply((Expression.Emit) expression);
            case Value.Unit_Expression:
                return  apply((Value.Unit) expression);
            case Value.Integer_Expression:
                return  apply((Value.Integer) expression);
            case Value.Bool_Expression:
                return  apply((Value.Boolean) expression);
            case Value.Location_Expression:
                return  apply((Value.Reference) expression);
        }
        // Attempt to run extensions
        for(int i=0;i!=expressions.length;++i) {
            expressions[i].apply(expression);
        }
        // Give up
        throw new IllegalArgumentException("Invalid term encountered: " + expression);
    }

    protected abstract Expression apply(Expression.Assignment expression);

    protected abstract Expression apply(Expression.Block expression);
    protected abstract Expression apply(Expression.Box expression);

    protected abstract Expression apply(Expression.Trc expression);

    protected abstract Expression apply(Expression.Conditional expression);
    protected abstract Expression apply(Expression.IfElse expression);

    protected abstract Expression apply(InvokeFunction expression);

    protected abstract Expression apply(Expression.Clone expression);
    protected abstract Expression apply(Expression.Borrow expression);
    protected abstract Expression apply(Expression.Declaration expression);

    protected abstract Expression apply(Expression.Cooperate expression);

    protected abstract Expression apply(Expression.Access expression);

    protected abstract Expression apply(Expression.Sig expression);

    protected abstract Expression apply(Expression.When expression);

    protected abstract Expression apply(Expression.Watch expression);

    protected abstract Expression apply(Expression.Emit expression);


    protected abstract Expression apply(Tuples.TuplesExpression expression);

    protected abstract Expression apply(Value.Unit value);

    protected abstract Expression apply(Value.Integer value);

    protected abstract Expression apply(Value.Reference value);

    protected abstract Expression apply(Expression.Print expression);

    public interface ExtensionToC {
        abstract Expression apply(Expression expression);
    }

}
