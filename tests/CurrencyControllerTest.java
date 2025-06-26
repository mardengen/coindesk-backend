package tests;

import app.repository.CurrencyRepository;
import app.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CurrencyControllerTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testFindAll() {
        assertThat(currencyRepository.findAll()).isNotEmpty();
    }

    @Test
    public void testSaveCurrency() {
        Currency c = new Currency("JPY", "日圓");
        currencyRepository.save(c);
        assertThat(currencyRepository.findById("JPY")).isPresent();
    }
}
