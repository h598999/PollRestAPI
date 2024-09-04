package no.hvl.oblig11.Poll.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import no.hvl.oblig11.Poll.Domains.DomainManager;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.VoteOption;
import no.hvl.oblig11.Poll.Exceptions.Message;

/**
 * UserController
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
  
  @Autowired DomainManager manager;

  @GetMapping("/init")
  public ResponseEntity<Object> init(){
    User user = new User("Jonas", "jonas@email.com");
    VoteOption o1 = new VoteOption("Vann", 1);
    VoteOption o2 = new VoteOption("Melk", 2);
    Poll poll = new Poll("Hva smaker best?", user, List.of(o1,o2));;
    user = manager.addUser(user);
    poll = manager.addPoll(poll);
    if (user == null || poll == null){
      return new ResponseEntity<>(new Message("Something is null + " + user + " " + poll), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(new Message("inited"), HttpStatus.OK);
  }

  @GetMapping("/users")
  public ResponseEntity<Object> getAllUsers(){
    Map<Integer, User> users = manager.getUsers();
    if ( users == null || users.size() == 0){
      return new ResponseEntity<>(new Message("No users"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<Object> addUser(@RequestBody User user){
    User created = manager.addUser(user);
    if (created == null){
      return new ResponseEntity<>(new Message("Could not add user"), HttpStatus.BAD_REQUEST);
    }
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(created.getId())
      .toUri();

    return ResponseEntity.created(location).body(user);
  }
}
