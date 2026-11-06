package com.example.noteapi.controller;

import com.example.noteapi.dto.Note;
import com.example.noteapi.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> findAllNotes()  {
        List<Note> notes = noteService.findAll();
//        return new ResponseEntity<>(notes,HttpStatus.OK);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody Note note, UriComponentsBuilder uriBuilder)  { // HTTPリクエストにくっついたJSONを引数とする
        Note createdNote = noteService.saveNote(note);
        URI location = uriBuilder.path("/api/notes/{id}").buildAndExpand(createdNote.getId()).toUri();

//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(location);

//        return new ResponseEntity<>(createdNote, headers, HttpStatus.CREATED); // 201を返す

        return ResponseEntity.created(location).body(createdNote); // 201を返す
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable long id) {
        Note note = noteService.findById(id);
        return ResponseEntity.ok(note); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable long id,
                                       @RequestBody Note note // ここでidとcreated_atが入ってないからserviceでsetterを使ったというわけ
    ) {
        noteService.update(note, id);
        return ResponseEntity.ok(note);
    }
}
