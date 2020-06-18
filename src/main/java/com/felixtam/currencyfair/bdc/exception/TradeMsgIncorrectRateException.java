package com.felixtam.currencyfair.bdc.exception;

public class TradeMsgIncorrectRateException extends RuntimeException {

    public TradeMsgIncorrectRateException(String errorMessage) {
        super(errorMessage);
    }
}
