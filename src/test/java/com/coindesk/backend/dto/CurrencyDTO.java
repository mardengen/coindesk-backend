package com.coindesk.backend.dto;

public class CurrencyDTO {

    private String currency;
    private String chineseName;
    private String rate;

    public CurrencyDTO() {}

    public CurrencyDTO(String currency, String chineseName, String rate) {
        this.currency = currency;
        this.chineseName = chineseName;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getRate() {
        return rate;
    }
}
