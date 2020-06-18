package com.felixtam.currencyfair.bdc.controller;

import com.felixtam.currencyfair.bdc.repository.TradeMsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.felixtam.currencyfair.bdc.constant.ApiConstant.API_ENDPOINT;
import static com.felixtam.currencyfair.bdc.constant.ApiConstant.API_ENDPOINT_DATA;

@RestController
@RequestMapping(API_ENDPOINT)
public class MsgFrontendController {

    @Autowired
    TradeMsgRepository tradeMsgRepository;

    @Autowired
    SimpMessagingTemplate notificationMsgtemplate;

    @DeleteMapping(API_ENDPOINT_DATA)
    public void deleteAll() {
        tradeMsgRepository.deleteAll();
        notificationMsgtemplate.convertAndSend("/notification/trade-msg", tradeMsgRepository.findAll());
    }

    @MessageMapping("/start")
    @SendTo("/notification/trade-msg")
    public Iterable initialize (){
        return tradeMsgRepository.findAll();
    }
}
