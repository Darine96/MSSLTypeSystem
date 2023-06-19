package fr.univorleans.mssl.CompiletoC;

import java.util.HashMap;

public class ListeVar {

    private HashMap<String, Integer> liste_var;

    public ListeVar(HashMap<String, Integer> liste_var) {
        this.liste_var = liste_var;
    }
    public HashMap<String, Integer> getListe_var() {
        return liste_var;
    }

    public void setListe_var(String var, int count) {
        this.liste_var.put(var,count);
    }

    public int get(String key){
        return liste_var.get(key);
    }

    public boolean contains(String key){
        return liste_var.containsKey(key);
    }

    public void pop(String key){
        liste_var.remove(key);
    }

    public void pop(String key, int count){

        liste_var.put(key, liste_var.get(key)+count);
    }

    public void update(String key, int count){
        liste_var.put(key, liste_var.get(key)+count);
    }
}
