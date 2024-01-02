package pl.Exchange_Rate.Exchange_Rate.domain.currency.dto;

public class CurrencyHomePageDto {
    private String name;
    private String currencyCode;
    private double mid;

    public CurrencyHomePageDto(String name, String currencyCode, double mid) {
        this.name = name;
        this.currencyCode = currencyCode;
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }
}
