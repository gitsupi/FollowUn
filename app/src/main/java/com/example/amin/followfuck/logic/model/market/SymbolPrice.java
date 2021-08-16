package com.example.amin.followfuck.logic.model.market;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

import com.example.amin.followfuck.logic.constant.BinanceApiConstants;

public class SymbolPrice {

    private String symbol;

    private BigDecimal price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("symbol", symbol)
                .append("price", price).toString();
    }
}
