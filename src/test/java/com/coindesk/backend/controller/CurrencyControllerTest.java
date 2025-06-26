package com.coindesk.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllCurrencies() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreateCurrency() throws Exception {
        String json = "{"
                + "\"code\":\"JPY\","
                + "\"chineseName\":\"日圓\""
                + "}";

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("JPY"))
                .andExpect(jsonPath("$.chineseName").value("日圓"));
    }

    @Test
    void testUpdateCurrency() throws Exception {
        // 先新增 JPY
        String createJson = "{"
                + "\"code\":\"JPY\","
                + "\"chineseName\":\"日圓\""
                + "}";

        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson)).andExpect(status().isOk());

        // 修改中文名稱
        String updateJson = "{"
                + "\"code\":\"JPY\","
                + "\"chineseName\":\"日本圓\""
                + "}";

        mockMvc.perform(put("/api/currencies/JPY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chineseName").value("日本圓"));
    }

    @Test
    void testDeleteCurrency() throws Exception {
        // 先新增 EUR
        String json = "{"
                + "\"code\":\"EUR\","
                + "\"chineseName\":\"歐元\""
                + "}";

        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());

        // 刪除 EUR
        mockMvc.perform(delete("/api/currencies/EUR"))
                .andExpect(status().isNoContent());

        // 再查詢 EUR，應該找不到
        mockMvc.perform(get("/api/currencies/EUR"))
                .andExpect(status().isNotFound());
    }

    @BeforeEach
    void prepareCurrencyData() throws Exception {
        // 建立幣別中文對應（這是 transform API 要用的）
        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"USD\",\"chineseName\":\"美元\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"GBP\",\"chineseName\":\"英鎊\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"EUR\",\"chineseName\":\"歐元\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchRawCoindesk() throws Exception {
        mockMvc.perform(get("/api/coindesk/raw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bpi").exists())
                .andExpect(jsonPath("$.bpi.USD").exists());
    }

    @Test
    void testFetchTransformedCoindesk() throws Exception {
        mockMvc.perform(get("/api/coindesk/transform"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateTime").exists())
                .andExpect(jsonPath("$.currencies.USD.code").value("USD"))
                .andExpect(jsonPath("$.currencies.USD.chineseName").value("美元"))
                .andExpect(jsonPath("$.currencies.USD.rate").exists());
    }
}
