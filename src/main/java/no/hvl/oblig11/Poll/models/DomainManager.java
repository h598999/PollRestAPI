package no.hvl.oblig11.Poll.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    poll.setId(this.pollsid);
    if (!creator.createPoll(poll)){
      return null;
    }
    this.pollsid++;
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

  public List<Poll> getAllPolls(){
    return new ArrayList<Poll>(polls.values());
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

  public List<User> getAllUsers(){
    return new ArrayList<User>(users.values());
  }

  public Vote createVote(int userid, Vote vote){
    User caster = users.get(userid);
    if (caster == null){
      System.out.println("Caster is null");
      return null;
    }
    Optional<VoteOption> poll = polls.values().stream().flatMap(u -> u.getVoteOptions().stream())
      .filter(vo -> vo.getId() == vote.getSelected().getId())
      .findAny();
    if (!poll.isPresent()){
      System.out.println("Could not find voteOption");
      return null;
    }
    vote.setCaster(caster);
    vote.setId(votesid);
    votesid++;
    vote.setPublishedAt(Instant.now());
    if (!caster.castVote(vote)){
      System.out.println("Couldnot cast vote");
      return null;
    }
    return vote;
  }

  public Vote removeVote(int id){
    Optional<Vote> removedvotes = users.values().stream().flatMap(v -> v.getVotes().stream())
      .filter(v -> v.getId() == id).findAny();

    if (removedvotes.isEmpty()){
      System.out.println("Could not find vote");
      return null;
    }
    Vote vote = removedvotes.get();
    // Optional<User> caster = removedvotes.forEach(removedVote -> users.values().stream()
    //     .filter(u -> u.getVotes().contains(removedVote))
    //     .findAny();
    //
    Optional<User> caster = users.values().stream().filter(u -> u.getVotes().contains(vote)).findAny();
    
    if (!caster.isPresent()){
      System.out.println("Caster is null");
      return null;
    }
    User cr = caster.get();
    if (!cr.getVotes().remove(vote)){
      System.out.println("Could not remove vote");
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

  public List<Vote> getAllVotes(){
    List<Vote> allVotes = new ArrayList<>();
    users.values().stream().flatMap(u -> u.getVotes().stream())
      .forEach(u -> allVotes.add(u));
    return allVotes;
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

  public VoteOption removeVoteOption(int pollid, int voteOptionId){
    Poll poll = polls.get(pollid);
    if (poll == null ){
      return null;
    }
    Optional<VoteOption> removed = poll.getVoteOptions().stream().filter(v -> v.getId() == voteOptionId).findAny();
    if (!removed.isPresent()){
      return null;
    }
    VoteOption deleted = poll.getVoteOptions().remove(voteOptionId);
    if (deleted == null){
      return null;
    }
    List<Vote> removedvotes = users.values().stream().flatMap(v -> v.getVotes().stream())
      .filter(v -> v.getSelected().getId() == voteOptionId).toList();

    removedvotes.forEach(removedVote -> users.values().stream()
        .filter(u -> u.getVotes().contains(removedVote))
        .forEach(u -> u.getVotes().remove(removedVote))
        );
    return deleted;
  }

  public VoteOption getVoteOptionById(int voteopionid){
    Optional<VoteOption> option = polls.values().stream().flatMap(p -> p.getVoteOptions().stream())
      .filter(vo -> vo.getId() == voteopionid)
      .findAny();

    if (option.isEmpty()){
      return null;
    }
    return option.get();
  }

  public VoteOption updateVoteOption(int voteOptionId, VoteOption newOption){
    Optional<VoteOption> option = polls.values().stream().flatMap(p -> p.getVoteOptions().stream())
      .filter(vo -> vo.getId() == voteOptionId)
      .findAny();
    if (option.isEmpty()){
      return null;
    }
    List<Vote> removedvotes = users.values().stream().flatMap(v -> v.getVotes().stream())
      .filter(v -> v.getSelected().getId() == voteOptionId).toList();

    removedvotes.forEach(removedVote -> users.values().stream()
        .filter(u -> u.getVotes().contains(removedVote))
        .forEach(u -> u.getVotes().remove(removedVote))
        );

    VoteOption updated = option.get();

    updated.setCaption(newOption.getCaption());
    updated.setValidUntil(newOption.getValidUntil());

    return updated;

  }
  
  public HashMap<Integer, User> getUsers(){
    return this.users;
  }
  
  public HashMap<Integer, Poll> getPoll(){
    return this.polls;
  }
}
