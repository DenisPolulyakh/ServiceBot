package com.neznayka.www.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Denis Polulyakh
 */

public class Message {


    private Set codeCurrency;
    private Set nameCurrency;

    private String phrase;

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

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}


