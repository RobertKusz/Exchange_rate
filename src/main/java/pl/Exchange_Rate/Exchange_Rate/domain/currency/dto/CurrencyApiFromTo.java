package pl.Exchange_Rate.Exchange_Rate.domain.currency.dto;

import java.time.LocalDate;

public class CurrencyApiFromTo {
    String effectiveDate;
    Double mid;

    public CurrencyApiFromTo(String effectiveDate, Double mid) {
        this.effectiveDate = effectiveDate;
        this.mid = mid;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }
}
