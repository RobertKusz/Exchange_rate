package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.DataMenager.DataManager;
import pl.Exchange_Rate.Exchange_Rate.database.DataProcessor;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void fetchDataAndSaveToDatabase() {
        //List<Currency> currencies = dataManager.convertAndSaveData(); // usunąć/ to było d
        dataManager.convertAndSaveData();

        List<Currency> currenciesToSaveInRepository = StreamSupport.stream(currencyRepository.findAll().spliterator(), false)
                .toList();

        DataProcessor.saveDataToDatabase(currenciesToSaveInRepository);
    }

    public CurrencyStatsDto getCurrencyInfo(String code){
//        Currency currency = currencyRepository.findByCurrencyCode(code).orElseThrow();
        try {
            List<CurrencyStatsDto> currencies = dataManager.get52WeeksData(code);
            return calculateCurrencyStats(currencies, code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private CurrencyStatsDto calculateCurrencyStats(List<CurrencyStatsDto> currencies, String code) {
        Currency currency = currencyRepository.findByCurrencyCode(code).orElseThrow();

        currency.setMid(currencies.get(currencies.size()-1).getMid());
        CurrencyStatsDto currencyStatsDto = CurrencyDtoMapper.mapToStatsDto(currency);

        currencyStatsDto.setYearMin(getMinValue(currencies));
        currencyStatsDto.setYearMax(getMaxValue(currencies));
        currencyStatsDto.setCurrencyChange(getValueChange(currencies));
        currencyStatsDto.setDateTime(LocalDate.now());

        return currencyStatsDto;
    }

    public Map<String, Double> getCurrencyValueFromTo(String code, LocalDate from, LocalDate to){

        if(from == null){
            from = LocalDate.now().minusDays(7);
        }
        if(to == null){
            to = LocalDate.now();
        }
        Currency currency = currencyRepository.findByCurrencyCode(code).orElseThrow();
        try {
            return dataManager.getMapDataFromTo(code, from, to);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

        double change = (latestMidValue - newestMidValue) / latestMidValue * 100;

        return Math.round(change * 100.0) / 100.0;


    }
}
