package com.felixtam.currencyfair.bdc.repository;

import com.felixtam.currencyfair.bdc.domain.TradeMsg;
import org.springframework.data.repository.CrudRepository;

public interface TradeMsgRepository extends CrudRepository<TradeMsg, Long> {
    TradeMsg findByUserId(long userId);
}
