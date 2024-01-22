package pl.Exchange_Rate.Exchange_Rate.web;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyService;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private Model model;

    @InjectMocks
    private CurrencyController currencyController;


    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrencyInfo() {
        // Given
        String code = "USD";
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 31);
        CurrencyStatsDto currencyStatsDto = createMockCurrencyStatsDto();
        Map<String, Double> currencyValueFromTo = createMockCurrencyValueFromTo();

        when(currencyService.getCurrencyInfo(code)).thenReturn(currencyStatsDto);
        when(currencyService.getCurrencyValueFromTo(code, from, to)).thenReturn(currencyValueFromTo);

        // When
        String result = currencyController.getCurrencyInfo(model, code, from, to);

        // Then
        Assertions.assertThat(result).isEqualTo("single_currency");

        verify(model).addAttribute("currency", currencyStatsDto);
        verify(model).addAttribute("valuesFromTo", currencyValueFromTo);
    }

    private CurrencyStatsDto createMockCurrencyStatsDto() {
        return new CurrencyStatsDto("USD", "Test Currency", 1.0);
    }

    private Map<String, Double> createMockCurrencyValueFromTo() {
        Map<String, Double> currencyValueFromTo = new HashMap<>();
        currencyValueFromTo.put("2022-01-01", 1.1);
        currencyValueFromTo.put("2022-01-02", 1.2);
        return currencyValueFromTo;
    }
}
