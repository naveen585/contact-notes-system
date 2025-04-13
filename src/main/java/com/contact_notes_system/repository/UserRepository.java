package com.contact_notes_system.repository;

import com.contact_notes_system.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Useful for accessing the userinfo data from the table, and we can avoid the sql queries by using JPA.
@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByUsername(String username);
}
