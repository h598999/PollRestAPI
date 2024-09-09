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
import no.hvl.oblig11.Poll.models.Poll;

/**
 * PollController
 */
@RestController
@RequestMapping("/api/v1")
public class PollController {
  
  @Autowired
  DomainManager manager;

  @PostMapping("/polls/{id}")
  public ResponseEntity<Poll> createPoll(@PathVariable("id") int id, @RequestBody Poll poll){
    Poll created = manager.addPoll(id, poll);
    if (created == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(created, HttpStatus.OK);
  }

  @GetMapping("/polls/{id}")
  public ResponseEntity<Poll> readPoll(@PathVariable("id") int id){
    Poll retrieved = manager.getPollById(id);
    if (retrieved == null){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(retrieved, HttpStatus.OK);
  }

  @GetMapping("/polls")
  public ResponseEntity<List<Poll>> readAllPolls(){
    List<Poll> allPolls = manager.getAllPolls();
    if (allPolls == null || allPolls.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allPolls, HttpStatus.OK);
  }

  @PutMapping("/polls/{id}")
  public ResponseEntity<Poll> updatePoll(@PathVariable("id") int id, @RequestBody Poll newPoll){
    Poll updatedPoll = manager.updatePoll(id, newPoll);
    if (updatedPoll == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updatedPoll, HttpStatus.OK);
  }

  @DeleteMapping("/polls/{id}")
  public ResponseEntity<Poll> deletPoll(@PathVariable("id") int id){
    Poll deleted = manager.deletePoll(id);
    if (deleted == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(deleted, HttpStatus.OK);
  }
  
}
