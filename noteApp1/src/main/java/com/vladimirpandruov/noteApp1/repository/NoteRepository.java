package com.vladimirpandruov.noteApp1.repository;

import com.vladimirpandruov.noteApp1.domain.Note;
import com.vladimirpandruov.noteApp1.enumeration.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByLevel(Level level);
    void deleteNoteById(Long id);
}
