package pl.Exchange_Rate.Exchange_Rate.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyService;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyStatsDto;

@Controller
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/waluta/{code}")
    String getCurrencyInfo(Model model, @PathVariable String code){
        CurrencyStatsDto currencyInfo = currencyService.getCurrencyInfo(code);
        System.out.println(currencyInfo.toString());
        model.addAttribute("currency",currencyInfo);
        return "single_currency";
    }
}
