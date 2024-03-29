package pl.Exchange_Rate.Exchange_Rate.domain.currency.dto;

import java.time.LocalDate;

public class CurrencyStatsDto {
    private String name;
    private String currencyCode;
    private double mid;
    private double currencyChange;
    private LocalDate dateTime;
    private double yearMax;
    private double yearMin;

    public CurrencyStatsDto() {
    }

    public CurrencyStatsDto(String name,
                            String currencyCode,
                            double mid) {
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

    public double getCurrencyChange() {
        return currencyChange;
    }

    public void setCurrencyChange(double change) {
        this.currencyChange = change;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public double getYearMax() {
        return yearMax;
    }

    public void setYearMax(double yearMax) {
        this.yearMax = yearMax;
    }

    public double getYearMin() {
        return yearMin;
    }

    public void setYearMin(double yearMin) {
        this.yearMin = yearMin;
    }


}
