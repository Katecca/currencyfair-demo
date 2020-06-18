package com.felixtam.currencyfair.bdc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class TradeMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Positive(message = "userId is invalid")
    private long userId;

    @NotBlank
    @Size(min = 3, max = 3, message = "currency must be in 3 chars")
    private String currencyFrom;

    @NotBlank
    @Size(min = 3, max = 3, message = "currency must be in 3 chars")
    private String currencyTo;

    @NotNull
    @Positive
    private BigDecimal amountSell;

    @NotNull
    @Positive
    private BigDecimal amountBuy;

    @NotNull
    @Positive
    @Column(precision = 20, scale = 10)
    private BigDecimal rate;

    @NotNull
    @Past
    @JsonFormat(pattern = "dd-MMM-yy k:mm:ss")
    private Date timePlaced;

    @NotBlank
    @Size(min = 2, max = 2, message = "country code must be in 2 chars")
    private String originatingCountry;

    private Date insertDate;

    private String ipAddr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public BigDecimal getAmountSell() {
        return amountSell;
    }

    public void setAmountSell(BigDecimal amountSell) {
        this.amountSell = amountSell;
    }

    public BigDecimal getAmountBuy() {
        return amountBuy;
    }

    public void setAmountBuy(BigDecimal amountBuy) {
        this.amountBuy = amountBuy;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(Date timePlaced) {
        this.timePlaced = timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }

    public void setOriginatingCountry(String originatingCountry) {
        this.originatingCountry = originatingCountry;
    }

    public Date getInsertDate() { return insertDate; }

    public void setInsertDate(Date insertDate) { this.insertDate = insertDate; }

    public String getIpAddr() { return ipAddr; }

    public void setIpAddr(String ipAddr) { this.ipAddr = ipAddr; }
}
