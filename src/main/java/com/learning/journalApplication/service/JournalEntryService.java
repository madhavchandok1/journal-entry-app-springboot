package com.learning.journalApplication.service;

import com.learning.journalApplication.entity.JournalEntry;
import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository _journalEntryRepository;

    @Autowired
    private UserService _userService;

    public Optional<JournalEntry> getEntryById(ObjectId id){
        Optional<JournalEntry> journalEntry = _journalEntryRepository.findById(id);
        return journalEntry;
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = _userService.findUserByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournalEntry = _journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            _userService.saveUser(user);
        }
        catch(Exception exception){
            log.error("Exception", exception);
            throw new RuntimeException("An error occurred while saving the entry: ",exception);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        _journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public boolean deleteEntry(ObjectId id, String userName){
        boolean isRemoved = false;
        try {
            User user = _userService.findUserByUserName(userName);
            isRemoved = user.getJournalEntries()
                    .removeIf(journalEntry -> journalEntry.getId().equals(id));
            if (isRemoved) {
                _userService.saveUser(user);
                _journalEntryRepository.deleteById(id);
            }
        }
        catch (Exception exception){
            System.out.println(exception);
            throw new RuntimeException("An error occurred while deleting the entry.", exception);
        }
        return isRemoved;
    }
}
