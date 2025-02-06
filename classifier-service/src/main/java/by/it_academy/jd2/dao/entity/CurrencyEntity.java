package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "currency", schema = "classifier_data")
public class CurrencyEntity {
    @Id
    @Column(name = "currency_id")
    private UUID id;

    @Column(name = "dt_create")

    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column
    private String title;

    @Column
    private String description;



    @PrePersist
    private void prePersist(){
        this.dtCreate = LocalDateTime.now();
        this.dtUpdate = this.dtCreate;
    }


}
