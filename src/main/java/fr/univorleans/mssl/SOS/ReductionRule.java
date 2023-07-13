package fr.univorleans.mssl.SOS;

import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;

public abstract class ReductionRule<T, S, E extends ReductionRule.Extension<T, S>> {
    private final E[] expressions;

    protected ReductionRule(E[] extensions) {
        this.expressions = extensions;
    }

    protected Pair<T, S> apply(T state, Lifetime lifetime, Expression expression, int k) {
        switch (expression.getOpcode()) {
            case Syntax.Let_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Declaration) expression, k);
            case Syntax.Assignment_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Assignment) expression, k);
            case Syntax.Block_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Block) expression, k);
            case Syntax.Box_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Box) expression, k);
            case Syntax.Trc_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Trc) expression, k);
            case Syntax.Invoke_Expression:
                return (Pair<T, S>) apply(state, lifetime, (InvokeFunction) expression, k);
            case Syntax.Declaration_Function:
                return (Pair<T, S>) apply(state, lifetime, (Function) expression, k);
            case Syntax.Sig_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Sig) expression, k);
            case Syntax.Emit_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Emit) expression, k);
            case Syntax.When_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.When) expression, k);
            case Syntax.Watch_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Watch) expression, k);
            case Syntax.Clone_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Clone) expression, k);
            case Syntax.IfElse_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.IfElse) expression, k);
            case Syntax.Borrow_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Borrow) expression, k);
            case Syntax.Conditional_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Conditional) expression, k);
            case Syntax.Dereference_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Access) expression, k);
            case Syntax.Tuples_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Tuples.TuplesExpression) expression, k);
            case Syntax.Cooperate_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Cooperate) expression, k);
            case Syntax.Print_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Print) expression, k);
            case Value.Unit_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Unit) expression, k);
            case Value.Integer_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Integer) expression, k);
            case Value.Bool_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Boolean) expression, k);
            case Value.Location_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Reference) expression, k);
        }
        // Attempt to run extensions
        for(int i=0;i!=expressions.length;++i) {
            Pair<T,S> r = expressions[i].apply(state, lifetime, k);
            if(r != null) {
                return r;
            }
        }
        // Give up
        throw new IllegalArgumentException("Invalid term encountered: " + expression);
    }

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Assignment expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Block expression, int k);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Box expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Sig expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Trc expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Emit expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.When expression, int k);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Conditional expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.IfElse expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Watch expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, InvokeFunction expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Clone expression, int k);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Borrow expression, int k);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Declaration expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Cooperate expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Access expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Tuples.TuplesExpression expression, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Value.Unit value, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Value.Integer value, int k);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Value.Reference value, int k);

    //protected abstract Pair<T, S> apply(Environment gam, Lifetime lifetime, Expression.Print expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Print expression, int k);

    //protected abstract void apply(Lifetime lifetime, Function function);

    public interface Extension<T,S> {
        abstract Pair<T, S> apply(T state, Lifetime lifetime, int k);
    }


}
