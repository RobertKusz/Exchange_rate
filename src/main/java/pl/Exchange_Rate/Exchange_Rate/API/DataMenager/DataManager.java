package pl.Exchange_Rate.Exchange_Rate.API.DataMenager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.api_connetion.NbpApi;
import pl.Exchange_Rate.Exchange_Rate.API.json_parsing.JsonUtils;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.Currency;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyRepository;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyApiFromTo;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;
import pl.Exchange_Rate.Exchange_Rate.exceptions.exception.JsonParsingException;

import java.time.LocalDate;
import java.util.*;

@Service
public class DataManager {
    private final CurrencyRepository currencyRepository;
    private final NbpApi nbpApi;

    public DataManager(CurrencyRepository currencyRepository, NbpApi nbpApi) {
        this.currencyRepository = currencyRepository;
        this.nbpApi = nbpApi;
    }
    @Transactional
    public List<Currency> convertAndSaveData() {
        try {
            String dataNbpApi = nbpApi.getTodaysCurrency();
            JsonNode parsedData = JsonUtils.parse(dataNbpApi);

            List<Currency> currencies = new ArrayList<>();
            JsonNode ratesNode = parsedData.get(0).path("rates");

//            if (parsedData.isArray() && !parsedData.isEmpty()) {
//                if (!ratesNode.isMissingNode() && ratesNode.isArray()) {
                    for (JsonNode rate : ratesNode) {
                        Currency currency = createCurrency(rate);
                        currencies.add(currency);
                        currencyRepository.updateCurrencyMid(rate.path("code").asText(), rate.path("mid").asDouble());
                    }
//                } else {
//                    System.out.println("BŁĄD: Brak danych w polu 'rates'");
//                    System.out.println("Dane z API: " + dataNbpApi);
//                }
//            } else {
//                System.out.println("BŁĄD: Nieprawidłowy format odpowiedzi z API");
//                System.out.println("Dane z API: " + dataNbpApi);
//            }
            return currencies;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(e);
        }
    }

    private Currency createCurrency(JsonNode rate){
        Currency currency = new Currency();
        currency.setName(rate.path("currency").asText());
        currency.setCurrencyCode(rate.path("code").asText());
        currency.setMid(rate.path("mid").asDouble());
        return currency;
    }


    public List<CurrencyStatsDto> get52WeeksData(String code) throws JsonProcessingException {
        String dataNbpApi = nbpApi.get52WeeksData(code);
        JsonNode parsedData = JsonUtils.parse(dataNbpApi);

        JsonNode ratesNode = parsedData.path("rates");
        List<CurrencyStatsDto> currencies = new ArrayList<>();
        for (JsonNode rate : ratesNode) {
            CurrencyStatsDto currency = createCurrStatsDto(code, rate);
            currencies.add(currency);
        }
        return currencies;
    }
    private static CurrencyStatsDto createCurrStatsDto(String code, JsonNode rate) {
        CurrencyStatsDto currency = new CurrencyStatsDto();
        currency.setCurrencyCode(code);
        currency.setMid(rate.path("mid").asDouble());
        currency.setDateTime(LocalDate.parse(rate.path("effectiveDate").asText()));
        return currency;
    }

    public Map<String, Double> getMapDataFromTo(String code, LocalDate from, LocalDate to) throws JsonProcessingException {
        String dataNbpApi = nbpApi.getDataFromTo(code, from, to);
        JsonNode parsedData = JsonUtils.parse(dataNbpApi);

        JsonNode ratesNode = parsedData.path("rates");
        return createCurrencyValuesMap(ratesNode);
    }

    private static Map<String, Double> createCurrencyValuesMap(JsonNode ratesNode) {
        Map<String, Double> currencyValues = new LinkedHashMap<>();
        for (JsonNode rate : ratesNode) {
            String valueData = rate.path("effectiveDate").asText();
            double mid = rate.path("mid").asDouble();
            currencyValues.put(valueData, mid);
        }
        return currencyValues;
    }

    public String convertObjectsToJson(Object currencies) throws JsonProcessingException {
        return JsonUtils.parseCurrencyToJson(currencies);
    }
    public String convertObjectToJson(Currency currency) throws JsonProcessingException {
        return JsonUtils.parseCurrencyToJson(currency);
    }

    public List<CurrencyApiFromTo> getListDataFromTo(String code, LocalDate from, LocalDate to) throws JsonProcessingException {
        String dataNbpApi = nbpApi.getDataFromTo(code, from, to);
        JsonNode parsedData = JsonUtils.parse(dataNbpApi);
        JsonNode ratesNode = parsedData.path("rates");

        return getCurrencyApiFromToList(ratesNode);
    }

    private static List<CurrencyApiFromTo> getCurrencyApiFromToList(JsonNode ratesNode) {
        List<CurrencyApiFromTo> currenciesValue = new ArrayList<>();
        for (JsonNode rate : ratesNode) {
            String effectiveDate = rate.path("effectiveDate").asText();
            double mid = rate.path("mid").asDouble();
            currenciesValue.add(new CurrencyApiFromTo(effectiveDate,mid));
        }
        return currenciesValue;
    }
}