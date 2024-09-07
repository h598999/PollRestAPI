package no.hvl.oblig11.Poll.Domains;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;
import no.hvl.oblig11.Poll.models.Vote;
import no.hvl.oblig11.Poll.models.VoteOption;

/**
 * DomainManager
 */
@Component
public class DomainManager {

  private HashMap<Integer, Poll> polls;
  private HashMap<Integer, User> users;

  public DomainManager(){
    this.polls = new HashMap<>();
    this.users = new HashMap<>();
  }

  public User addUser(User user){
    if (users.containsValue(user)){
      return null;
    }
    users.put(user.getId(), user);
    return users.get(user.getId());
  }

  public User removeUser(int id){
    User deleted = users.remove(id);
    return deleted;
  }

  public Poll addPoll(Poll poll){
    User creator = users.get(poll.getCreator().getId());
    if (creator == null){
      return null;
    }
    creator.getCreatedPolls().add(poll);
    polls.put(poll.getId(), poll);
    return polls.get(poll.getId());
  }

  public Poll removePoll(int id){
    Poll deleted = polls.remove(id);
    if (deleted != null){
      deleted.getCreator().getCreatedPolls().remove(deleted);
    }
    return deleted;
  }

  public User updateUser(int userid, User newUser){
    User updated = users.get(userid);
    if (updated == null){
      return null;
    }
    updated.setEmail(newUser.getEmail());
    updated.setUsername(newUser.getUsername());
    return updated;
  }

  public User updateCastedVotesForUser(int userid, List<Vote> votes){
    User updated = users.get(userid);
    if (updated == null){
      return null;
    }
    updated.setCastedVotes(votes);
    return updated;
  }

  public Vote castVote(Vote vote){
    Vote casted = new Vote(vote.getCaster(), vote.getVoteOption());
    if (casted.getCaster() == null || casted.getVoteOption() == null){
      return null;
    }
    VoteOption option = isFound(vote.getVoteOption());
    if (option == null){
      return null;
    }
    option.castVote();
    casted.setVoteOption(option);
    System.out.println(option);
    casted.getCaster().getCastedVotes().add(casted);
    return casted;
  }

  public Vote removeVote(Vote vote){
    if (!getUsers().get(vote.getCaster().getId()).getCastedVotes().remove(vote)){
      return null;
    }
    vote.getVoteOption().removeVote();
    return vote;
  }

  public HashMap<Integer, Poll> getPolls() {
    return polls;
  }

  public VoteOption isFound(VoteOption voteOption){
    return getPolls().values().stream()
      .flatMap(p -> p.getVoteOptions().stream())
      .filter(v -> v.getId() == voteOption.getId())
      .findAny()
      .orElseThrow(() -> new NoSuchElementException("Vote option not found"));
  }

  public void setPolls(HashMap<Integer, Poll> polls) {
    this.polls = polls;
  }

  public HashMap<Integer, User> getUsers() {
    return users;
  }

  public void setUsers(HashMap<Integer, User> users) {
    this.users = users;
  }
}
