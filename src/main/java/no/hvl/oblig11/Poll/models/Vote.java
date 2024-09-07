package no.hvl.oblig11.Poll.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Vote
 */
public class Vote {

  private static int idCounter = 0;
  
  private int id;
  @JsonIgnore
  private User caster;
  private Instant publishedAt;
  private VoteOption voteOption;

  public Vote(){}

  public Vote(VoteOption voteOption){
    this.voteOption = voteOption;
  }

  public Vote(User caster, VoteOption voteOption){
    this.id = generateId();
    this.caster = caster;
    this.voteOption = voteOption;
    this.publishedAt = Instant.now();
  }

  public Vote(User caster){
    this.id = generateId();
    this.caster = caster;
    this.publishedAt = Instant.now();
  }
  private synchronized int generateId(){
    return idCounter++;
  }

  public static int getIdCounter() {
    return idCounter;
  }

  public static void setIdCounter(int idCounter) {
    Vote.idCounter = idCounter;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getCaster() {
    return caster;
  }

  public void setCaster(User caster) {
    this.caster = caster;
  }

  public Instant getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(Instant publishedAt) {
    this.publishedAt = publishedAt;
  }

  public VoteOption getVoteOption() {
    return voteOption;
  }

  public void setVoteOption(VoteOption voteOption) {
    this.voteOption = voteOption;
  }

  @Override
  public String toString(){
    return this.getCaster().getUsername() + " " + this.getVoteOption().getCaption();
  }
  @Override
  public boolean equals(Object obj){
    // Check if the object is compared with itself
    if (this == obj) {
      return true;
    }
    // Check if the object is of the same type
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    // Cast the object to a Vote
    Vote other = (Vote) obj;
    // Compare id, caster, publishedAt, and voteOption
    return this.id == other.id &&
      (this.caster == null ? other.caster == null : this.caster.equals(other.caster)) &&
      (this.publishedAt == null ? other.publishedAt == null : this.publishedAt.equals(other.publishedAt)) &&
      (this.voteOption == null ? other.voteOption == null : this.voteOption.equals(other.voteOption));
  }
}
