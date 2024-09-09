package no.hvl.oblig11.Poll.modeltest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.oblig11.Poll.models.DomainManager;
import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Vote;
import no.hvl.oblig11.Poll.models.VoteOption;

/**
 * DomainManagerTest
 */
public class DomainManagerTest {

  DomainManager manager;

  @BeforeEach
  public void setUp(){
    this.manager = new DomainManager();
  }


  @Test
  public void addPoll(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll addedPoll = manager.addPoll(addedUser.getId(), poll);
    assertTrue(addedPoll.getId() == 0);
    assertTrue(addedPoll.getQuestion().equals(poll.getQuestion()));
    assertTrue(addedPoll.getVoteOptions().equals(poll.getVoteOptions()));
    assertTrue(addedPoll.getCreator().equals(addedUser));
    assertTrue(addedPoll.getVoteOptions().get(0).getId() == 0);
    assertTrue(addedPoll.getVoteOptions().get(1).getId() == 1);
    assertTrue(manager.getPoll().get(0).equals(addedPoll));
  }

  @Test
  public void deletePoll(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll addedPoll = manager.addPoll(addedUser.getId(), poll);
    assertTrue(manager.getUsers().get(0).getCreatedPolls().contains(addedPoll));
    Poll deletedPoll = manager.deletePoll(0);
    assertTrue(addedPoll.equals(deletedPoll));
    assertTrue(addedUser.getCreatedPolls().isEmpty());
    assertTrue(manager.getPoll().isEmpty());
  }

  @Test
  public void getPollById(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll addedPoll = manager.addPoll(addedUser.getId(), poll);
    Poll getPollById = manager.getPollById(0);
    assertTrue(addedPoll.equals(getPollById));
    assertTrue(!manager.getPoll().isEmpty());
    assertTrue(manager.getPoll().size() == 1);
  }

  @Test
  public void updatePoll(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    VoteOption o3 = new VoteOption("Jonas", Instant.MAX);
    VoteOption o4 = new VoteOption("Even", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll addedPoll = manager.addPoll(addedUser.getId(), poll);
    Poll updatedPoll = new Poll("Hvem er kulest?", List.of(o3, o4));
    Poll newPoll = manager.updatePoll(addedPoll.getId(), updatedPoll);
    assertTrue(newPoll.getId() == 0);
    assertTrue(newPoll.getQuestion().equals(updatedPoll.getQuestion()));
    assertTrue(newPoll.getVoteOptions().equals(updatedPoll.getVoteOptions()));
    assertTrue(newPoll.getCreator().equals(addedUser));
    assertTrue(newPoll.getVoteOptions().get(0).getId() == 2);
    assertTrue(addedPoll.getVoteOptions().get(1).getId() == 3);
    assertTrue(addedUser.getCreatedPolls().contains(newPoll));
    assertTrue(manager.getPoll().get(0).equals(newPoll));
  }

  @Test
  public void createUserTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User createdUser = manager.addUser(user);
    assertTrue(createdUser.getId() == 0);
    assertTrue(createdUser.getUsername().equals(user.getUsername()));
    assertTrue(createdUser.getEmail().equals(user.getEmail()));
    assertTrue(createdUser.getCreatedPolls().isEmpty());
    assertTrue(createdUser.getVotes().isEmpty());
    assertTrue(manager.getUsers().get(0).equals(createdUser));
  }

  @Test
  public void deleteUserTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User createdUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    manager.addPoll(createdUser.getId(), poll);
    User removedUser = manager.removeUser(createdUser.getId());
    assertTrue(removedUser.equals(createdUser));
    assertTrue(manager.getUsers().isEmpty());
    assertTrue(manager.getPoll().isEmpty());
  }

  @Test
  public void getUserById(){
    User user = new User("Jonas", "Jonas@email.com");
    User createdUser = manager.addUser(user);
    User retrieved = manager.getUserById(0);
    assertTrue(retrieved.equals(createdUser));
    assertTrue(manager.getUsers().size() == 1);
  }

  @Test
  public void updateUser(){
    User user = new User("Jonas", "Jonas@email.com");
    User createdUser = manager.addUser(user);
    assertTrue(createdUser != null);
    User updatedUser = new User("Jonas2", "Jonas2@email.com");
    User newUser = manager.updateUser(createdUser.getId(), updatedUser);
    assertTrue(newUser.getId() == createdUser.getId());
    assertTrue(newUser.getUsername().equals(updatedUser.getUsername()));
    assertTrue(newUser.getEmail().equals(updatedUser.getEmail()));
    assertTrue(newUser.getCreatedPolls().equals(updatedUser.getCreatedPolls()));
    assertTrue(newUser.getVotes().equals(updatedUser.getVotes()));
  }

  @Test
  public void castVote(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll addedPoll = manager.addPoll(addedUser.getId(), poll);
    Vote vote = new Vote(o1);
    Vote wrongVote = new Vote(new VoteOption("Brus", Instant.MAX));
    Vote castedVote = manager.createVote(addedUser.getId(), vote);
    Vote notcastedVote = manager.createVote(addedUser.getId(), wrongVote);
    assertTrue(addedUser.getVotes().contains(castedVote));
    assertTrue(castedVote.getSelected().equals(o1));
    assertTrue(notcastedVote == null);
    assertTrue(!addedUser.getVotes().contains(notcastedVote));
  }

}
