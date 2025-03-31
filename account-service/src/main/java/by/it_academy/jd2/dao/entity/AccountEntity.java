package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "account", schema = "account_data")
public class AccountEntity {
    @Id
    @Column(name = "account_id")
    private UUID id;

    @Column(name = "user_id")
    private UUID user;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    @Version
    private LocalDateTime dtUpdate;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private BigDecimal balance;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountType type;


    @ManyToOne
    @JoinColumn(name = "currency_id", foreignKey = @ForeignKey(name = "currency_id_FK"))
    private CurrencyInfoEntity currency;


    @PrePersist
    private void prePersist(){
        this.dtCreate = LocalDateTime.now();
        this.dtUpdate = this.dtCreate;
    }

}
