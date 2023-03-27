package com.vladimirpandruov.noteApp1.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
