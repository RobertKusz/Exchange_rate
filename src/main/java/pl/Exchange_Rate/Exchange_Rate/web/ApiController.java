package pl.Exchange_Rate.Exchange_Rate.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.Exchange_Rate.Exchange_Rate.API.ApiService;

@Controller
public class ApiController {
    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }


    @GetMapping("/api")
    String getApi(){
        return "api";
    }


    @GetMapping("/api/all")
    @ResponseBody
    String getAllCurrencies(){
        return apiService.getAllCurrencies();
    }

    @GetMapping("/api/rates/{code}")
    @ResponseBody
    String getAllCurrencies(@PathVariable String code){
        return apiService.getCurrencyByCode(code);
    }

    @GetMapping("/api/rates/{code}/last/{topCount}")
    @ResponseBody
    String getLastRatingsCurrencyByCode(@PathVariable String code, @PathVariable int topCount){
        return apiService.getLastRatingsCurrencyByCode(code,topCount);
    }
}