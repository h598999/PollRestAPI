package no.hvl.oblig11.Poll.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User
 */
public class User {
  
  private static int idCounter = 0;

  private int id;
  private String username;
  private String email;
  private List<Vote> castedVotes;
  private List<Poll> createdPolls;

  public User(){
    this.createdPolls = new ArrayList<>();
    this.castedVotes = new ArrayList<>();
  }

  public User(String username, String email){
    this.id = generateId();
    this.username = username;
    this.email = email;
    this.createdPolls = new ArrayList<>();
    this.castedVotes = new ArrayList<>();
  }

  private int generateId(){
    return idCounter++;
  }

  public static int getIdCounter() {
    return idCounter;
  }

  public static void setIdCounter(int idCounter) {
    User.idCounter = idCounter;
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

  public List<Vote> getCastedVotes() {
    return castedVotes;
  }

  public void setCastedVotes(List<Vote> castedVotes) {
    this.castedVotes = castedVotes;
  }

  public List<Poll> getCreatedPolls() {
    return createdPolls;
  }

  public void setCreatedPolls(List<Poll> createdPolls) {
    this.createdPolls = createdPolls;
  }

  @Override
  public String toString(){
    return "ID: " + this.id + " Username: " + this.username + " Email: " + this.email + " Votes: " + this.getCastedVotes() + " Polls: "; 
  }

  @Override
  public boolean equals(Object obj){
    if (this == obj){
      return true;
    }
    if (obj == null | getClass() != obj.getClass()){
      return false;
    }
    User user = (User)obj;
    return Objects.equals(this.username, user.username) && Objects.equals(this.email, user.email);
  }
}
