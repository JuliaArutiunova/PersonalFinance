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

@Table(name = "user", schema = "user_data")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column
    private String fio;

    @Column
    private String mail;

    @Column
    private String password;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;


    @PrePersist
    private void prePersist(){
        this.dtCreate = LocalDateTime.now();
        this.dtUpdate = this.dtCreate;
    }
}
