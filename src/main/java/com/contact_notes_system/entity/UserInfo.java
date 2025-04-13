package com.contact_notes_system.entity;

import jakarta.persistence.*;
import lombok.*;

//This is UserInfo Entity which will map to the UserInfo table directly using Java Hibernate ORM features.
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role")
    private String role;
}
