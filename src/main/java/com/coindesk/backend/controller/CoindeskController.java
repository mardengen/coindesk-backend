package com.coindesk.backend.controller;

import com.coindesk.backend.dto.CoindeskResponseDTO;
import com.coindesk.backend.service.CoindeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskService coindeskService;

    @GetMapping("/original")
    public String getOriginal() {
        return coindeskService.getOriginalData();
    }

    @GetMapping("/converted")
    public CoindeskResponseDTO getConverted() {
        return coindeskService.getConvertedData();
    }
}
