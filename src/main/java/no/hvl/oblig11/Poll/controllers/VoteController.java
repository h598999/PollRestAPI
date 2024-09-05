package no.hvl.oblig11.Poll.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.oblig11.Poll.Domains.DomainManager;
import no.hvl.oblig11.Poll.Exceptions.Message;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Vote;

/**
 * VoteController
 */
@RequestMapping
@RestController("/api/v1")
public class VoteController {
  
  @Autowired DomainManager manager;

  @PostMapping("/votes/{id}")
  public ResponseEntity<Object> castVote(@PathVariable("id") int id, @RequestBody Vote vote){
    User caster = manager.getUsers().get(id);   
    if (caster == null){
      return new ResponseEntity<>(new Message("User not found"),HttpStatus.NOT_FOUND);
    }
    vote.setCaster(caster);
    manager.castVote(vote);
    return new ResponseEntity<>(vote, HttpStatus.OK);
  }
}
