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
import no.hvl.oblig11.Poll.models.User;

/**
 * UserController
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

  @Autowired DomainManager manager;

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user){
    User created = manager.addUser(user);
    if (created == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(created, HttpStatus.OK);
  }
  
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(){
    List<User> allUsers = manager.getAllUsers();
    if (allUsers == null || allUsers.isEmpty()){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allUsers, HttpStatus.OK);
  }
  
  @GetMapping("/users/{id}")
  public ResponseEntity<User> readUser(@PathVariable("id") int id){
    User retrieved = manager.getUserById(id);
    if (retrieved == null){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(retrieved, HttpStatus.OK);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User newUser){
    User updated = manager.updateUser(id, newUser);
    if (updated == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<User> deleteUser(@PathVariable("id") int id){
    User deleted = manager.removeUser(id);
    if (deleted == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(deleted, HttpStatus.OK);
  }
}
