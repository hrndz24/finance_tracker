package by.javatr.financetracker.bean;

import java.math.BigDecimal;
import java.util.Date;

abstract class Transaction {
    BigDecimal sum;
    String note;
    Account account;
    Date date;
    final int id;

    protected Transaction(){
        this.id = new Date().hashCode();
    }

    protected Transaction(Date date){
        this.id = date.hashCode();
    }

    protected Transaction(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
