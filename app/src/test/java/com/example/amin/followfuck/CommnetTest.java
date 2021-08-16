package com.example.amin.followfuck;

import com.example.amin.followfuck.logic.PrivateConfig;
import com.example.amin.followfuck.logic.SyncRequestClient;
import com.example.amin.followfuck.logic.model.market.ExchangeInfoEntry;
import com.example.amin.followfuck.logic.model.market.ExchangeInformation;

import org.junit.Test;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CommnetTest {
    @Test
    public void addition_isCorrect() {


        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);


        ExchangeInformation exchangeInformation = syncRequestClient.getExchangeInformation();
        List<ExchangeInfoEntry> symbols = exchangeInformation.getSymbols();
        System.out.println(symbols.get(0));

    }
}