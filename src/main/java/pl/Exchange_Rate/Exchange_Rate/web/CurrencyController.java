package pl.Exchange_Rate.Exchange_Rate.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyService;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/waluta/{code}")
    String getCurrencyInfo(Model model, @PathVariable String code,
                           @PathVariable(required = false) LocalDate from,
                           @PathVariable(required = false) LocalDate to){
        CurrencyStatsDto currencyInfo = currencyService.getCurrencyInfo(code);
        model.addAttribute("currency",currencyInfo);
        Map<String, Double> currencyValueFromTo = currencyService.getCurrencyValueFromTo(code, from, to);
        model.addAttribute("valuesFromTo", currencyValueFromTo);


        return "single_currency";
    }
}
