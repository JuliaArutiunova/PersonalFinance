package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IOperationDao extends JpaRepository<OperationEntity, UUID> {
    Page<OperationEntity> findAllByAccountId(UUID id, Pageable pageable);

    @Query("SELECT o FROM OperationEntity o WHERE o.id = :operationId AND o.account.id = :accountId")
    OperationEntity findByOperationIdAndAccountId(@Param("operationId") UUID operationId,
                                                  @Param("accountId") UUID accountId);
}
