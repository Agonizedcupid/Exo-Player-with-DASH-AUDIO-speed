package com.udvash.videofolder.pipe;

public class ParsingException extends Exception {
    public ParsingException(final String message) {
        super(message);
    }

    public ParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
