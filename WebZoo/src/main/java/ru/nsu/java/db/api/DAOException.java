package ru.nsu.java.db.api;

public class DAOException extends Exception {
    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
