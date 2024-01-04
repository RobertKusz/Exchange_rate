package pl.Exchange_Rate.Exchange_Rate.domain.currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Optional<Currency> findByCurrencyCode(String code);

}

