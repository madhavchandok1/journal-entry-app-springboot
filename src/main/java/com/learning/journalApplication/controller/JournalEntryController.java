package com.learning.journalApplication.controller;

import com.learning.journalApplication.entity.JournalEntry;
import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.service.JournalEntryService;
import com.learning.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService _journalEntryService;

    @Autowired
    private UserService _userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = _userService.findUserByUserName(userName);
        List<JournalEntry> availableEntries = user.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(journalId)).collect(Collectors.toList());
        if(!availableEntries.isEmpty()){
            Optional<JournalEntry> journalEntry = _journalEntryService.getEntryById(journalId);
            if(journalEntry.isPresent())
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createEntry")
    public ResponseEntity<JournalEntry> createEntry(
            @RequestBody JournalEntry journalEntry
    ){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            _journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{journalId}")
    public ResponseEntity<?> updateJournalEntryById(
            @RequestBody JournalEntry newJournalEntry,
            @PathVariable ObjectId journalId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = _userService.findUserByUserName(userName);
        List<JournalEntry> availableEntries = user.getJournalEntries().stream()
                .filter(entry -> entry.getId().equals(journalId)).collect(Collectors.toList());

        if(!availableEntries.isEmpty()){
            Optional<JournalEntry> journalEntry = _journalEntryService.getEntryById(journalId);
            if (journalEntry.isPresent()){
                JournalEntry oldJournalEntry = journalEntry.get();
                oldJournalEntry.setTitle(
                        newJournalEntry.getTitle() !=null &&
                                !newJournalEntry.getTitle().isEmpty() ?
                                newJournalEntry.getTitle() : oldJournalEntry.getTitle()
                );
                oldJournalEntry.setContent(
                        newJournalEntry.getContent() != null &&
                                !newJournalEntry.getContent().isEmpty() ?
                                newJournalEntry.getContent() : oldJournalEntry.getContent()
                );
                _journalEntryService.saveEntry(oldJournalEntry);
                return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(
            @PathVariable ObjectId journalId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean isRemoved = _journalEntryService.deleteEntry(journalId, userName);
        if (isRemoved)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
