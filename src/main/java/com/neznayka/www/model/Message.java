package com.neznayka.www.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Denis Polulyakh
 */

public class Message {

    private List<String> phrase = new ArrayList<String>();

    private Set codeCurrency;
    private Set nameCurrency;

    public Set getCodeCurrency() {
        return codeCurrency;
    }

    public void setCodeCurrency(Set codeCurrency) {
        this.codeCurrency = codeCurrency;
    }

    public Set getNameCurrency() {
        return nameCurrency;
    }

    public void setNameCurrency(Set nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    public List<String> getPhrase() {
        return phrase;
    }

    public void setPhrase(List<String> word) {
        this.phrase = phrase;
    }

    public void addPhrase(String word){
        phrase.add(word);
    }

    public void resetPhrase(){
        phrase.clear();
    }
}
