package com.felixtam.currencyfair.bdc.controller;

import com.felixtam.currencyfair.bdc.domain.TradeMsg;
import com.felixtam.currencyfair.bdc.exception.TradeMsgIncorrectRateException;
import com.felixtam.currencyfair.bdc.repository.TradeMsgRepository;
import com.felixtam.currencyfair.bdc.service.TradeMsgValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.felixtam.currencyfair.bdc.exception.TradeMsgSameCurrencyException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.felixtam.currencyfair.bdc.constant.ApiConstant.API_ENDPOINT;
import static com.felixtam.currencyfair.bdc.constant.ApiConstant.API_ENDPOINT_DATA;

@RestController
@RequestMapping(API_ENDPOINT)
public class MsgConsumptionController {

    @Autowired
    TradeMsgRepository tradeMsgRepository;

    @Autowired
    MsgFrontendController msgFrontendController;

    @Autowired
    TradeMsgValidationService tradeMsgValidationService;

    @Autowired
    SimpMessagingTemplate notificationMsgtemplate;

    @PostMapping(value = API_ENDPOINT_DATA, consumes = "application/json")
    public TradeMsg consume(@Valid @RequestBody TradeMsg tradeMsg, HttpServletRequest request){
        tradeMsg.setInsertDate(new Date());
        tradeMsg.setIpAddr(request.getRemoteAddr());
        tradeMsgValidationService.validate(tradeMsg);
        tradeMsgRepository.save(tradeMsg);

        notificationMsgtemplate.convertAndSend("/notification/trade-msg", tradeMsgRepository.findAll());
        return tradeMsg;
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TradeMsgSameCurrencyException.class)
    public Map<String, String> handleValidationExceptions(TradeMsgSameCurrencyException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("currencyFrom", ex.getMessage());
        errors.put("currencyTo", ex.getMessage());
        return errors;
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TradeMsgIncorrectRateException.class)
    public Map<String, String> handleValidationExceptions(TradeMsgIncorrectRateException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("rate", ex.getMessage());
        return errors;
    }
}
