package pl.Exchange_Rate.Exchange_Rate.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.DataMenager.DataManager;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.Currency;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyRepository;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyApiFromTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
public class ApiService {
    private final CurrencyRepository currencyRepository;
    private final DataManager dataManager;

    public ApiService(CurrencyRepository currencyRepository, DataManager dataManager) {
        this.currencyRepository = currencyRepository;
        this.dataManager = dataManager;
    }

    public String getAllCurrencies(){
        try {
            List<Currency> currencies = StreamSupport
                    .stream(currencyRepository.findAll().spliterator(), false)
                    .toList();
            return dataManager.convertObjectsToJson(currencies);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrencyByCode(String code){
        try {
            Currency currency = currencyRepository.findByCurrencyCode(code).orElseThrow();
            return dataManager.convertObjectToJson(currency);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public String getLastRatingsCurrencyByCode(String code, int topCount) {
        try {
            LocalDate to = LocalDate.now();
            LocalDate from = LocalDate.now().minusDays(topCount);
            List<CurrencyApiFromTo> listDataFromTo = dataManager.getListDataFromTo(code, from, to);

            return dataManager.convertObjectsToJson(listDataFromTo);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
