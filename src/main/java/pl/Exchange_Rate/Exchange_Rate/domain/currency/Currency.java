package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String currencyCode;
    private double mid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public double getCurrencyChange() {
//        return currencyChange;
//    }
//
//    public void setCurrencyChange(double currencyChange) {
//        this.currencyChange = currencyChange;
//    }
//
//    public LocalDate getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(LocalDate dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    public double getYearMax() {
//        return yearMax;
//    }
//
//    public void setYearMax(double yearMax) {
//        this.yearMax = yearMax;
//    }
//
//    public double getYearMin() {
//        return yearMin;
//    }
//
//    public void setYearMin(double yearMin) {
//        this.yearMin = yearMin;
//    }
}
