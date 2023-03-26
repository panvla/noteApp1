package com.vladimirpandruov.noteApp1.service;

import com.vladimirpandruov.noteApp1.domain.HttpResponse;
import com.vladimirpandruov.noteApp1.domain.Note;
import com.vladimirpandruov.noteApp1.enumeration.Level;
import com.vladimirpandruov.noteApp1.repository.NoteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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





}
