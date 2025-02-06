package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountDao extends JpaRepository<AccountEntity, UUID> {

    Page<AccountEntity> findAllByUser(UUID id, Pageable pageable);
}
