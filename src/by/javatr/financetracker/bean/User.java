package by.javatr.financetracker.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private String logIn;
    private final int id;
    //private char[] password; //TODO think if it's needed here
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public User() {
        id = new Date().hashCode();
    }

    public User(String logIn) {
        this.logIn = logIn;
        id = new Date().hashCode();
    }

    public User(String logIn, int id) {
        this.logIn = logIn;
        this.id = id;
        //this.password = password;
    }

    public User(String logIn, int id, ArrayList<Account> accounts, ArrayList<Transaction> transactions) {
        this.logIn = logIn;
        this.id = id;
        //this.password = password;
        this.accounts = accounts;
        this.transactions = transactions;
    }

    public String getLogIn() {
        return logIn;
    }

    public void setLogIn(String logIn) {
        this.logIn = logIn;
    }

    //TODO add setters and getters for an object from collection via id
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(int accountId) {
        for (Account account : accounts) {
            if (account.getId() == accountId) {
                return account;
            }
        }
        //TODO logically it should throw exception bu it can't
        return null;
    }

    public boolean hasAccount(int accountId) {
        for (Account account : accounts) {
            if (account.getId() == accountId) {
                return true;
            }
        }
        return false;
    }
    /*
    public void addAccount(Account account){
        accounts.add(account);
    } */

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public Transaction getTransaction(int transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == transactionId) {
                return transaction;
            }
        }
        //TODO logically it should throw exception
        return null;
    }

    public boolean hasTransaction(int transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getId() == transactionId) {
                return true;
            }
        }
        return false;
    }

    /*
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    } */

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        //TODO maybe return id here instead of all that stuff
        final int prime = 31;
        int result = 1;
        //result = prime * result + ((logIn == null) ? 0 : logIn.hashCode());
        result = prime * result + id;
        return result;
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

        User otherUser = (User) obj;

        if (logIn == null) {
            if (otherUser.logIn != null) {
                return false;
            }
        } else {
            if (!logIn.equals(otherUser.logIn)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        //TODO maybe it should return more stuff
        return getClass().getName() + "@" + "logIn: " + logIn + " id:" + id;
    }
}
