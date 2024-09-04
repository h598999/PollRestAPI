package no.hvl.oblig11.Poll.Domains;

import java.util.HashMap;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import no.hvl.oblig11.Poll.models.Poll;
import no.hvl.oblig11.Poll.models.User;

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
    User created = new User(user.getUsername(), user.getEmail());
    users.put(created.getId(), created);
    return users.get(created.getId());
  }

  public User removeUser(int id){
    User deleted = users.remove(id);
    return deleted;
  }

  public Poll addPoll(Poll poll){
    Poll created = new Poll(poll.getQuestion(), poll.getCreator(), poll.getVoteOptions());
    polls.put(created.getId(), created);
    return polls.get(created.getId());
  }

  public Poll removePoll(int id){
    Poll deleted = polls.remove(id);
    return deleted;
  }

  public User updateUser(int userid, User newUser){
    User updated = users.get(userid);
    updated.setEmail(newUser.getEmail());
    updated.setUsername(newUser.getUsername());
    return updated;
  }

  public HashMap<Integer, Poll> getPolls() {
    return polls;
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
