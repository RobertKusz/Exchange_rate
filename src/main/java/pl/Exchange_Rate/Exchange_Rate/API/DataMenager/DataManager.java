package pl.Exchange_Rate.Exchange_Rate.API.DataMenager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.api_connetion.NbpApi;
import pl.Exchange_Rate.Exchange_Rate.API.json_parsing.JsonUtils;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.Currency;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyRepository;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

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

            JsonNode ratesNode = parsedData.get(0).path("rates");
            List<Currency> currencies = new ArrayList<>();

            if (parsedData.isArray() && !parsedData.isEmpty()) {
                if (!ratesNode.isMissingNode() && ratesNode.isArray()) {
                    for (JsonNode rate : ratesNode) {
                        Currency currency = new Currency();
                        currency.setName(rate.path("currency").asText());
                        currency.setCurrencyCode(rate.path("code").asText());
                        currency.setMid(rate.path("mid").asDouble());
                        currencies.add(currency);
                    }
                } else {
                    System.out.println("BŁĄD: Brak danych w polu 'rates'");
                    System.out.println("Dane z API: " + dataNbpApi);
                }
            } else {
                System.out.println("BŁĄD: Nieprawidłowy format odpowiedzi z API");
                System.out.println("Dane z API: " + dataNbpApi);
            }
            return currencies;
        } catch (JsonProcessingException e) {
            System.out.println("BŁĄD: Problem z parsowaniem danych JSON");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("BŁĄD: Inny problem");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public List<CurrencyStatsDto> get52WeeksData(String code) throws JsonProcessingException {
        String dataNbpApi = nbpApi.get52WeeksData(code);
        JsonNode parsedData = JsonUtils.parse(dataNbpApi);

        JsonNode ratesNode = parsedData.path("rates");
        List<CurrencyStatsDto> currencies = new ArrayList<>();
        for (JsonNode rate : ratesNode) {
            CurrencyStatsDto currency = new CurrencyStatsDto();
            currency.setCurrencyCode(code);
            currency.setMid(rate.path("mid").asDouble());
            currency.setDateTime(LocalDate.parse(rate.path("effectiveDate").asText()));
            currencies.add(currency);
        }
        return currencies;
    }
    public Map<String, Double> getDataFromTo(String code, LocalDate from, LocalDate to) throws JsonProcessingException {
        String dataNbpApi = nbpApi.getDataFromTo(code, from, to);
        JsonNode parsedData = JsonUtils.parse(dataNbpApi);

        JsonNode ratesNode = parsedData.path("rates");
        Map<String, Double> currencyValues= new LinkedHashMap<>();
        for (JsonNode rate : ratesNode) {
            String valueData = rate.path("effectiveDate").asText();
            double mid = rate.path("mid").asDouble();
            currencyValues.put(valueData, mid);
        }
        return currencyValues;
    }
}