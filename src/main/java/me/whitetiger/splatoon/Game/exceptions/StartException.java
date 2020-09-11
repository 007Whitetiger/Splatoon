package me.whitetiger.splatoon.Game.exceptions;

public class StartException extends Exception{

    private final String message;

    public StartException(String message) {
        this.message = message;
    }

    public StartException() {
        this("StartException");
    }

    public String toString() {
        return message;
    }
}
