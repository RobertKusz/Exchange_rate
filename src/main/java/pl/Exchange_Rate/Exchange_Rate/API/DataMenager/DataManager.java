package pl.Exchange_Rate.Exchange_Rate.API.DataMenager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.api_connetion.NbpApi;
import pl.Exchange_Rate.Exchange_Rate.API.json_parsing.JsonUtils;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.Currency;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyRepository;

import java.util.Iterator;

@Service
public class DataManager {
    private final CurrencyRepository currencyRepository;

    public DataManager(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    @Transactional
    public void convertAndSaveData() {
        NbpApi nbpApi = new NbpApi();
        try {
            String dataNbpApi = nbpApi.getDataNbpApi();
            JsonNode parsedData = JsonUtils.parse(dataNbpApi);

            JsonNode ratesNode = parsedData.get(0).path("rates");
            if (parsedData.isArray() && parsedData.size() > 0) {
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
}