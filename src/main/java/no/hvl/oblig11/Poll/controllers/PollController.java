package no.hvl.oblig11.Poll.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.oblig11.Poll.Domains.DomainManager;
import no.hvl.oblig11.Poll.Exceptions.Message;
import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Vote;
import no.hvl.oblig11.Poll.models.VoteOption;

/**
 * PollController
 */
@RestController
@RequestMapping("/api/v1")
public class PollController {

  @Autowired DomainManager manager;

  @GetMapping("/Polls")
  public ResponseEntity<Object> getPolls(){
    Map<Integer,Poll> polls = manager.getPolls();
    if (polls.size() == 0){
      return new ResponseEntity<>(new Message("Det finnes ingen polls"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(polls, HttpStatus.OK);
  }

  @PostMapping("/Polls/{id}")
  public ResponseEntity<Object> userIdCreatePoll(@PathVariable("id") int id, @RequestBody Poll poll){
    // User creator = manager.getUsers().get(id);
    // if (creator == null){
    //   return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    // }
    // poll.setCreator(creator);
    // List<VoteOption> options = new ArrayList<>(); 
    // poll.getVoteOptions().forEach(p -> {
    //   options.add(new VoteOption(p.getCaption(), p.getPresentationOrder()));
    // });
    //
    // Poll added = manager.addPoll(poll);
    // if (added == null){
    //   return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    // }
    // return new ResponseEntity<>(added, HttpStatus.OK);
    User creator = manager.getUsers().get(id);
    if (creator == null){
      return new ResponseEntity<>(new Message("User not found"), HttpStatus.NOT_FOUND);
    }

    return null;
  }

  @DeleteMapping("/Polls/{id}")
  public ResponseEntity<Object> deletePollById(@PathVariable("id") int id){
    Poll deleted = manager.getPolls().get(id);
    if (deleted == null){
      return new ResponseEntity<>(new Message("No such poll exists"), HttpStatus.NOT_FOUND);
    }
    // Get all the voteoptions
    List<VoteOption> options = deleted.getVoteOptions();
    System.out.println("Options: " + options.toString());
    // Find all the votes containing those options
    List<Vote> votes = new ArrayList<>();
      manager.getUsers().values().stream()
        .flatMap(u -> u.getCastedVotes().stream())
        .filter(v -> options.contains(v.getVoteOption()))
        .forEach(v -> votes.add(v));
      System.out.println("Votes: " + votes.toString());
    // Remove those votes
    votes.forEach(v -> {
      manager.getUsers().values().stream().forEach(u -> {
        if (u.getCastedVotes().contains(v)){
          System.out.println(v.toString());
          u.getCastedVotes().remove(v);
        }
      });
    });
    manager.getPolls().remove(id);
    return new ResponseEntity<>(deleted, HttpStatus.OK);
  }
}
