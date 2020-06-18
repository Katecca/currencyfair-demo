package com.felixtam.currencyfair.bdc.service;

import com.felixtam.currencyfair.bdc.domain.TradeMsg;
import com.felixtam.currencyfair.bdc.exception.TradeMsgIncorrectRateException;
import com.felixtam.currencyfair.bdc.exception.TradeMsgSameCurrencyException;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@Service
public class TradeMsgValidationService {

    public void validate(TradeMsg tradeMsg) throws TradeMsgSameCurrencyException {
        if(tradeMsg.getCurrencyFrom().equalsIgnoreCase(tradeMsg.getCurrencyTo())){
            throw new TradeMsgSameCurrencyException("currencyFrom and currencyTo cannot be the same");

        }else if (tradeMsg.getAmountBuy().divide(tradeMsg.getAmountSell(), tradeMsg.getRate().scale(), RoundingMode.HALF_UP)
                .compareTo(tradeMsg.getRate()) != 0){
            throw new TradeMsgIncorrectRateException("currencyRate is invalid");

        }
    }
}
