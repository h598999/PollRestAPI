package no.hvl.oblig11.Poll.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.oblig11.Poll.models.DomainManager;
import no.hvl.oblig11.Poll.models.Vote;

/**
 * VoteController
 */
@RestController
@RequestMapping("/api/v1")
public class VoteController {

  @Autowired
  DomainManager manager;

  @PostMapping("/votes/{id}")
  public ResponseEntity<Vote> createVote(@PathVariable("id") int userid, @RequestBody Vote vote){
    Vote created = manager.createVote(userid, vote);
    if (created == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(created, HttpStatus.OK);
  }

  @GetMapping("/votes/{id}")
  public ResponseEntity<Vote> readVote(@PathVariable("id") int id){
    Vote retrieved = manager.getVoteById(id);
    if (retrieved== null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(retrieved, HttpStatus.OK);

  }

  @GetMapping("/votes")
  public ResponseEntity<List<Vote>> readAllVotes(){
    List<Vote> allVotes = manager.getAllVotes();
    if (allVotes == null || allVotes.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allVotes, HttpStatus.OK);
  }

  @PutMapping("/votes/{id}")
  public ResponseEntity<Vote> updateVote(@PathVariable("id") int id, @RequestBody Vote newVote){
    Vote updated = manager.updateVote(id, newVote);
    if (updated == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/votes/{id}")
  public ResponseEntity<Vote> deleteVote(@PathVariable("id") int id){
    Vote deleted = manager.removeVote(id);
    if (deleted == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(deleted, HttpStatus.OK);
  }



}
