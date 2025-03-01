package by.it_academy.jd2.dao.entity;

import by.it_academy.lib.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "user_info", schema = "audit_data")
public class UserInfoEntity {
    @Id
    @Column(name = "user_id")
    private UUID userId;
    @Column
    private String fio;
    @Column
    private String mail;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
