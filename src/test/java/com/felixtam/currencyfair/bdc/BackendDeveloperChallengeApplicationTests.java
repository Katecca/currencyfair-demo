package com.felixtam.currencyfair.bdc;

import com.felixtam.currencyfair.bdc.domain.TradeMsg;
import com.felixtam.currencyfair.bdc.repository.TradeMsgRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class BackendDeveloperChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeMsgRepository tradeMsgRepository;


    @Test
    void postNormalTradeMsg() throws Exception {

        String postMsg = "{\"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.7471, \"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FR\"}";

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(postMsg))
                .andExpect(status().isOk());

        TradeMsg tradeMsgResult = tradeMsgRepository.findByUserId(134256);

        assertThat("tradeMsgResult", tradeMsgResult.getUserId() == 134256);
    }

    @Test
    void postInvalidCurrencyTradeMsg() throws Exception {

        String postMsg = "{\"userId\": \"134257\", \"currencyFrom\": \"EU\", \"currencyTo\": \"GB\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.7471, \"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FR\"}";

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(postMsg))
                .andExpect(status().is4xxClientError());

        TradeMsg tradeMsgResult = tradeMsgRepository.findByUserId(134257);

        assertThat("tradeMsgResult", tradeMsgResult == null);
    }

    @Test
    void postNegativeAmtTradeMsg() throws Exception {

        String postMsg = "{\"userId\": \"134258\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": -1000, \"amountBuy\": 747.10, \"rate\": 0.7471, \"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FR\"}";

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(postMsg))
                .andExpect(status().is4xxClientError());

        TradeMsg tradeMsgResult = tradeMsgRepository.findByUserId(134258);

        assertThat("tradeMsgResult", tradeMsgResult == null);
    }


    @Test
    void postIncorrectRateTradeMsg() throws Exception {

        String postMsg = "{\"userId\": \"134259\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.07471, \"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FR\"}";

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(postMsg))
                .andExpect(status().is4xxClientError());

        TradeMsg tradeMsgResult = tradeMsgRepository.findByUserId(134259);

        assertThat("tradeMsgResult", tradeMsgResult == null);
    }

    @Test
    void postFutureTimePlacedTradeMsg() throws Exception {

        String postMsg = "{\"userId\": \"134260\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.07471, \"timePlaced\" : \"24-JAN-29 10:27:44\", \"originatingCountry\" : \"FR\"}";

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(postMsg))
                .andExpect(status().is4xxClientError());

        TradeMsg tradeMsgResult = tradeMsgRepository.findByUserId(134260);

        assertThat("tradeMsgResult", tradeMsgResult == null);
    }

    @Test
    void postIncorrectCountryTradeMsg() throws Exception {

        String postMsg = "{\"userId\": \"134261\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.07471, \"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FRE\"}";

        mockMvc.perform(post("/api/data")
                .contentType("application/json")
                .content(postMsg))
                .andExpect(status().is4xxClientError());

        TradeMsg tradeMsgResult = tradeMsgRepository.findByUserId(134261);

        assertThat("tradeMsgResult", tradeMsgResult == null);
    }
}
