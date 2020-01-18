package by.javatr.financetracker.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Income extends Transaction implements Serializable, Cloneable {

    private IncomeCategory category;

    public Income() {
        super();
    }

    public Income(BigDecimal sum, IncomeCategory category, int accountId, Date date, String note) {
        /*super(date);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date; */
        this(sum, category, accountId, date);
        this.note = note;
    }

    public Income(BigDecimal sum, IncomeCategory category, int accountId, Date date) {
        super(date);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date;
    }

    public Income(BigDecimal sum, IncomeCategory category, int accountId, Date date, String note, int id) {
        /*super(id);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date; */
        this(sum, category, accountId, date, id);
        this.note = note;
    }

    public Income(BigDecimal sum, IncomeCategory category, int accountId, Date date, int id) {
        super(id);
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
        this.accountId = accountId;
        this.category = category;
        this.date = date;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setCategory(IncomeCategory category) {
        this.category = category;
    }

    public IncomeCategory getCategory() {
        return category;
    }

    public void setAccount(int accountId) {
        this.accountId = accountId;
    }

    public int getAccount() {
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

        Income otherIncome = (Income) obj;

        if (category == null) {
            if (otherIncome.category != null) {
                return false;
            }
        } else {
            if (!category.equals(otherIncome.category)) {
                return false;
            }
        }

        if (date == null) {
            if (otherIncome.date != null) {
                return false;
            }
        } else {
            if (!date.equals(otherIncome.date)) {
                return false;
            }
        }

        if (note == null) {
            if (otherIncome.note != null) {
                return false;
            }
        } else {
            if (!note.equals(otherIncome.note)) {
                return false;
            }
        }

        if (Double.doubleToLongBits(sum.doubleValue()) != Double.doubleToLongBits(otherIncome.sum.doubleValue())) {
            return false;
        }

        if(accountId!= otherIncome.accountId){
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
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
