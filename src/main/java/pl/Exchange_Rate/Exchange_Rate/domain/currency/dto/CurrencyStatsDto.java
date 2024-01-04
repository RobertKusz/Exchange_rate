package pl.Exchange_Rate.Exchange_Rate.domain.currency.dto;

import java.time.LocalDate;

public class CurrencyStatsDto {
    private String name;
    private String currencyCode;
    private double mid;
    private double change;
    private LocalDate dateTime;
    private double yearMax;
    private double yearMin;


    public CurrencyStatsDto(String name,
                            String currencyCode,
                            double mid,
                            double change,
                            LocalDate dateTime,
                            double yearMax,
                            double yearMin) {
        this.name = name;
        this.currencyCode = currencyCode;
        this.mid = mid;
        this.change = change;
        this.dateTime = dateTime;
        this.yearMax = yearMax;
        this.yearMin = yearMin;
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

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
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


    @Override
    public String toString() {
        return "CurrencyStatsDto{" +
                "name='" + name + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", mid=" + mid +
                ", change=" + change +
                ", dateTime=" + dateTime +
                ", yearMax=" + yearMax +
                ", yearMin=" + yearMin +
                '}';
    }
}
