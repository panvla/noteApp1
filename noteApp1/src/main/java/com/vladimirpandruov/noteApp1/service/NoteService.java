package com.vladimirpandruov.noteApp1.service;

import com.vladimirpandruov.noteApp1.domain.HttpResponse;
import com.vladimirpandruov.noteApp1.domain.Note;
import com.vladimirpandruov.noteApp1.enumeration.Level;
import com.vladimirpandruov.noteApp1.exception.NoteNotFoundException;
import com.vladimirpandruov.noteApp1.repository.NoteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vladimirpandruov.noteApp1.util.DataUtil.dateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    public HttpResponse<Note> getNotes(){
        log.info("Fetching all the notes from the database");
        return HttpResponse.<Note>builder()
                .notes(noteRepository.findAll())
                .message(noteRepository.count() > 0 ? noteRepository.count() + " notes retrieved" : "No notes to display")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    public HttpResponse<Note> filterNotes(Level level) {
        List<Note> notes = noteRepository.findByLevel(level);
        log.info("Fetching all the notes by level {}", level);
        return HttpResponse.<Note>builder()
                .notes(notes)
                .message(notes.size() + " notes are of " + level + " importance")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    public HttpResponse<Note> saveNote(Note note){
        log.info("Saving new note to the database");
        note .setCreatedAt(LocalDateTime.now());
        Note newNote = noteRepository.save(note);
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(newNote))
                .message("Note created successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    public HttpResponse<Note> updateNote(Note note) throws NoteNotFoundException{
        log.info("Updating note to the database");
        Optional<Note> optionalNote = Optional.ofNullable(noteRepository.findById(note.getId()).orElseThrow(() -> new NoteNotFoundException("The note was not found on the server")));
        Note updateNote = optionalNote.get();
        updateNote.setId(note.getId());
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());
        updateNote.setLevel(note.getLevel());
        noteRepository.save(updateNote);
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(updateNote))
                .message("Note updated successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    public HttpResponse<Note> deleteNote(Long id) throws NoteNotFoundException {
        log.info("Deleting note form the database by id {}", id);
        Optional<Note> optionalNote = Optional.ofNullable(noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("The note was not found on the server")));
        optionalNote.ifPresent(noteRepository::delete);
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(optionalNote.get()))
                .message("Note updated successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }



}
