package com.coindesk.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coindesk.backend.dto.CoindeskResponseDTO;
import com.coindesk.backend.dto.CurrencyDTO;
import com.coindesk.backend.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.coindesk.backend.repository.CurrencyRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoindeskService {

    private static final String COINDESK_URL = "https://kengp3.github.io/blog/coindesk.json";

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public String getOriginalData() {
        return restTemplate.getForObject(COINDESK_URL, String.class);
    }

    public CoindeskResponseDTO getConvertedData() {
        String json = getOriginalData();
        try {
            JsonNode root = objectMapper.readTree(json);
            String updateTime = root.path("time").path("updatedISO").asText();

            JsonNode bpi = root.path("bpi");
            List<CurrencyDTO> list = new ArrayList<>();
            bpi.fieldNames().forEachRemaining(code -> {
                JsonNode node = bpi.path(code);
                String currency = node.path("code").asText();
                String rate = node.path("rate_float").asText();

                Currency curEntity = currencyRepository.findById(currency).orElse(null);
                String chineseName = curEntity != null ? curEntity.getChineseName() : "";

                CurrencyDTO dto = new CurrencyDTO(currency, chineseName, rate);
                list.add(dto);
            });

            return new CoindeskResponseDTO(formatTime(updateTime), list);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatTime(String iso) {
        OffsetDateTime time = OffsetDateTime.parse(iso);
        return time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
