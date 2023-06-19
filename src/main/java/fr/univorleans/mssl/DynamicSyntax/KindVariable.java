package fr.univorleans.mssl.DynamicSyntax;

import java.util.HashMap;
import java.util.Map;

public class KindVariable {
    /**
     * evironment maps the variable to a boolean:
     * true: means that the variable have move semantics
     * otherwise false
     */
    private HashMap<String, Boolean> deduceKind;

    public KindVariable(HashMap<String, Boolean> deduceKind) {
        this.deduceKind = deduceKind;
    }

    public HashMap<String, Boolean> getDeduceKind() {
        return deduceKind;
    }

    public void setDeduceKind(HashMap<String, Boolean> deduceKind) {
        this.deduceKind = deduceKind;
    }
    
    public void put(String var, Boolean kind){
        this.deduceKind.put(var,kind);
    }

    public Boolean contains (String var){
        if(this.deduceKind.containsKey(var)){
            return deduceKind.get(var);
        }

        else return false;
    }

    public void clear(){
        this.deduceKind.clear();
    }

    public String toString(){
        String s ="";
        for (Map.Entry<String, Boolean>entry: deduceKind.entrySet()) {
            s=s+"string "+entry.getKey()+" kin "+entry.getValue()+" ";
        }
        return s;
    }
}
