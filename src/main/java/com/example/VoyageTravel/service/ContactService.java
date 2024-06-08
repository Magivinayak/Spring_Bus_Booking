package com.example.VoyageTravel.service;

import com.example.VoyageTravel.entity.ContactEntity;
import com.example.VoyageTravel.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactEntity saveContact(ContactEntity contactEntity){
        return contactRepository.save(contactEntity);
    }
}
