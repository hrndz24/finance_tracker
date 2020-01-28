package by.javatr.financetracker.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String logIn;
    private final int id;

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
    }

    public String getLogIn() {
        return logIn;
    }

    public void setLogIn(String logIn) {
        this.logIn = logIn;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
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

        if (id != otherUser.getId()) {
            return false;
        }
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
        return getClass().getName() + "@" + "logIn: " + logIn + ", id:" + id;
    }
}
