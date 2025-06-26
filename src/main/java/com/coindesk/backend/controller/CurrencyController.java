package com.coindesk.backend.controller;

import com.coindesk.backend.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.coindesk.backend.repository.CurrencyRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping
    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    @PostMapping
    public Currency create(@RequestBody Currency currency) {
        return currencyRepository.save(currency);
    }

    @PutMapping("/{code}")
    public Currency update(@PathVariable String code, @RequestBody Currency currency) {
        Currency existing = currencyRepository.findById(code)
                .orElseThrow(() -> new NoSuchElementException("Currency not found: " + code));

        existing.setChineseName(currency.getChineseName());
        return currencyRepository.save(existing);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) {
        currencyRepository.deleteById(code);
    }
}
