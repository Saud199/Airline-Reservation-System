package com.example.abc.emiratesairlines;

/**
 * Created by ABC on 25-Oct-17.
 */

public class Information {

    String name;
    String number;
    String accountType;


    public Information(){}

    public Information(String name, String number, String accountType) {
        this.name = name;
        this.number = number;
        this.accountType=accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}