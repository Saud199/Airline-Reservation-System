package com.example.abc.emiratesairlines;

/**
 * Created by ABC on 25-Oct-17.
 */

public class CustomerInfo {

    static String name;
    static String no;

    public CustomerInfo() {
    }

    public CustomerInfo(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
