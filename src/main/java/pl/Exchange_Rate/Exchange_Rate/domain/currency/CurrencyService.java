package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.DataMenager.DataManager;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.time.LocalTime.now;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final DataManager dataManager;

    public CurrencyService(CurrencyRepository currencyRepository, DataManager dataManager) {
        this.currencyRepository = currencyRepository;
        this.dataManager = dataManager;
    }

    public List<CurrencyHomePageDto> getAllCurrencies(){
        return StreamSupport.stream(currencyRepository.findAll().spliterator(),false)
                .map(CurrencyDtoMapper::mapToHomePageDto)
                .collect(Collectors.toList());
    }

    public void fetchDataAndSaveToDatabase() {
            dataManager.convertAndSaveData();
    }

    public CurrencyStatsDto getCurrencyInfo(String code){
        Currency currency = currencyRepository.findByCurrencyCode(code).orElseThrow();
        try {
            List<CurrencyStatsDto> currencies = dataManager.get52WeeksData(code)
                    .stream()
                    .map(CurrencyDtoMapper::mapToStatsDto)
                    .toList();
            return calculateCurrenciesStats(currencies);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private CurrencyStatsDto calculateCurrenciesStats(List<CurrencyStatsDto> currencies) {
        CurrencyStatsDto currencyStatsDto = currencies.get(currencies.size()-1);
        currencyStatsDto.setYearMin(getMinValue(currencies));
        currencyStatsDto.setYearMax(getMaxValue(currencies));
        currencyStatsDto.setChange(getValueChange(currencies));
        currencyStatsDto.setDateTime(LocalDate.now());
        return currencyStatsDto;
    }

    private double getMaxValue(List<CurrencyStatsDto> currencies) {
        double max = currencies.get(0).getMid();
        for (CurrencyStatsDto currency : currencies) {
            if (currency.getMid() > max)
                max = currency.getMid();
        }
        return max;
    }

    private double getMinValue(List<CurrencyStatsDto> currencies) {
        double min = currencies.get(0).getMid();
        for (CurrencyStatsDto currency : currencies) {
            if (currency.getMid() < min)
                min = currency.getMid();
        }
        return min;
    }

    private double getValueChange(List<CurrencyStatsDto> currencies) {
        int size = currencies.size();
        double latestMidValue = currencies.get(0).getMid();
        double newestMidValue = currencies.get(size-1).getMid();

        return (latestMidValue - newestMidValue) / latestMidValue * 100;
    }
}
