package com.vladimirpandruov.noteApp1.controller;

import com.vladimirpandruov.noteApp1.domain.HttpResponse;
import com.vladimirpandruov.noteApp1.domain.Note;
import com.vladimirpandruov.noteApp1.enumeration.Level;
import com.vladimirpandruov.noteApp1.exception.NoteNotFoundException;
import com.vladimirpandruov.noteApp1.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path="/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<HttpResponse<Note>> getNotes() {
        return ResponseEntity.ok().body(noteService.getNotes());
    }
    @PostMapping
    public ResponseEntity<HttpResponse<Note>> saveNote(@RequestBody @Valid Note note){
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/notes").toUriString())
        ).body(noteService.saveNote(note));
    }
    @GetMapping("/filter?level=HIGH")
    public ResponseEntity<HttpResponse<Note>> filterNotes(@RequestParam(value="level") Level level){
        return ResponseEntity.ok().body(noteService.filterNotes(level));
    }
    @PutMapping
    public ResponseEntity<HttpResponse<Note>> updateNote(@RequestBody @Valid Note note)throws NoteNotFoundException {
        return ResponseEntity.ok().body(noteService.updateNote(note));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse<Note>> deleteNote(@PathVariable(value="noteId") Long id) throws NoteNotFoundException{
        return ResponseEntity.ok().body(noteService.deleteNote(id));
    }

}
