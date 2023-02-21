package it.unipi.trustgraphmanager.exceptions;

import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private String errorMessage;

    public ServiceException(final Throwable throwable, final String errorMessage) {
        super(throwable);
        this.errorMessage = errorMessage;
    }

    public ServiceException(final Throwable throwable) {
        super(throwable);
    }
}
