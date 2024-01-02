package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;

public class CurrencyDtoMapper {
    static CurrencyHomePageDto map(Currency currency){
        return new CurrencyHomePageDto(
                currency.getName(),
                currency.getCurrencyCode(),
                currency.getMid()
        );
    }
}
