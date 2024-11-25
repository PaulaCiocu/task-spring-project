package com.example.spring_project_work.controller;

import com.example.spring_project_work.domain.Owner;
import com.example.spring_project_work.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private OwnerService ownerService;
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public List<Owner> getOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long ownerId) {
        return ownerService.getOwnerById(ownerId);
    }

    @PostMapping
    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
        return ownerService.save(owner);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<Void> updateOwner(@PathVariable Long ownerId,  @RequestBody Owner owner) {
        return ownerService.updateOwner(ownerId, owner);
    }
}
