package by.javatr.financetracker.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Account implements Serializable, Cloneable {
    private String name;
    private BigDecimal balance;
    private final int id;

    public Account() {
        this.id = new Date().hashCode();
    }

    public Account(String name, BigDecimal balance) {
        this.id = new Date().hashCode();
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.name = name;
    }

    public Account(String name, BigDecimal balance, int id) {
        this.id = id;
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = (int) (prime * result + balance.doubleValue());
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

        Account otherAccount = (Account) obj;

        if (name == null) {
            if (otherAccount.name != null) {
                return false;
            }
        } else {
            if (!name.equals(otherAccount.name)) {
                return false;
            }
        }

        if (Double.doubleToLongBits(balance.doubleValue()) != Double.doubleToLongBits(otherAccount.balance.doubleValue())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + "name: " + name + ", balance: " + balance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
