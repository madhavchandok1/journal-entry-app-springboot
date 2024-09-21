package com.learning.journalApplication.controller;

import com.learning.journalApplication.entity.JournalEntry;
import com.learning.journalApplication.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService _journalEntryService;

    @Autowired

    @GetMapping("userName")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName){
        List<JournalEntry> journalEntries = _journalEntryService.getAllEntries();
        if(!journalEntries.isEmpty() && journalEntries != null){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalByID(@PathVariable ObjectId journalId){
        Optional<JournalEntry> journalEntry = _journalEntryService.getEntryById(journalId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try{

            _journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String journalId, @RequestBody JournalEntry journalEntry){
        JournalEntry oldJournalEntry = _journalEntryService.getEntryById(new ObjectId(journalId)).orElse(null);
        if(oldJournalEntry!=null){
            oldJournalEntry.setTitle(journalEntry.getTitle() !=null && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().equals("") ? journalEntry.getContent() : oldJournalEntry.getContent());
            _journalEntryService.saveEntry(oldJournalEntry);
            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId journalId){
        JournalEntry journalEntry = _journalEntryService.deleteEntry(journalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
