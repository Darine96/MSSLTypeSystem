package fr.univorleans.mssl.SOS;

import fr.univorleans.mssl.DynamicSyntax.Function;
import fr.univorleans.mssl.DynamicSyntax.Lifetime;
import fr.univorleans.mssl.DynamicSyntax.Syntax;
import fr.univorleans.mssl.SOS.StoreProgram.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationalSemanticsFunction extends OperationalSemantics.Extension {
    private Map<String, Function> functions = new HashMap<>();

    public OperationalSemanticsFunction(List<Function> decls) {
        for (int i = 0; i != decls.size(); ++i) {
            Function ith = decls.get(i);
            //System.out.printf("\n name function "+ith.getName());
            functions.put(ith.getName(), ith);
        }
    }

    public Map<String, Function> getFunctions() {
        return functions;
    }

    @Override
    public Pair<State, Syntax.Expression> apply(State state, Lifetime lifetime, Syntax.Expression expression) {
        return null;
    }
}