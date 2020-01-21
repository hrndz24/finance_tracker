package by.javatr.financetracker.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Income extends Transaction implements Serializable {

    public Income() {
        super();
    }

    public Income(BigDecimal sum, IncomeCategory category, int accountId, Date date, String note) {
        super(sum, category, accountId, date, note);
    }

    public Income(BigDecimal sum, IncomeCategory category, int accountId, Date date, String note, int id) {
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

        Income otherIncome = (Income) obj;

        if (this.getId() != otherIncome.getId()) {
            return false;
        }

        if (this.getCategory() == null) {
            if (otherIncome.getCategory() != null) {
                return false;
            }
        } else {
            if (!this.getCategory().equals(otherIncome.getCategory())) {
                return false;
            }
        }

        if (this.getDate() == null) {
            if (otherIncome.getDate() != null) {
                return false;
            }
        } else {
            if (!this.getDate().equals(otherIncome.getDate())) {
                return false;
            }
        }

        if (this.getNote() == null) {
            if (otherIncome.getNote() != null) {
                return false;
            }
        } else {
            if (!this.getNote().equals(otherIncome.getNote())) {
                return false;
            }
        }

        if (Double.doubleToLongBits(this.getSum().doubleValue()) != Double.doubleToLongBits(otherIncome.getSum().doubleValue())) {
            return false;
        }

        if (this.getAccountId() != otherIncome.getAccountId()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "@" + "sum: +" + this.getSum().doubleValue() + ", category: "
                + this.getCategory() + ", account: " + this.getAccountId()
                + ", date: " + this.getDate() + ", note: " + this.getNote() + ", id: " + this.getId();
    }

}
