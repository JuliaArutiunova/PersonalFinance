package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICurrencyDao extends JpaRepository<CurrencyEntity, UUID> {
    boolean existsByTitle(String title);
}
