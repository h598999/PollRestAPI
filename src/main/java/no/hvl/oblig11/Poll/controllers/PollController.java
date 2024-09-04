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
import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;

/**
 * PollController
 */
@RestController
@RequestMapping("/api/v1")
public class PollController {

  @Autowired DomainManager manager;

  @PostMapping("/Polls/{id}")
  public ResponseEntity<Object> userIdCreatePoll(@PathVariable("id") int id, @RequestBody Poll poll){
    User creator = manager.getUsers().get(id);
    if (creator == null){
      return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    }
    poll.setCreator(creator);
    Poll added = manager.addPoll(poll);
    if (added == null){
      return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(added, HttpStatus.OK);
  }
}


