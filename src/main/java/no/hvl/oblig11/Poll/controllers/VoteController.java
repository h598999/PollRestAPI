package no.hvl.oblig11.Poll.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import jakarta.websocket.server.PathParam;
import no.hvl.oblig11.Poll.Domains.DomainManager;
import no.hvl.oblig11.Poll.Exceptions.Message;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Vote;

/**
 * VoteController
 */
@RequestMapping("/api/v1")
@RestController
public class VoteController {
  
  @Autowired DomainManager manager;

  @GetMapping("/votes")
  public ResponseEntity<Object> getAllVotes(){
    List<Vote> allVotes = new ArrayList<>();
    
    manager.getUsers().values().stream()
      .flatMap(v -> v.getCastedVotes().stream())
      .forEach(v -> allVotes.add(v));
    
    if (allVotes.isEmpty()){
      return new ResponseEntity<>(new Message("No votes found"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(allVotes, HttpStatus.OK);
  }



  @PostMapping("/votes/{id}")
  public ResponseEntity<Object> castVote(@PathVariable("id") int id, @RequestBody Vote vote){
      User caster = manager.getUsers().get(id);   
      if (caster == null){
        return new ResponseEntity<>(new Message("User not found"),HttpStatus.NOT_FOUND);
      }
      vote.setCaster(caster);
      // manager.castVote(vote);
      vote = manager.castVote(vote);
      return new ResponseEntity<>(vote, HttpStatus.OK);
  }

  @PutMapping("/votes/{id}")
  public ResponseEntity<Object> updateVote(@PathVariable("id") int id, @RequestBody Vote vote){
    Vote old = manager.getUsers().values().stream()
      .flatMap(v -> v.getCastedVotes().stream())
      .filter(v -> v.getId() == v.getId())
      .findFirst()
      .orElseThrow(() -> new NoSuchElementException("Not found"));
    old.getVoteOption().removeVote();
    vote.getVoteOption().castVote();
    old.setVoteOption(vote.getVoteOption());
    return new ResponseEntity<>(old, HttpStatus.OK);
  }
}
