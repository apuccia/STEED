package it.unipi.trustgraphmanager.exceptions;

import lombok.Getter;

@Getter
public class ControllerException extends Exception {

    private final String errorMessage;

    public ControllerException(final Throwable throwable, final String errorMessage) {
        super(throwable);
        this.errorMessage = errorMessage;
    }
}
