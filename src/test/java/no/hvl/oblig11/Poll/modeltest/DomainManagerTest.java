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
    manager.addPoll(addedUser.getId(), poll);
    Vote vote = new Vote(o1);
    Vote wrongVote = new Vote(new VoteOption("Brus", Instant.MAX));
    Vote castedVote = manager.createVote(addedUser.getId(), vote);
    Vote notcastedVote = manager.createVote(addedUser.getId(), wrongVote);
    assertTrue(addedUser.getVotes().contains(castedVote));
    assertTrue(castedVote.getId() == 0);
    assertTrue(castedVote.getSelected().equals(o1));
    assertTrue(notcastedVote == null);
    assertTrue(!addedUser.getVotes().contains(notcastedVote));
  }

  @Test
  public void removeVote(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    manager.addPoll(addedUser.getId(), poll);
    Vote vote = new Vote(o1);
    Vote castedVote = manager.createVote(addedUser.getId(), vote);
    assertTrue(addedUser.getVotes().contains(castedVote));
    Vote deletedVote = manager.removeVote(castedVote.getId());
    assertTrue(castedVote.equals(deletedVote));
    assertTrue(!addedUser.getVotes().contains(castedVote));
    assertTrue(addedUser.getVotes().isEmpty());
  }

  @Test
  public void updateVote(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    manager.addPoll(addedUser.getId(), poll);
    Vote vote = new Vote(o1);
    Vote castedVote = manager.createVote(addedUser.getId(), vote);
    assertTrue(addedUser.getVotes().contains(castedVote));
    Vote updatedVote = new Vote(o2);
    updatedVote = manager.updateVote(castedVote.getId(), updatedVote);
    assertTrue(addedUser.getVotes().contains(updatedVote));
    assertTrue(addedUser.getVotes().size() == 1);
  }

  @Test
  public void getVoteByIdTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    manager.addPoll(addedUser.getId(), poll);
    Vote vote = new Vote(o1);
    Vote vote2 = new Vote(o2);
    Vote castedVote = manager.createVote(addedUser.getId(), vote);
    Vote castedVote2 = manager.createVote(addedUser.getId(), vote2);
    Vote retrievedVote = manager.getVoteById(0);
    assertTrue(castedVote.equals(retrievedVote));
    assertTrue(!castedVote2.equals(retrievedVote));
    assertTrue(addedUser.getVotes().size() == 2);
  }
  
  @Test
  public void addVoteOptionTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    manager.addPoll(addedUser.getId(), poll);
    
    VoteOption add = new VoteOption("Brus", Instant.MAX);
    VoteOption added = manager.createVoteOption(0, add);

    assertTrue(added.getId() == 2);
    assertTrue(added.getCaption().equals(add.getCaption()));
    assertTrue(added.getValidUntil().equals(add.getValidUntil()));
    assertTrue(manager.getPollById(0).getVoteOptions().contains(added));
    assertTrue(manager.getPollById(0).getVoteOptions().size() == 3);
  }

  @Test
  public void removeVoteOptionTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Vote vote = new Vote(o1);
    manager.addPoll(addedUser.getId(), poll);
    manager.createVote(addedUser.getId(), vote);
    
    VoteOption add = new VoteOption("Brus", Instant.MAX);
    manager.createVoteOption(0, add);

    VoteOption removed = manager.removeVoteOption(0, 0);
    // assertTrue(addedUser.getVotes().size() == 1 && addedUser.getVotes().contains(castedVote));
    assertTrue(manager.getPollById(0).getVoteOptions().size() == 2);
    assertTrue(!manager.getPollById(0).getVoteOptions().contains(o1));
    assertTrue(removed.equals(o1));
    assertTrue(addedUser.getVotes().isEmpty());
  }

  @Test
  public void getVoteOptionByIdTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    VoteOption o3 = new VoteOption("Brus", Instant.MAX);
    VoteOption o4 = new VoteOption("Juice", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll poll2 = new Poll("Hva smaker best?", List.of(o3,o4));
    Vote vote = new Vote(o1);
    manager.addPoll(addedUser.getId(), poll);
    manager.addPoll(addedUser.getId(), poll2);
    manager.createVote(addedUser.getId(), vote);

    VoteOption retrieved = manager.getVoteOptionById(0);
    assertTrue(retrieved.equals(o1));
    assertTrue(manager.getPoll().values().size() == 2);
  }

  @Test
  public void updateVoteOptionTest(){
    User user = new User("Jonas", "Jonas@email.com");
    User addedUser = manager.addUser(user);
    VoteOption o1 = new VoteOption("Vann", Instant.MAX);
    VoteOption o2 = new VoteOption("Melk", Instant.MAX);
    VoteOption o3 = new VoteOption("Brus", Instant.MAX);
    VoteOption o4 = new VoteOption("Juice", Instant.MAX);
    Poll poll = new Poll("Hva smaker best?", List.of(o1,o2));
    Poll poll2 = new Poll("Hva smaker best?", List.of(o3,o4));
    Vote vote = new Vote(o1);
    manager.addPoll(addedUser.getId(), poll);
    manager.addPoll(addedUser.getId(), poll2);
    manager.createVote(addedUser.getId(), vote);

    VoteOption newOption = new VoteOption("Cola", Instant.now());
    VoteOption updated = manager.updateVoteOption(0, newOption);
    assertTrue(updated.getId() == 0);
    assertTrue(updated.getCaption().equals(newOption.getCaption()));
    assertTrue(updated.getValidUntil().equals(newOption.getValidUntil()));

    assertTrue(manager.getPollById(0).getVoteOptions().size() == 2);
    assertTrue(manager.getPollById(0).getVoteOptions().contains(updated));

    assertTrue(addedUser.getVotes().isEmpty());

  }


}
