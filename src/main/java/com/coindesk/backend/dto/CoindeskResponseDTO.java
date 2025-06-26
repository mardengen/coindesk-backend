package com.coindesk.backend.dto;

import java.util.List;

public class CoindeskResponseDTO {

    private String updateTime;
    private List<CurrencyDTO> currencyList;

    public CoindeskResponseDTO() {}

    public CoindeskResponseDTO(String updateTime, List<CurrencyDTO> currencyList) {
        this.updateTime = updateTime;
        this.currencyList = currencyList;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public List<CurrencyDTO> getCurrencyList() {
        return currencyList;
    }
}
