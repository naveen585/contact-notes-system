package com.contact_notes_system.repository;

import com.contact_notes_system.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Useful for accessing the Notes data from the table, and we can avoid the sql queries by using JPA.
@Repository
public interface NotesRepository extends JpaRepository<Notes,Long> {
    List<Notes> findByContactId(Long contactId);
}
