package pl.Exchange_Rate.Exchange_Rate.API.DataMenager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.api_connetion.NbpApi;
import pl.Exchange_Rate.Exchange_Rate.API.json_parsing.JsonUtils;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.Currency;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataManager {
    private final CurrencyRepository currencyRepository;
    private final NbpApi nbpApi;

    public DataManager(CurrencyRepository currencyRepository, NbpApi nbpApi) {
        this.currencyRepository = currencyRepository;
        this.nbpApi = nbpApi;
    }
    @Transactional
    public void convertAndSaveData() {
        try {
            String dataNbpApi = nbpApi.getTodaysCurrency();
            JsonNode parsedData = JsonUtils.parse(dataNbpApi);

            JsonNode ratesNode = parsedData.get(0).path("rates");

            if (parsedData.isArray() && !parsedData.isEmpty()) {
                if (!ratesNode.isMissingNode() && ratesNode.isArray()) {
                    for (JsonNode rate : ratesNode) {
                        Currency currency = new Currency();
                        currency.setName(rate.path("currency").asText());
                        currency.setCurrencyCode(rate.path("code").asText());
                        currency.setMid(rate.path("mid").asDouble());
                        currencyRepository.save(currency);
                    }
                } else {
                    System.out.println("BŁĄD: Brak danych w polu 'rates'");
                    System.out.println("Dane z API: " + dataNbpApi);
                }
            } else {
                System.out.println("BŁĄD: Nieprawidłowy format odpowiedzi z API");
                System.out.println("Dane z API: " + dataNbpApi);
            }
        } catch (JsonProcessingException e) {
            System.out.println("BŁĄD: Problem z parsowaniem danych JSON");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("BŁĄD: Inny problem");
            e.printStackTrace();
        }
    }
    public List<Currency> get52WeeksData(String code) throws JsonProcessingException {
        String dataNbpApi = nbpApi.get52WeeksData(code);
        JsonNode parsedData = JsonUtils.parse(dataNbpApi);

        JsonNode ratesNode = parsedData.path("rates");
        List<Currency> currencies = new ArrayList<>();
        for (JsonNode rate : ratesNode) {
            Currency currency = new Currency();
            currency.setName(rate.path("currency").asText());
            currency.setCurrencyCode(rate.path("code").asText());
            currency.setMid(rate.path("mid").asDouble());
            currencies.add(currency);
        }
        return currencies;


        //  skonczone tutaj, chciałbym zeby ta klasa zwracała dane sprzed 52 tygodni
        //plan na to jest zeby wspólny kod z klasy wyzej wydzielić do osobniej klasy i wydobyć dane
    }

}