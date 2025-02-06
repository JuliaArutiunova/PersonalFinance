package by.it_academy.jd2.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "currency", schema = "account_data")
public class CurrencyIdEntity {
    @Id
    @Column(name = "currency_id")
    private UUID id;
}
