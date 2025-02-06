package by.it_academy.jd2.user_service.dao.entity;

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
@Table(name = "verification_info", schema = "user_data")
public class VerificationEntity {
    @Id
    @Column(name = "verification_id")
    private UUID verificationId;

    @Column
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id_FK"))
    private UserEntity userEntity;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @PrePersist
    private void PrePersist(){
        this.dtCreate = LocalDateTime.now();
    }

}
