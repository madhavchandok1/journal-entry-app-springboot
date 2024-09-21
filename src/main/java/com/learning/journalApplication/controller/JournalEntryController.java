package com.learning.journalApplication.controller;

import com.learning.journalApplication.entity.JournalEntry;
import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.service.JournalEntryService;
import com.learning.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService _journalEntryService;

    @Autowired
    private UserService _userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(
            @PathVariable String userName
    ){
        User user = _userService.findUserByUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if(!journalEntries.isEmpty() && journalEntries != null){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalByID(
            @PathVariable ObjectId journalId
    ){
        Optional<JournalEntry> journalEntry = _journalEntryService.getEntryById(journalId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createEntry/{userName}")
    public ResponseEntity<JournalEntry> createEntry(
            @RequestBody JournalEntry journalEntry,
            @PathVariable String userName
    ){
        try{
            _journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}/{journalId}")
    public ResponseEntity<?> updateJournalEntryById(
            @RequestBody JournalEntry journalEntry,
            @PathVariable String journalId,
            @PathVariable String userName
    ){
        JournalEntry oldJournalEntry = _journalEntryService.getEntryById(new ObjectId(journalId)).orElse(null);
        if(oldJournalEntry!=null){
            oldJournalEntry.setTitle(
                    journalEntry.getTitle() !=null &&
                    !journalEntry.getTitle().isEmpty() ?
                    journalEntry.getTitle() : oldJournalEntry.getTitle()
            );
            oldJournalEntry.setContent(
                    journalEntry.getContent() != null &&
                    !journalEntry.getContent().isEmpty() ?
                    journalEntry.getContent() : oldJournalEntry.getContent()
            );
            _journalEntryService.saveEntry(oldJournalEntry);
            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userName}/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(
            @PathVariable ObjectId journalId,
            @PathVariable String userName
    ){
        _journalEntryService.deleteEntry(journalId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
