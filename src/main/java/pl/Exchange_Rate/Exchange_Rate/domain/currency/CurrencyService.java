package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import pl.Exchange_Rate.Exchange_Rate.API.DataMenager.DataManager;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
                .map(CurrencyDtoMapper::map)
                .collect(Collectors.toList());
    }

    public void fetchDataAndSaveToDatabase() {
            dataManager.convertAndSaveData();
    }
}
