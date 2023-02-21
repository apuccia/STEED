package it.unipi.trustgraphmanager.exceptions;

import lombok.Getter;

@Getter
public class DeployerUtilityException extends Exception {

    private String errorMessage;

    public DeployerUtilityException(final Throwable throwable, final String errorMessage) {
        super(throwable);
        this.errorMessage = errorMessage;
    }

    public DeployerUtilityException(final Throwable throwable) {
        super(throwable);
    }
}
