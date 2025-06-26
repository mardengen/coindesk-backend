package com.coindesk.backend.controller;

import com.coindesk.backend.service.CoindeskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CoindeskControllerTest {

    @Autowired
    private CoindeskService coindeskService;

    @Test
    public void testGetOriginal() {
        String data = coindeskService.getOriginalData();
        assertThat(data).isNotNull();
    }

    @Test
    public void testGetConverted() {
        assertThat(coindeskService.getConvertedData()).isNotNull();
    }
}
