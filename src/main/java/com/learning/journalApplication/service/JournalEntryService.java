package com.learning.journalApplication.service;

import com.learning.journalApplication.entity.JournalEntry;
import com.learning.journalApplication.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository _journalEntryRepository;

    public List<JournalEntry> getAllEntries(){
        List<JournalEntry> journalEntries = _journalEntryRepository.findAll();
        return journalEntries;
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        Optional<JournalEntry> journalEntry = _journalEntryRepository.findById(id);
        return journalEntry;
    }

    public void saveEntry(JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            _journalEntryRepository.save(journalEntry);
        }
        catch(Exception exception){
            log.error("Exception", exception);
        }

    }

    public JournalEntry deleteEntry(ObjectId id){
        Optional<JournalEntry> journalEntry = _journalEntryRepository.findById(id);
        _journalEntryRepository.deleteById(id);
        return journalEntry.orElse(null);
    }
}
