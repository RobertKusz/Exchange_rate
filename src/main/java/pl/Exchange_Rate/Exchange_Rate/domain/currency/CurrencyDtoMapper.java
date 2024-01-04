package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

public class CurrencyDtoMapper {
    static CurrencyHomePageDto mapToHomePageDto(Currency currency){
        return new CurrencyHomePageDto(
                currency.getName(),
                currency.getCurrencyCode(),
                currency.getMid()
        );
    }

    static CurrencyStatsDto mapToStatsDto(Currency currency){
        return new CurrencyStatsDto(
                currency.getName(),
                currency.getCurrencyCode(),
                currency.getMid(),
                currency.getChange(),
                currency.getDateTime(),
                currency.getYearMax(),
                currency.getYearMin()
        );
    }
}
