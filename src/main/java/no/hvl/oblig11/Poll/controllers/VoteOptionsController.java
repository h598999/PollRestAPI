package no.hvl.oblig11.Poll.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.oblig11.Poll.models.DomainManager;
import no.hvl.oblig11.Poll.models.VoteOption;

/**
 * VoteOptionsController
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class VoteOptionsController {

  @Autowired
  DomainManager manager;
  
  @PostMapping("/voteoptions/{id}")
  public ResponseEntity<VoteOption> createVoteOption(@PathVariable("id") int id, @RequestBody VoteOption option){
    VoteOption created = manager.createVoteOption(id, option);
    if (created == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }
  
  @GetMapping("/voteoptions/{id}")
  public ResponseEntity<VoteOption> readVoteOption(@PathVariable("id") int id){
    VoteOption retrieved = manager.getVoteOptionById(id);
    if (retrieved == null){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(retrieved, HttpStatus.OK);
  }

  @GetMapping("/voteoptions")
  public ResponseEntity<List<VoteOption>> readAllVoteOptions(){
    List<VoteOption> allVoteOptions = manager.getAllVoteOptions();
    if (allVoteOptions == null || allVoteOptions.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allVoteOptions, HttpStatus.OK);
  }

  @PutMapping("/voteoptions/{id}")
  public ResponseEntity<VoteOption> updateVoteOption(@PathVariable("id") int id, @RequestBody VoteOption updatedOption){
    VoteOption updated = manager.updateVoteOption(id, updatedOption);
    if (updated == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/voteoptions/{id}")
  public ResponseEntity<VoteOption> deleteVoteOption(@PathVariable("id") int id){
    VoteOption deleted = manager.removeVoteOption(id);
    if (deleted == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(deleted, HttpStatus.OK);
  }
}
