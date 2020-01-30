package by.javatr.financetracker.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Expense extends Transaction implements Serializable {


    public Expense() {
        super();
    }

    public Expense(BigDecimal sum, ExpenseCategory category, int accountId, Date date, String note) {
        super(sum, category, accountId, date, note);
    }

    public Expense(BigDecimal sum, ExpenseCategory category, int accountId, Date date, String note, int id) {
        super(sum, category, accountId, date, note, id);
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Expense otherExpense = (Expense) obj;

        if (this.getId() != otherExpense.getId()) {
            return false;
        }
        if (this.getCategory() == null) {
            if (otherExpense.getCategory() != null) {
                return false;
            }
        } else {
            if (!this.getCategory().equals(otherExpense.getCategory())) {
                return false;
            }
        }

        if (this.getDate() == null) {
            if (otherExpense.getDate() != null) {
                return false;
            }
        } else {
            if (!this.getDate().equals(otherExpense.getDate())) {
                return false;
            }
        }

        if (this.getNote() == null) {
            if (otherExpense.getNote() != null) {
                return false;
            }
        } else {
            if (!this.getNote().equals(otherExpense.getNote())) {
                return false;
            }
        }

        if (Double.doubleToLongBits(this.getSum().doubleValue()) != Double.doubleToLongBits(otherExpense.getSum().doubleValue())) {
            return false;
        }

        if (this.getAccountId() != otherExpense.getAccountId()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "@" + "sum: -" + this.getSum().doubleValue() + ", category: "
                + this.getCategory() + ", account:  " + this.getAccountId()
                + ", date: " + this.getDate() + ", note: " + this.getNote() + ", id: " + this.getId();
    }

}
