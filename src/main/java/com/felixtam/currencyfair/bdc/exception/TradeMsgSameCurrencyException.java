package com.felixtam.currencyfair.bdc.exception;

public class TradeMsgSameCurrencyException extends RuntimeException {

    public TradeMsgSameCurrencyException(String errorMessage) {
        super(errorMessage);
    }
}
