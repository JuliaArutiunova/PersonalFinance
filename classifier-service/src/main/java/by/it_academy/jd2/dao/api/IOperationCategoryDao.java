package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.OperationCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOperationCategoryDao extends JpaRepository<OperationCategoryEntity, UUID> {
    boolean existsByTitle(String title);
}
