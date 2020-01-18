package by.javatr.financetracker.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Expense extends Transaction implements Serializable, Cloneable {

    //private ExpenseCategory category;

    public Expense() {
        super();
    }

    public Expense(BigDecimal sum, ExpenseCategory category, int accountId, Date date, String note) {
        /*
        super(date);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date; */
        this(sum, category, accountId, date);
        this.note = note;
    }

    public Expense(BigDecimal sum, ExpenseCategory category, int accountId, Date date) {
        super(date);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date;
    }

    public Expense(BigDecimal sum, ExpenseCategory category, int accountId, Date date, String note, int id) {
        /*
        super(id);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date; */
        this(sum, category, accountId, date, id);
        this.note = note;
    }

    public Expense(BigDecimal sum, ExpenseCategory category, int accountId, Date date, int id) {
        super(id);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date;
    }

    /**
     * maybe it should be overloaded in service
     * public void setSum(double sum){
     * this.sum = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
     * }
     */

    public void setSum(BigDecimal sum) {
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSum() {
        return sum;
    }

   // public void setCategory(ExpenseCategory category) {
     //   this.category = category;
    //}

    //public ExpenseCategory getCategory() {
      //  return category;
    //}

    public void setAccount(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = (int) (prime * result + sum.doubleValue());
        result = prime * result + accountId;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        return result * super.hashCode();
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

        if (category == null) {
            if (otherExpense.category != null) {
                return false;
            }
        } else {
            if (!category.equals(otherExpense.category)) {
                return false;
            }
        }

        if (date == null) {
            if (otherExpense.date != null) {
                return false;
            }
        } else {
            if (!date.equals(otherExpense.date)) {
                return false;
            }
        }

        if (note == null) {
            if (otherExpense.note != null) {
                return false;
            }
        } else {
            if (!note.equals(otherExpense.note)) {
                return false;
            }
        }

        if (Double.doubleToLongBits(sum.doubleValue()) != Double.doubleToLongBits(otherExpense.sum.doubleValue())) {
            return false;
        }

        if(accountId!= otherExpense.accountId){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + "sum: " + sum + ", category: " + category + ", accountId: " + accountId
                + ", date: " + date + ", note: " + note;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
