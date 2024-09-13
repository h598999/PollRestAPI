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

  public boolean reset(){
    users = new HashMap<Integer, User>();
    polls= new HashMap<Integer, Poll>();
    pollsid = 0;
    usersid = 0;
    votesid = 0;
    voteoptionsid = 0;
    return true;
  }

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

    List<Integer> voteOptions = new ArrayList<>();

    removed.getVoteOptions().forEach(vo -> {
      voteOptions.add(vo.getId());
    });

    List<Vote> removedvotes = users.values().stream().flatMap(v -> v.getVotes().stream())
      .filter(v -> voteOptions.contains(v.getId())).toList();

    removedvotes.forEach(removedVote -> users.values().stream()
        .filter(u -> u.getVotes().contains(removedVote))
        .forEach(u -> u.getVotes().remove(removedVote))
        );
    removed.getCreator().getCreatedPolls().remove(removed);
    return removed;
  }

  public Poll getPollById(int id){
    Poll poll = polls.get(id);
    if (poll == null){
      return null;
    }
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


  /**
   *Creates a vote on a poll. A user can only have one single vote per poll. 
   * If a vote by the user already exists on the poll the user is voting on, the existing vote will be removed.
   * @param userid the id of the user casting the vote
   * @param vote the vote to be casted
   * @return the vote that was casted or null if the vote could not be casted
   */
  public Vote createVote(int userid, Vote vote){
    // Check if there exists a user with the given id
    User caster = users.get(userid);
    if (caster == null){
      System.out.println("Caster is null");
      return null;
    }
    // Check if the votingOption given by the vote exists in a poll
    Optional<VoteOption> votedOption = polls.values().stream().flatMap(u -> u.getVoteOptions().stream())
      .filter(vo -> vo.getId() == vote.getSelected().getId() && vo.getCaption().equals(vote.getSelected().getCaption()))
      .findAny();
    if (!votedOption.isPresent()){
      // If the votingOption does not exist in any poll return null and do nothing
      System.out.println("Could not find voteOption");
      return null;
    }
    // To make sure that a user can only have one vote per Poll
    //
    // Find the Poll currently getting voted on
    Optional<Poll> votingPoll = polls.values().stream().filter(p -> p.getVoteOptions().contains(votedOption.get())).findAny();
    // Check if there exists a vote from the given user on the current poll being voted on
    Optional<Vote> castedVote = caster.getVotes().stream()
      .filter(v -> votingPoll.get().getVoteOptions().contains(v.getSelected()))
      .findAny();
    if (castedVote.isPresent()){
      // If the user has already casted a vote on this poll then remove the vote
      caster.getVotes().remove(castedVote.get());
      decreaseNumberOfVotes(castedVote.get());
    }
    //Set the caster of the vote ** Do not think this actually matters as it is not sent through json bcs of serialization
    vote.setCaster(caster);
    //Set the id of the vote with the kept votesid variable
    vote.setId(votesid);
    //increment the votesid
    votesid++;
    //Set the publishedtime of the vote to now
    vote.setPublishedAt(Instant.now());
    //If the vote cannot be casted return null ** should never fail at this point
    if (!caster.castVote(vote)){
      System.out.println("Couldnot cast vote");
      return null;
    }
    increaseNumberOfVotes(vote);
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
    vote.getSelected().decreaseNumberOfVotes();
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
    decreaseNumberOfVotes(vote);
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
    decreaseNumberOfVotes(vote);
    vote.setSelected(updatedvote.getSelected());
    vote.setPublishedAt(Instant.now());
    increaseNumberOfVotes(vote);
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

  public VoteOption removeVoteOption(int voteOptionId){
    Optional<VoteOption> removed = polls.values().stream()
      .flatMap(p -> p.getVoteOptions().stream())
      .filter(vo -> vo.getId() == voteOptionId)
      .findAny();
    if (!removed.isPresent()){
      return null;
    }
    Optional<Poll> oppoll = polls.values().stream().filter(p -> p.getVoteOptions().contains(removed.get())).findAny();
    if (oppoll.isEmpty()){
      return null;
    }
    Poll poll = oppoll.get();
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

  public List<VoteOption> getAllVoteOptions(){
    return polls.values().stream()
      .flatMap(p -> p.getVoteOptions().stream())
      .toList();
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

  private boolean increaseNumberOfVotes(Vote vote){
    Optional<VoteOption> vo = polls.values().stream().flatMap(p -> p.getVoteOptions().stream())
      .filter(v -> vote.getSelected().equals(v)).findAny();
    if (!vo.isPresent()){
      return false;
    }
    vo.get().incrementNumberOfVotes();
    return true;
  }

  private boolean decreaseNumberOfVotes(Vote vote){
    Optional<VoteOption> vo = polls.values().stream().flatMap(p -> p.getVoteOptions().stream())
      .filter(v -> vote.getSelected().equals(v)).findAny();
    if (!vo.isPresent()){
      return false;
    }
    vo.get().decreaseNumberOfVotes();
    return true;
  }
}
