package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Optional<Currency> findByCurrencyCode(String code);
    @Modifying
    @Query("UPDATE Currency c SET c.mid = :mid WHERE c.currencyCode = :currencyCode")
    void updateCurrencyMid(@Param("currencyCode") String currencyCode, @Param("mid") Double mid);


}

