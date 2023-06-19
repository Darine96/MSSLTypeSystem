package fr.univorleans.mssl.SOS;

import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;
import fr.univorleans.mssl.TypeSystem.Environment;

public abstract class ReductionRule<T, S, E extends ReductionRule.Extension<T, S>> {
    private final E[] expressions;

    protected ReductionRule(E[] extensions) {
        this.expressions = extensions;
    }

    protected Pair<T, S> apply(T state, Lifetime lifetime, Expression expression) {
        switch (expression.getOpcode()) {
            case Syntax.Let_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Declaration) expression);
            case Syntax.Assignment_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Assignment) expression);
            case Syntax.Block_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Block) expression);
            case Syntax.Box_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Box) expression);
            case Syntax.Trc_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Trc) expression);
            case Syntax.Invoke_Expression:
                return (Pair<T, S>) apply(state, lifetime, (InvokeFunction) expression);
            case Syntax.Declaration_Function:
                return (Pair<T, S>) apply(state, lifetime, (Function) expression);
            case Syntax.Clone_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Clone) expression);
            case Syntax.Borrow_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Borrow) expression);
            case Syntax.Dereference_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Access) expression);
            case Syntax.Tuples_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Tuples.TuplesExpression) expression);
            case Syntax.Cooperate_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Cooperate) expression);
            case Syntax.Print_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Expression.Print) expression);
            case Value.Unit_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Unit) expression);
            case Value.Integer_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Integer) expression);
            case Value.Bool_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Boolean) expression);
            case Value.Location_Expression:
                return (Pair<T, S>) apply(state, lifetime, (Value.Reference) expression);
        }
        // Attempt to run extensions
        for(int i=0;i!=expressions.length;++i) {
            Pair<T,S> r = expressions[i].apply(state, lifetime, expression);
            if(r != null) {
                return r;
            }
        }
        // Give up
        throw new IllegalArgumentException("Invalid term encountered: " + expression);
    }

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Assignment expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Block expression);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Box expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Trc expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, InvokeFunction expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Clone expression);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Borrow expression);
    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Declaration expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Cooperate expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Access expression);


    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Tuples.TuplesExpression expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Value.Unit value);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Value.Integer value);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Value.Reference value);

    //protected abstract Pair<T, S> apply(Environment gam, Lifetime lifetime, Expression.Print expression);

    protected abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression.Print expression);

    //protected abstract void apply(Lifetime lifetime, Function function);

    public interface Extension<T,S> {
        abstract Pair<T, S> apply(T state, Lifetime lifetime, Expression expression);
    }


}
