package pl.Exchange_Rate.Exchange_Rate.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.Exchange_Rate.Exchange_Rate.API.DataMenager.DataManager;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.CurrencyService;
import pl.Exchange_Rate.Exchange_Rate.domain.currency.dto.CurrencyHomePageDto;

import java.util.List;

@Controller
public class HomeController {
    private final CurrencyService currencyService;

    public HomeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    String home(Model model){
//        currencyService.fetchDataAndSaveToDatabase();
        List<CurrencyHomePageDto> currencies = currencyService.getAllCurrencies();
        model.addAttribute("currencies", currencies);
        return "homePage";
    }
}