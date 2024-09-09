package no.hvl.oblig11.Poll.models;

import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * DomainManager
 */
@Component
public class DomainManager {

  private HashMap<Integer, Poll> polls = new HashMap<>();
  private HashMap<Integer, User> users = new HashMap<>();
  private int pollsid = 0;
  private int usersid = 0;
  private int votesid = 0;
  private int voteoptionsid = 0;

  public DomainManager(){}

  public Poll addPoll(int userid, Poll poll){
    Optional<User> cr = users.values().stream().filter(u -> u.getId()==(userid)).findAny();
    if (!cr.isPresent()){
      return null;
    }
    User creator = cr.get();
    if (!(poll.getVoteOptions().size() >= 2)){
      return null;
    }
    if (!creator.createPoll(poll)){
      return null;
    }
    poll.setId(pollsid);
    pollsid++;
    poll.getVoteOptions().forEach(vo ->  {
      vo.setId(voteoptionsid);
      voteoptionsid++;
    });
    if (polls.containsValue(poll)){
      return null;
    }
    polls.put(poll.getId(), poll);
    return poll;
  }

  public Poll deletePoll(int id){
    Poll removed = polls.remove(id);
    if (removed == null){
      return removed;
    }
    removed.getCreator().getCreatedPolls().remove(removed);
    return removed;
  }

  public Poll getPollById(int id){
    return polls.get(id);
  }
  
  public Poll updatePoll(int id, Poll newPoll){
    Poll updated = polls.get(id);
    if (updated == null){
      return null;
    }
    if (newPoll.getVoteOptions() == null || !(newPoll.getVoteOptions().size() >= 2)){
      return null;
    }
    updated.setQuestion(newPoll.getQuestion());
    newPoll.getVoteOptions().stream().forEach(vo -> {
      vo.setId(voteoptionsid);
      voteoptionsid++;
    });
    updated.setVoteOptions(newPoll.getVoteOptions());
    updated.setPublishedAt(Instant.now());
    return updated;
  }

  public User addUser(User user){
    System.out.println("First line in method");
    Optional<User> found = users.values().stream().filter(u -> u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail())).findAny();
    if (found.isPresent()){
      return null;
    }
    if (!user.getVotes().isEmpty() || !user.getCreatedPolls().isEmpty()){
      return null;
    }
    user.setId(usersid);
    usersid++;
    users.put(user.getId(), user);
    System.out.println("Done");
    return user;
  }

  public User removeUser(int id){
    User removed = this.users.remove(id);
    if (removed == null){
      return null;
    }
    removed.getCreatedPolls().forEach(p -> {
      polls.remove(p.getId());
    });
    return removed;
  }

  public User updateUser(int id, User newUser){
    User updated = users.get(id);
    if (updated == null){
      return null;
    }
    if (newUser.getCreatedPolls() == null || newUser.getVotes() == null){
      return null;
    }
    updated.setUsername(newUser.getUsername());
    updated.setEmail(newUser.getEmail());
    return updated;
  }

  public User getUserById(int id){
    return this.users.get(id);
  }

  public Vote createVote(int userid, Vote vote){
    User caster = users.get(userid);
    if (caster == null){
      return null;
    }
    Optional<Poll> poll = polls.values().stream().filter(p -> p.getVoteOptions().contains(vote.getSelected())).findAny();
    if (!poll.isPresent()){
      return null;
    }
    vote.setCaster(caster);
    if (!caster.castVote(vote)){
      return null;
    }
    return vote;
  }

  public Vote removeVote(Vote vote){
    Optional<User> caster = users.values().stream().filter(u -> u.getVotes().contains(vote)).findAny();
    if (!caster.isPresent()){
      return null;
    }
    User cr = caster.get();
    if (!cr.getVotes().remove(vote)){
      return null;
    }
    return vote;
  }

  public Vote getVoteById(int id){
    Optional<Vote> found = users.values().stream()
      .flatMap(u -> u.getVotes().stream())
      .filter(v -> v.getId() == id)
      .findFirst();

    if (!found.isPresent()){
      return null;
    }
    return found.get();
  }

  public Vote updateVote(int id, Vote updatedvote){
    Optional<Vote> found = users.values().stream()
      .flatMap(u -> u.getVotes().stream())
      .filter(v -> v.getId() == id)
      .findFirst();
    if (!found.isPresent()){
      return null;
    }
    Vote vote = found.get();
    vote.setSelected(updatedvote.getSelected());
    vote.setPublishedAt(Instant.now());
    return vote;
  }

  public VoteOption createVoteOption(int id, VoteOption option){
    Poll poll = polls.get(id);
    if (poll == null){
      return null;
    }
    if (!poll.addVoteOption(option)){
      return null;
    }
    option.setId(voteoptionsid);
    voteoptionsid++;
    return option;
  }

  public VoteOption removeVoteOption(){
    return null;
  }

  public VoteOption getVoteOptionById(){
    return null;
  }

  public VoteOption updateVoteOption(){
    return null;
  }
  
  public HashMap<Integer, User> getUsers(){
    return this.users;
  }
  
  public HashMap<Integer, Poll> getPoll(){
    return this.polls;
  }
}
