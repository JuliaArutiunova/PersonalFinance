package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "operation", schema = "account_data")
public class OperationEntity {

    @Id
    @Column(name = "operation_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "user_id_FK"))
    private AccountEntity account;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    @Version
    private LocalDateTime dtUpdate;

    @Column
    private LocalDate date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "operation_category_id", foreignKey = @ForeignKey(name = "operation_category_FK"))
    private OperationCategoryIdEntity category;

    @Column
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "currency_id", foreignKey = @ForeignKey(name = "currency_FK"))
    private CurrencyInfoEntity currency;


    @PrePersist
    private void prePersist(){
        this.dtCreate = LocalDateTime.now();
        this.dtUpdate = this.dtCreate;
    }
}
