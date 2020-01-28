package by.javatr.financetracker.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public abstract class Transaction {
    private BigDecimal sum;
    private String note = "";
    private int accountId;
    private Date date;
    private final int id;
    private Enum category;

    public Transaction(BigDecimal sum, Enum category, int accountId, Date date, String note, int id) {
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.note = note;
        this.accountId = accountId;
        this.date = date;
        this.id = id;
        this.category = category;
    }

    public Transaction(BigDecimal sum, Enum category, int accountId, Date date, String note) {
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.note = note;
        this.accountId = accountId;
        this.date = date;
        this.id = date.hashCode();
        this.category = category;
    }

    Transaction() {
        this.id = new Date().hashCode();
    }

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

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
}
