package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import pl.Exchange_Rate.Exchange_Rate.API.DataMenager.DataManager;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrencyServiceTest {

    @Mock CurrencyRepository currencyRepository;
    @Mock DataManager dataManager;
    @InjectMocks CurrencyService currencyService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }


    //    getAllCurrencies --------------------------------------
    @Test
    void shouldReturnAllCurrencies(){
        //given
        Currency currency1 = createCurrency(1L,"test1","T01", 3.0);
        Currency currency2 = createCurrency(2L,"test2","T02", 4.0);
        List<Currency> currencies = List.of(currency1, currency2);

        when(currencyRepository.findAll()).thenReturn(currencies);
        //when
        List<CurrencyHomePageDto> currenciesHomePageDto = currencyService.getAllCurrencies();
        //then
        CurrencyHomePageDto currencyHomePageDto = currenciesHomePageDto.get(0);
        assertThat(currencyHomePageDto.getName()).isEqualTo(currency1.getName());
        assertThat(currencyHomePageDto.getCurrencyCode()).isEqualTo(currency1.getCurrencyCode());
        assertThat(currencyHomePageDto.getMid()).isEqualTo(currency1.getMid());
    }
    //    getAllCurrencies --------------------------------------
    //    getCurrencyInfo----------------------------------------
    @Test
    void shouldReturnCorrectCurrencyInfo() throws JsonProcessingException {
        //given
        String code = "test";
        CurrencyStatsDto currency1 = createCurrencyStatsDto("test1" ,code, 5.0);
        CurrencyStatsDto currency2 = createCurrencyStatsDto("test1" ,code, 4.0);
        CurrencyStatsDto currency3 = createCurrencyStatsDto("test1" ,code, 3.0);
        List<CurrencyStatsDto> currencies = List.of(currency1, currency2, currency3);

        when(dataManager.get52WeeksData(code)).thenReturn(currencies);

        Currency currency = createCurrency(1L, "test1", code, 3.0);
        when(currencyRepository.findByCurrencyCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(currency));

        //when
        CurrencyStatsDto currencyInfo = currencyService.getCurrencyInfo(code);
        //then
        assertThat(currencyInfo.getName()).isEqualTo("test1");
        assertThat(currencyInfo.getCurrencyCode()).isEqualTo(code);
        assertThat(currencyInfo.getYearMax()).isEqualTo(5.0);
        assertThat(currencyInfo.getYearMin()).isEqualTo(3.0);
        assertThat(currencyInfo.getCurrencyChange()).isEqualTo(40.00);
    }
    //    getCurrencyInfo----------------------------------------


    Currency createCurrency(Long id, String name, String code, double mid){
        Currency currency = mock(Currency.class);
        when(currency.getId()).thenReturn(id);
        when(currency.getName()).thenReturn(name);
        when(currency.getCurrencyCode()).thenReturn(code);
        when(currency.getMid()).thenReturn(mid);
        return currency;
    }
    CurrencyStatsDto createCurrencyStatsDto(String name, String code, double mid){
        CurrencyStatsDto currency = mock(CurrencyStatsDto.class);
        when(currency.getName()).thenReturn(name);
        when(currency.getCurrencyCode()).thenReturn(code);
        when(currency.getMid()).thenReturn(mid);
        when(currency.getDateTime()).thenReturn(LocalDate.now());
        return currency;
    }

}