package by.it_academy.jd2.dao.entity;

import by.it_academy.lib.enums.EssenceType;
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
@Table(name = "audit", schema = "audit_data")
public class AuditEntity {
    @Id
    @Column(name = "audit_id")
    private UUID auditId;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_fk"))
    private UserInfoEntity user;

    @Column
    private String text;

    @Column(name = "essence_type")
    @Enumerated(EnumType.STRING)
    private EssenceType essenceType;

    @Column(name = "entity_id")
    private UUID entityId;


    @PrePersist
    private void prePersist(){
        this.dtCreate = LocalDateTime.now();
        this.dtUpdate = this.dtCreate;
    }
}
