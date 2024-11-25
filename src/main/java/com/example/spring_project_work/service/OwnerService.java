package com.example.spring_project_work.service;

import com.example.spring_project_work.domain.Owner;
import com.example.spring_project_work.repository.OwnerJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    private OwnerJpaRepository ownerJpaRepository;
    public OwnerService(OwnerJpaRepository ownerJpaRepository) {
        this.ownerJpaRepository = ownerJpaRepository;
    }

    public List<Owner> getAllOwners() {
        return ownerJpaRepository.findAll();
    }

    public ResponseEntity<Owner> save(Owner owner) {
        if ((owner.getFirstName() != null && !owner.getFirstName().isEmpty()) ||
                (owner.getLastName() != null && !owner.getLastName().isEmpty())) {
            Owner savedOwner = ownerJpaRepository.save(owner);
            return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Owner> getOwnerById(Long ownerId) {
        Optional<Owner> owner = ownerJpaRepository.findById(ownerId);
        if (owner.isPresent()) {
            return new ResponseEntity<>(owner.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> updateOwner(Long ownerId,  Owner owner) {
        Optional optional = ownerJpaRepository.findById(ownerId);
        if (optional.isPresent()) {
            if (owner.getFirstName() != null && !owner.getFirstName().isEmpty()) {
                ownerJpaRepository.updateOwner(ownerId, owner.getFirstName(), owner.getLastName());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
