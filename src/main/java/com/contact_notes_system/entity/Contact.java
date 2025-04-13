package com.contact_notes_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//This is Contact Entity which will map to the contact table directly using Java Hibernate ORM features.
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 2, message = "First Name must be at least 2 characters")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Last Name cannot be blank")
    @Size(min = 2, message = "Last Name must be at least 2 characters")
    private String lastName;
    @Column(name = "email",nullable = false,unique = true)
    @Email
    private String email;
    @Column(name = "phone",unique = true)
    private int phone;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Notes> notes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

}
