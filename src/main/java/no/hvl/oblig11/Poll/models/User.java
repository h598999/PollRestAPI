package no.hvl.oblig11.Poll.models;

import java.util.ArrayList;
import java.util.List;

/**
 * user
 * @author Jonas Vestbø
 */

public class User {
  
  private int id;
  private String username;
  private String email;

  private List<Vote> votes;
  private List<Poll> createdPolls;

  public User(String username, String email){
    this.username = username;
    this.email = email;
    this.votes = new ArrayList<>();
    this.createdPolls = new ArrayList<>();
  }

  public boolean createPoll(Poll poll){
    poll.setCreator(this);
    if (createdPolls.contains(poll)){
      return false;
    }
    return createdPolls.add(poll);
  }

  public boolean castVote(Vote vote){
    if (votes.contains(vote)){
      return false;
    }
    return votes.add(vote);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Vote> getVotes() {
    return votes;
  }

  public void setVotes(List<Vote> votes) {
    this.votes = votes;
  }

  public List<Poll> getCreatedPolls() {
    return createdPolls;
  }

  public void setCreatedPolls(List<Poll> createdPolls) {
    this.createdPolls = createdPolls;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", votes=" + votes +
      ", createdPolls=" + createdPolls +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (id != user.id) return false;
    if (!username.equals(user.username)) return false;
    return email.equals(user.email);
  }

}
