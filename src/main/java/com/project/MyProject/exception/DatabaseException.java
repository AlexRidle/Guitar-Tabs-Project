package com.project.MyProject.exception;

public class DatabaseException extends Exception {
    public DatabaseException() {
        super();
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Exception e) {
        super(e);
    }

    public DatabaseException(String message, Exception e) {
        super(message, e);
    }
}