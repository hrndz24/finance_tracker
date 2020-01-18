package by.javatr.financetracker.bean;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Transaction {
    protected BigDecimal sum;
    protected String note = "";
    protected int accountId;
    protected Date date;
    protected final int id;
    protected Enum category;

    public Enum getCategory() {
        return category;
    }

    public void setCategory(Enum category) {
        this.category = category;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public String getNote() {
        return note;
    }

    public int getAccountId() {
        return accountId;
    }

    public Date getDate() {
        return date;
    }

    //TODO setters and getter should be here
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
